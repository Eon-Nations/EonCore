package com.eonnations.eoncore.modules.worldgen;

import io.vavr.Function1;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.PerlinOctaveGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class IslandGenerator extends ChunkGenerator {

    private SimplexOctaveGenerator noiseGen;
    private SimplexOctaveGenerator regionGen;
    private SimplexOctaveGenerator bottomGen;

    private Set<Tuple2<Double, Double>> islandCenters;
    private Map<Tuple2<Double, Double>, List<Tuple2<Double, Double>>> islandShapes;

    private static final int WORLD_SIZE = 10000;
    private static final int NUM_ISLANDS = 100;
    private static final int ISLAND_RADIUS = 100;

    public IslandGenerator() {
        Random random = new Random();
        islandCenters = Stream.continually(() -> Tuple.of(random.nextDouble(-WORLD_SIZE, WORLD_SIZE), random.nextDouble(-WORLD_SIZE, WORLD_SIZE)))
                .take(NUM_ISLANDS)
                .append(Tuple.of(0.0, 0.0))
                .toSet();
        islandShapes = islandCenters
                .map(k -> Tuple.of(k, List.ofAll(ShapeGen.generatePolygon(k, ISLAND_RADIUS, 1.0, 0.5, 20, random))))
                .toMap(Tuple2::_1, Tuple2::_2);
    }

    @Override
    public void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {
        if (noiseGen == null) {
            noiseGen = new SimplexOctaveGenerator(worldInfo.getSeed(), 5);
            regionGen = new SimplexOctaveGenerator(worldInfo.getSeed() + 1, 2);
            bottomGen = new SimplexOctaveGenerator(worldInfo.getSeed() + 2, 2);
        }
        generateTerrain(chunkData, chunkX, chunkZ, noiseGen);
    }

    private double distanceFromCenter(Tuple2<Double, Double> center, int x, int z) {
        double dx = x - center._1;
        double dz = z - center._2;
        return Math.sqrt(dx * dx + dz * dz);
    }

    private double distanceFromCenterWithY(Tuple2<Double, Double> center, int topY, int x, int y, int z) {
        double dx = x - center._1;
        double dz = z - center._2;
        double dy = topY - y;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    private Tuple2<Tuple2<Double, Double>, Boolean> isInIsland(int x, int z) {
        Tuple2<Tuple2<Double, Double>, Double> closestIsland = islandCenters
                .map(k -> Tuple.of(k, distanceFromCenter(k, x, z)))
                .minBy(Comparator.comparing(v -> v._2))
                .get();
        boolean isCloseToIsland = closestIsland._2 < ISLAND_RADIUS;
        if (!isCloseToIsland) {
            return Tuple.of(Tuple.of(0.0, 0.0), false);
        }
        return Tuple.of(closestIsland._1, islandShapes.exists(val -> ShapeGen.pointInPolygon(x, z, val._2)));
    }

    private void generateTerrain(ChunkData chunkData, int chunkX, int chunkZ, SimplexOctaveGenerator noiseGenerator) {
        int worldHeight = 128;

        // Loop over the chunk (16x16)
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = chunkX * 16 + x;
                int worldZ = chunkZ * 16 + z;
                Tuple2<Tuple2<Double, Double>, Boolean> islandResults = isInIsland(worldX, worldZ);
                if (!islandResults._2) {
                    continue;
                }
                double regionNoise = regionGen.noise(worldX * 0.001, worldZ * 0.001, 0.5, 0.5);
                double scale = lerp(0.0005, 0.002, regionNoise);

                double height = Math.abs(generatePerlinNoise(worldX, worldZ, noiseGenerator, scale));
                int islandHeight = (int) (worldHeight * height);

                final int spikeDepth = 10;
                double spikeNoise = bottomGen.noise(worldX * 0.08, worldZ * 0.08, 0.5, 0.5) * 5;
                int spikeOffsetFromZero = (int) spikeNoise * spikeDepth;
                // Place blocks based on the noise
                for (int y = -spikeOffsetFromZero; y < islandHeight; y++) {
                    // Generate height map for the island
                    double yNormal = y / (double) islandHeight;
                    // Base radius taper (like a cone)
                    double baseRadius = ISLAND_RADIUS * (1.0 - yNormal);

                    double bottomNoise = bottomGen.noise(worldX * 0.08, y * 0.08, worldZ * 0.08, 0.5, 0.5);
                    double noiseModifier = (bottomNoise - 0.5) * 20.0; // Range: [-10, +10] tweak strength here

                    double finalRadius = baseRadius + noiseModifier;
                    finalRadius = Math.max(2.0, finalRadius);
                    double distanceToCenter = distanceFromCenter(islandResults._1, worldX, worldZ);

                    if (distanceToCenter > finalRadius) continue;
                    chunkData.setBlock(x, y + worldHeight, z, Material.STONE);
                }
            }
        }
    }

    private static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    private double generatePerlinNoise(int worldX, int worldZ, SimplexOctaveGenerator noiseGenerator, double scale) {
        // Generate noise based on world coordinates scaled for island generation
        return noiseGenerator.noise(worldX * scale, worldZ * scale, 0.5, 0.5);
    }

    public boolean shouldGenerateNoise() { return false; }
    public boolean shouldGenerateSurface() { return true; }
    public boolean shouldGenerateBedrock() { return false; }
    public boolean shouldGenerateCaves() { return false; }
    public boolean shouldGenerateDecorations() { return true; }
    public boolean shouldGenerateMobs() { return true; }
    public boolean shouldGenerateStructures() { return false; }
}

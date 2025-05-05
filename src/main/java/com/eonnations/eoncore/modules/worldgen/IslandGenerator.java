package com.eonnations.eoncore.modules.worldgen;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.PerlinOctaveGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class IslandGenerator extends ChunkGenerator {

    private PerlinOctaveGenerator noiseGen;
    private PerlinOctaveGenerator regionGen;

    @Override
    public void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {
        if (noiseGen == null) {
            noiseGen = new PerlinOctaveGenerator(worldInfo.getSeed(), 5);
            regionGen = new PerlinOctaveGenerator(worldInfo.getSeed() + 1, 2);
        }
        generateTerrain(chunkData, chunkX, chunkZ, noiseGen);
    }

    private void generateTerrain(ChunkData chunkData, int chunkX, int chunkZ, PerlinOctaveGenerator noiseGenerator) {
        int worldHeight = 256;

        // Loop over the chunk (16x16)
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = chunkX * 16 + x;
                int worldZ = chunkZ * 16 + z;

                double regionNoise = regionGen.noise(worldX * 0.001, worldZ * 0.001, 0.5, 0.5);
                double scale = lerp(0.0005, 0.002, regionNoise);

                // Generate height map for the island
                double height = generatePerlinNoise(worldX, worldZ, noiseGenerator, scale);
                int islandHeight = (int) (worldHeight * height);

                // Place blocks based on the noise
                for (int y = 0; y < islandHeight; y++) {
                    chunkData.setBlock(x, y, z, Material.STONE);
                }
            }
        }
    }

    private static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    private double generatePerlinNoise(int worldX, int worldZ, PerlinOctaveGenerator noiseGenerator, double scale) {
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

package com.eonnations.eoncore.node.holograms;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import net.kyori.adventure.text.Component;

public class Hologram implements AutoCloseable {
    private final List<ArmorStand> stands;
    private static final double SPACE_BETWEEN_LINES = 0.35;

    public Hologram(List<Component> lines, Location base) {
        this.stands = lines.reverse()
                .zipWithIndex()
                .map(pair -> new Tuple2<>(spawnArmorStand(pair._2(), base), pair._1()))
                .map(pair -> changeName(pair._2(), pair._1()));
    }

    private ArmorStand changeName(Component name, ArmorStand stand) {
        stand.customName(name);
        return stand;
    }

    private ArmorStand spawnArmorStand(int index, Location base) {
        double heightBoost = base.getY() + (index * SPACE_BETWEEN_LINES);
        Location newLoc = base.clone();
        newLoc.setY(heightBoost);
        ArmorStand stand = base.getWorld().spawn(newLoc, ArmorStand.class);
        stand.setInvulnerable(true);
        stand.setCollidable(false);
        stand.setInvisible(true);
        stand.setMarker(true);
        stand.setCustomNameVisible(true);
        stand.setGravity(false);
        stand.setAI(false);
        return stand;
    }

    @Override
    public void close() {
        stands.forEach(ArmorStand::remove);
    }
}

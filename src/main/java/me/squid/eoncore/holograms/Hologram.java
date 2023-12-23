package me.squid.eoncore.holograms;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

public class Hologram implements AutoCloseable {
    private final List<ArmorStand> stands;

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
        double heightBoost = index * 0.5;
        ArmorStand stand = base.getWorld().spawn(base.add(0, heightBoost, 0), ArmorStand.class);
        stand.setInvulnerable(true);
        stand.setCollidable(false);
        stand.setInvisible(true);
        stand.setCustomNameVisible(true);
        stand.setGravity(false);
        return stand;
    }

    @Override
    public void close() {
        stands.forEach(ArmorStand::remove);
    }
}

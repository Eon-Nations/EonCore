package me.squid.eoncore.holograms;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import static me.squid.eoncore.messaging.Messaging.fromFormatString;

public class Hologram implements AutoCloseable {
    private final ArmorStand stand;

    public Hologram(String line, Location location) {
        this.stand = location.getWorld().spawn(location, ArmorStand.class);
        stand.setInvisible(true);
        stand.setInvulnerable(true);
        stand.setCustomNameVisible(true);
        stand.setGravity(false); // Use setGravity instead of hasGravity
        stand.customName(fromFormatString(line));
    }

    @Override
    public void close() {
        stand.remove();
    }
}

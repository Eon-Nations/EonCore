package me.squid.eoncore.managers;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.Serializable;
import java.util.UUID;

public class Cooldown implements Serializable {

    private final UUID uuid;
    private final long length;
    private final long time;

    public Cooldown(UUID uuid, long length, long time) {
        this.uuid = uuid;
        this.length = length;
        this.time = time;
    }

    public UUID getUUID() {
        return uuid;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() >= time + length;
    }

    public long getTimeRemaining() {
        return (time + length) - System.currentTimeMillis();
    }
}

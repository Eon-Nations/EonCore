package me.squid.eoncore.managers;

import java.util.UUID;

public record Cooldown(UUID uuid, long length, long time) {

    public UUID getUUID() {
        return uuid;
    }

    public boolean isExpired() {
        if (time == -1) return false;
        return System.currentTimeMillis() >= time + length;
    }

    public long getTimeRemaining() {
        return (time + length) - System.currentTimeMillis();
    }

    public long getTime() {
        return time;
    }

    public long getLength() {
        return length;
    }

    @Override
    public String toString() {
        return time + ";" + length;
    }
}

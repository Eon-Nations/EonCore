package me.squid.eoncore.managers;

import java.util.UUID;

public record Cooldown(UUID uuid, long length, long time) {

    public boolean isExpired() {
        if (length == -1) return false;
        return System.currentTimeMillis() >= time + length;
    }

    public long getTimeRemaining() {
        return (time + length) - System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return time + ";" + length;
    }
}

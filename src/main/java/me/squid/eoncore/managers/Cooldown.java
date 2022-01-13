package me.squid.eoncore.managers;

import java.io.Serializable;
import java.util.UUID;

public class Cooldown extends Object implements Serializable {

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
        if (time == -1) return false;
        return System.currentTimeMillis() >= time + length;
    }

    public long getTimeRemaining() {
        return (time + length) - System.currentTimeMillis();
    }

    public long getTime() { return time; }

    public long getLength() { return length; }

    @Override
    public String toString() {
        return time + ";" + "length";
    }
}

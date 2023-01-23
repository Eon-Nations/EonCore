package me.squid.eoncore.misc.managers;

import java.util.*;

public class CooldownManager {
    private final Map<UUID, Cooldown> cooldowns;

    public CooldownManager() {
        cooldowns = new HashMap<>();
    }

    public void add(Cooldown cooldown) {
        UUID uuid = cooldown.uuid();
        cooldowns.put(uuid, cooldown);
    }

    public void remove(UUID uuid) {
        cooldowns.remove(uuid);
    }

    public Cooldown getCooldown(UUID uuid) {
        return cooldowns.get(uuid);
    }

    public boolean hasCooldown(UUID uuid) {
        Cooldown cooldown = cooldowns.get(uuid);
        if (cooldown == null) return false;

        if (cooldown.isExpired()) {
            cooldowns.remove(uuid);
            return false;
        }
        return true;
    }

    public Set<UUID> getUUIDsFromCooldownMap() {
        return cooldowns.keySet();
    }
}

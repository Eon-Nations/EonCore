package me.squid.eoncore.managers;

import scala.concurrent.impl.FutureConvertersImpl;

import java.util.*;

public class CooldownManager {
    private Map<UUID, Cooldown> cooldowns;

    public CooldownManager() {
        cooldowns = new HashMap<>();
    }

    public void add(Cooldown cooldown) {
        final UUID uuid = cooldown.getUUID();
        cooldowns.remove(uuid);
        cooldowns.put(uuid, cooldown);
    }

    public void remove(Cooldown cooldown) {
        final UUID uuid = cooldown.getUUID();
        cooldowns.remove(uuid);
    }

    public Cooldown getCooldown(UUID uuid) {
        if (hasCooldown(uuid)) {
            return cooldowns.get(uuid);
        } else return null;
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

    public List<UUID> getUUIDsFromCooldownMap() {
        return new ArrayList<>(cooldowns.keySet());
    }
}

package me.squid.eoncore.misc.managers;

import me.squid.eoncore.EonCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Set;
import java.util.UUID;

public class MutedManager implements Listener {

    EonCore plugin;
    CooldownManager cooldownManager = new CooldownManager();

    public MutedManager(EonCore plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void savePlayer(UUID uuid) {

    }

    public void loadPlayer(UUID uuid) {

    }

    public void addCooldown(Cooldown cooldown) {
        cooldownManager.add(cooldown);
    }

    public void removePlayer(UUID uuid) {

    }

    public Cooldown getCooldown(UUID uuid) {
        return cooldownManager.getCooldown(uuid);
    }

    public boolean hasCooldown(UUID uuid) {
        return cooldownManager.hasCooldown(uuid);
    }

    public Set<UUID> getAllUUIDs() {
        return cooldownManager.getUUIDsFromCooldownMap();
    }

    public boolean hasMute(UUID uuid) {
        return false;
    }

    @EventHandler
    public void subscribeLoadPlayer(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        loadPlayer(uuid);
    }

    @EventHandler
    public void subscribeSavePlayer(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (hasCooldown(uuid)) savePlayer(uuid);
    }
}

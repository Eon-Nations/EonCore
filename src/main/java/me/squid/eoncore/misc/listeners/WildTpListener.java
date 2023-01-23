package me.squid.eoncore.misc.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.misc.events.WildTeleportEvent;
import me.squid.eoncore.misc.managers.Cooldown;
import me.squid.eoncore.misc.managers.CooldownManager;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.*;
import java.util.function.Consumer;

public class WildTpListener implements Listener {

    EonCore plugin;
    CooldownManager cooldownManager = new CooldownManager();
    private final Set<UUID> playersInPortal = new HashSet<>();
    private final List<Material> blackList = new ArrayList<>();

    public WildTpListener(EonCore plugin) {
        this.plugin = plugin;
        initializeBlacklist();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onWildTeleport(WildTeleportEvent e) {
        Player p = e.player();

        if (e.isInPortal()) {
            playersInPortal.add(p.getUniqueId());
            return;
        }

        if (!cooldownManager.hasCooldown(p.getUniqueId())) {
            p.sendMessage(getWildPrefix() + " Finding a location for you...");
            Location toTp = Utils.generateLocation(e.world(), blackList);
            p.sendMessage(getWildPrefix() + " Found a location. Loading...");

            Bukkit.getScheduler().runTask(plugin,
                    () -> p.getWorld().getChunkAtAsync(toTp.getBlock(), teleportPlayerChunk(p, toTp)));
        } else {
            if (e.isInPortal()) Bukkit.getScheduler().runTask(plugin, () -> p.teleport(Utils.getSpawnLocation()));
            Cooldown cooldown = cooldownManager.getCooldown(p.getUniqueId());
            p.sendMessage(getWildPrefix() + Utils.chat(" &aYou are on cooldown for " +
                    Utils.getFormattedTimeString(cooldown.getTimeRemaining())));
            Bukkit.getScheduler().runTaskLater(plugin, () -> playersInPortal.remove(p.getUniqueId()), 20L);
        }
    }

    private Consumer<Chunk> teleportPlayerChunk(Player player, Location toTp) {
        return c -> {
            player.teleportAsync(toTp);
            player.sendMessage(getWildPrefix() + " You have been teleported to the coords: x:" + toTp.getBlockX() + " y:" + toTp.getBlockY() + " z:" + toTp.getBlockZ());
            player.setInvulnerable(true);
            applyCooldown(player);
            removeInitialSafetyNet(player);
        };
    }

    private void removeInitialSafetyNet(Player p) {
        Runnable removal = () -> {
            p.setInvulnerable(false);
            playersInPortal.remove(p.getUniqueId());
        };
        // 100 Ticks is about 5 seconds of invulnerability
        Bukkit.getScheduler().runTaskLater(plugin, removal, 100L);
    }

    private void applyCooldown(Player p) {
        if (!p.isOp()) {
            Cooldown cooldown = new Cooldown(p.getUniqueId(), minutesToMilliseconds(5), System.currentTimeMillis());
            cooldownManager.add(cooldown);
        }
    }

    private int minutesToMilliseconds(int minutes) {
        final int MILLISECONDS = 1000;
        final int SECONDS = 60;
        return minutes * SECONDS * MILLISECONDS;
    }

    private void initializeBlacklist() {
        blackList.add(Material.LAVA);
        blackList.add(Material.CACTUS);
        blackList.add(Material.FIRE);
        blackList.add(Material.WATER);
    }

    private String getWildPrefix() {
        return Utils.translateHex("#7f7f7f[#ff7f00Wild#7f7f7f]");
    }
}

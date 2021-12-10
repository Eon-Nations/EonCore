package me.squid.eoncore.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.events.WildTeleportEvent;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class WildTpListener implements Listener {

    EonCore plugin;
    private HashMap<UUID, Long> cooldown = new HashMap<>();
    private List<UUID> playersInPortal = new ArrayList<>();
    private List<Material> blackList = new ArrayList<>();

    public WildTpListener(EonCore plugin) {
        this.plugin = plugin;
        initializeBlacklist();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onWildTeleport(WildTeleportEvent e) {
        Player p = e.getPlayer();

        if (e.isInPortal()) {
            if (!playersInPortal.contains(p.getUniqueId())) playersInPortal.add(p.getUniqueId());
            else return;
        }

        if (!isOnCoolDown(p)) {
            p.sendMessage(getWildPrefix()
                    .append(Component.text(" Finding a location for you...").color(TextColor.color(192, 192, 192))));
            Location toTp = Utils.generateLocation(e.getWorld(), blackList);
            p.sendMessage(getWildPrefix().append(Component.text(" Found a location. Loading...")
                    .color(TextColor.color(192, 192, 192))));

            Bukkit.getScheduler().runTask(plugin, () -> p.teleport(toTp));
            p.sendMessage(getWildPrefix().append(Component.text(" You have been teleported to the coords: x:" + toTp.getBlockX() + " y:" + toTp.getBlockY() + " z:" + toTp.getBlockZ())
                    .color(TextColor.color(192, 192, 192))));
            p.setInvulnerable(true);
            addCoolDown(p, 300000);
            Bukkit.getScheduler().runTaskLater(plugin, () -> { p.setInvulnerable(false);
                playersInPortal.remove(p.getUniqueId()); } , 100L);
        } else {
            if (e.isInPortal()) Bukkit.getScheduler().runTask(plugin, () -> p.teleportAsync(Utils.getSpawnLocation()));
            p.sendMessage(getWildPrefix().append(Utils.chat(" &aYou are on cooldown for " +
                    ((cooldown.get(p.getUniqueId()) - System.currentTimeMillis()) / 1000) + " more seconds")));
            Bukkit.getScheduler().runTaskLater(plugin, () -> playersInPortal.remove(p.getUniqueId()), 20L);
        }
    }

    public boolean isOnCoolDown(Player p) {
         if (cooldown.get(p.getUniqueId()) != null) {
             if (System.currentTimeMillis() > cooldown.get(p.getUniqueId())) {
                 cooldown.remove(p.getUniqueId());
                 return false;
             } else return true;
         }
         return false;
    }

    public void addCoolDown(Player p, long length) {
        if (!p.isOp()) {
            cooldown.put(p.getUniqueId(), System.currentTimeMillis() + length);
        }
    }

    private void initializeBlacklist() {
        blackList.add(Material.LAVA);
        blackList.add(Material.CACTUS);
        blackList.add(Material.FIRE);
        blackList.add(Material.WATER);
    }

    private Component getWildPrefix() {
        return Component.text("[").color(TextColor.color(128, 128, 128))
                .append(Component.text("Wild").color(TextColor.color(255, 128, 0)))
                .append(Component.text("]").color(TextColor.color(128, 128, 128)));
    }
}

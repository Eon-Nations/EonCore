package me.squid.eoncore.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.events.WildTeleportEvent;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.UUID;

public class WildTpListener implements Listener {

    EonCore plugin;
    private HashMap<UUID, Long> cooldown = new HashMap<>();

    public WildTpListener(EonCore plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onWildTeleport(WildTeleportEvent e) {
        Player p = e.getPlayer();
        Location toTp = e.getLocation();

        if (!isOnCoolDown(p)) {
            p.sendMessage(Utils.chat("&7[&6&lWild&7] &aFinding a safe place for you to teleport to"));
            p.sendMessage(Utils.chat("&7[&6&lWild&7] &aFound a location. Loading..."));
            p.sendMessage(Utils.chat("&7[&6&lWild&7] &r&5You have been teleported to the coords: x:" + toTp.getBlockX() + " y:" + toTp.getBlockY() + " z:" + toTp.getBlockZ()));
            Bukkit.getScheduler().runTask(plugin, () -> p.teleport(toTp));
            addCoolDown(p, 300000);
        } else {
            p.sendMessage(Utils.chat("&7[&6&lWild&7] &r&aYou are on cooldown for " + ((cooldown.get(p.getUniqueId()) - System.currentTimeMillis()) / 1000) + " more seconds"));
        }
    }

    public boolean isOnCoolDown(Player p) {
         if (cooldown.get(p.getUniqueId()) != null) {
             if (System.currentTimeMillis() > cooldown.get(p.getUniqueId())) {
                 cooldown.remove(p.getUniqueId());
                 return false;
             } else {
                 return true;
             }
         }
         return false;
    }

    public void addCoolDown(Player p, long length) {
        if (!p.isOp()) {
            cooldown.put(p.getUniqueId(), System.currentTimeMillis() + length);
        }
    }
}

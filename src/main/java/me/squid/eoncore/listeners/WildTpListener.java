package me.squid.eoncore.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.events.WildTeleportEvent;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class WildTpListener implements Listener {

    EonCore plugin;
    private HashMap<UUID, Long> cooldown = new HashMap<>();
    private ArrayList<UUID> mainList = new ArrayList<>();
    private ArrayList<UUID> resourceList = new ArrayList<>();

    public WildTpListener(EonCore plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onWildTeleport(WildTeleportEvent e) {
        Player p = e.getPlayer();

        if (!isOnCoolDown(p)) {
            p.sendMessage(Component.text("[").color(TextColor.color(128, 128, 128))
                    .append(Component.text("Wild").color(TextColor.color(255, 128, 0)))
                    .append(Component.text("]").color(TextColor.color(128, 128, 128)))
                    .append(Component.text(" Finding a location for you...").color(TextColor.color(192, 192, 192))));
            Location toTp = Utils.generateLocation(e.getWorld());
            p.sendMessage(Utils.chat("&7[&6&lWild&7] &aFound a location. Loading..."));
            Bukkit.getScheduler().runTask(plugin, () -> p.teleport(toTp));
            p.sendMessage(Utils.chat("&7[&6&lWild&7] &r&5You have been teleported to the coords: x:" + toTp.getBlockX() + " y:" + toTp.getBlockY() + " z:" + toTp.getBlockZ()));
            p.setInvulnerable(true);
            addCoolDown(p, 300000);
            Bukkit.getScheduler().runTaskLater(plugin, () -> p.setInvulnerable(false), 100L);
        } else {
            if (e.isInPortal()) p.teleportAsync(Utils.getSpawnLocation());
            p.sendMessage(Utils.chat("&7[&6&lWild&7] &r&aYou are on cooldown for " + ((cooldown.get(p.getUniqueId()) - System.currentTimeMillis()) / 1000) + " more seconds"));
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
}

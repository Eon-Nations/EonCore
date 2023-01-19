package me.squid.eoncore.misc.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.misc.events.WildTeleportEvent;
import me.squid.eoncore.misc.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PortalListener implements Listener {

    EonCore plugin;

    public PortalListener(EonCore plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEndPortalJump(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL) && isInSpawn(p)) {
            e.setCancelled(true);
            WildTeleportEvent wildEvent = new WildTeleportEvent(p, Bukkit.getWorld("world"), true);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> Bukkit.getPluginManager().callEvent(wildEvent));
        }
    }

    private boolean isInSpawn(Player p) {
        return p.getWorld().getName().contains("spawn");
    }

    @EventHandler
    public void onNetherPortalEnter(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) && isInSpawn(p)) {
            e.setCancelled(true);
            p.teleport(Utils.getSpawnLocation());
            // Leaving a couple ticks of space for Mob Arena to register that the previous location is spawn and not the portal
            Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.dispatchCommand(p, "ma join"), 2);
        }
    }
}

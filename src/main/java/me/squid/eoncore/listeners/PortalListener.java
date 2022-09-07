package me.squid.eoncore.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.events.WildTeleportEvent;
import me.squid.eoncore.utils.Utils;
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
            p.teleportAsync(Utils.getSpawnLocation());
            Bukkit.dispatchCommand(p, "ma join");
        }
    }
}

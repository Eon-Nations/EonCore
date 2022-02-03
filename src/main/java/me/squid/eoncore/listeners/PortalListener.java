package me.squid.eoncore.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.events.WildTeleportEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
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
        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL) &&
        p.getWorld().getName().equals("spawn_void")) {
            e.setCancelled(true);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () ->
                    Bukkit.getPluginManager().callEvent(new WildTeleportEvent(p, Bukkit.getWorld("world"), true)));
        }
    }

    @EventHandler
    public void onRedstoneBlockClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().equals(Material.REDSTONE_BLOCK)) {
                e.getPlayer().sendMessage("That's a redstone block");
            }
        }
    }
}

package me.squid.eoncore.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class JoinLeaveListener implements Listener {

    EonCore plugin;

    public JoinLeaveListener(EonCore plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void JoinMessage(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.hasPlayedBefore()){
            e.setJoinMessage(Utils.chat(Objects.requireNonNull(plugin.getConfig().getString("Join-Message"))
            .replace("<player>", p.getName())));
            p.sendTitle(Utils.chat("&5&lEon Survival"), Utils.chat("&bWelcome back!"), 30, 30, 30);
            if (p.isOp()) p.setSleepingIgnored(true);
        } else {
            Location spawnLoc = new Location(Bukkit.getWorld("spawn"), -546.3696854992578, 46.0
            , -568.5314282419047);
            e.setJoinMessage(Utils.chat(Objects.requireNonNull(plugin.getConfig().getString("Welcome-Message"))
            .replace("<player>", p.getName())));
            p.teleportAsync(spawnLoc);
            p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
            p.sendTitle(Utils.chat("&5&lEon Survival"), Utils.chat("&bWelcome " + p.getName()) + "!", 30, 30, 30);
        }
    }

    @EventHandler
    public void LeaveMessage(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage(Utils.chat(Objects.requireNonNull(plugin.getConfig().getString("Leave-Message"))
        .replace("<player>", p.getName())));
    }
}

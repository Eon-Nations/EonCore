package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.events.BackToDeathLocationEvent;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashMap;

public class BackCommand implements CommandExecutor, Listener {
    EonCore plugin;
    private final HashMap<Player, Location> backLocations = new HashMap<>();

    public BackCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("back").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            boolean hasCooldown = !p.hasPermission("eoncommands.back.cooldown.bypass");
            Bukkit.getPluginManager().callEvent(new BackToDeathLocationEvent(p, hasCooldown));
        }
        return true;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (!e.getEntity().getWorld().equals(Bukkit.getWorld("spawn_void"))) {
            backLocations.put(e.getEntity(), e.getEntity().getLocation());
        }
        if (e.getEntity().isOp()) {
            e.setKeepInventory(true);
            e.getDrops().clear();
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        e.getPlayer().sendMessage(Utils.getPrefix("nations") + Utils.chat(plugin.getConfig().getString("Death-Back-Message")));
    }

    @EventHandler
    public void onBackCommand(BackToDeathLocationEvent e) {
        Player p = e.getPlayer();
        Location toTeleport = backLocations.remove(p);

        if (!backLocations.containsKey(p)) {
            p.sendMessage(Utils.getPrefix("nations") + Utils.chat("&7There is no back location to teleport to"));
            return;
        }

        if (e.hasCooldown()) {
            p.sendMessage(Utils.getPrefix("nations") + Utils.chat(plugin.getConfig().getString("Cooldown-Teleport-Message")
                    .replace("<seconds>", Long.toString(plugin.getConfig().getLong("Delay-Seconds")))));
            Bukkit.getScheduler().runTaskLater(plugin, doTeleportDelay(p, toTeleport), plugin.getConfig().getLong("Delay-Seconds") * 20);
        } else {
            p.teleport(toTeleport);
            p.sendMessage(Utils.chat(Utils.getPrefix("nations") + plugin.getConfig().getString("Teleport-Successful")));
        }
    }

    private Runnable doTeleportDelay(Player p, Location toTeleport) {
        return () -> {
            p.teleport(toTeleport);
            p.sendMessage(Utils.chat(Utils.getPrefix("nations") + plugin.getConfig().getString("Teleport-Successful")));
        };
    }
}

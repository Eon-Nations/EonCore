package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.events.BackToDeathLocationEvent;
import me.squid.eoncore.utils.Messaging;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashMap;

public class BackCommand extends EonCommand implements Listener {
    EonCore plugin;
    private final HashMap<Player, Location> backLocations = new HashMap<>();

    public BackCommand(EonCore plugin) {
        super("back", plugin);
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void execute(Player player, String[] args) {
        boolean hasCooldown = !player.hasPermission("eoncommands.back.cooldown.bypass");
        Bukkit.getPluginManager().callEvent(new BackToDeathLocationEvent(player, hasCooldown));
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
        Messaging.sendNationsMessage(e.getPlayer(), plugin.getConfig().getString("Death-Back-Message"));
    }

    @EventHandler
    public void onBackCommand(BackToDeathLocationEvent e) {
        Player p = e.player();
        Location toTeleport = backLocations.remove(p);

        if (toTeleport == null) {
            Messaging.sendNationsMessage(p, "There is no back location to teleport to");
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

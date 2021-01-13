package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TpaAcceptCommand implements CommandExecutor {

    EonCore plugin;
    List<Material> blackList = new ArrayList<>();

    final String prefix = "&7[&5&lEon Survival&7]&r ";

    public TpaAcceptCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("tpaccept")).setExecutor(this);
        blackList.add(Material.LAVA);
        blackList.add(Material.WATER);
        blackList.add(Material.CACTUS);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            int delay = plugin.getConfig().getInt("Delay-Seconds");

            if (TpaCommand.getRequests().containsKey(p.getUniqueId())) {
                Player target = Bukkit.getPlayer(TpaCommand.getRequests().get(p.getUniqueId()));
                if (p.hasPermission("eoncommands.tpa.cooldown.bypass")) {
                    Bukkit.getScheduler().runTask(plugin, teleportPlayer(target, p));
                } else if (!p.hasPermission("eoncommands.tpa.cooldown.bypass")) {
                    Bukkit.getScheduler().runTaskLater(plugin, teleportPlayer(target, p), delay * 20);
                    target.sendMessage(Utils.chat(prefix + plugin.getConfig().getString("Cooldown-Teleport-Message")
                    .replace("<seconds>", String.valueOf(delay))));
                }
            } else {
                p.sendMessage(Utils.chat(prefix + plugin.getConfig().getString("No-Pending-Requests")));
            }
        }

        return true;
    }

    private Runnable teleportPlayer(Player p, Player target) {
        return () -> {
            Location targetLoc = target.getLocation();
            Block below = targetLoc.getWorld().getBlockAt(targetLoc.getBlockX(), targetLoc.getBlockY() - 1, targetLoc.getBlockZ());
            Block inside = targetLoc.getBlock();
            Block above = target.getWorld().getBlockAt(targetLoc.getBlockX(), targetLoc.getBlockY() + 1, targetLoc.getBlockZ());

            if (below.getType().equals(Material.AIR) || blackList.contains(below.getType()) || (blackList.contains(inside.getType())) || blackList.contains(above.getType())) {
                p.sendMessage(Utils.chat(prefix + plugin.getConfig().getString("Unsafe-Tp-Player-Message")));
                target.sendMessage(Utils.chat(prefix + Objects.requireNonNull(plugin.getConfig().getString("Unsafe-Tp-Target-Message"))
                        .replace("<material>", StringUtils.capitalize(below.getType().name().toLowerCase()))));
            } else {
                p.teleport(target.getLocation(), PlayerTeleportEvent.TeleportCause.SPECTATE);
                p.sendMessage(Utils.chat(prefix + plugin.getConfig().getString("Teleport-Successful")));
                target.sendMessage(Utils.chat(prefix + plugin.getConfig().getString("Teleport-Successful")));
            }
            TpaCommand.getRequests().remove(target.getUniqueId());
        };
    }
}

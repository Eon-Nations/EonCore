package me.squid.eoncore.tasks;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.commands.TpaCommand;
import me.squid.eoncore.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TpaCooldownTask extends BukkitRunnable {

    EonCore plugin;
    Player p;
    Player target;
    final String prefix = "&7[&5&lEon Survival&7]&r ";

    List<Material> blackList = new ArrayList<>();

    public TpaCooldownTask(EonCore plugin, Player p, Player target) {
        this.plugin = plugin;
        this.p = p;
        this.target = target;
        blackList.add(Material.LAVA);
        blackList.add(Material.WATER);
        blackList.add(Material.CACTUS);
    }

    @Override
    public void run() {
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
    }
}

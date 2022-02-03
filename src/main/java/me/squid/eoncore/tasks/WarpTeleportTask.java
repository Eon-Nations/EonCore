package me.squid.eoncore.tasks;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WarpTeleportTask extends BukkitRunnable {

    EonCore plugin;
    Location toWarp;
    Player p;
    String name;

    public WarpTeleportTask(EonCore plugin, Location toWarp, Player p, String name) {
        this.plugin = plugin;
        this.toWarp = toWarp;
        this.p = p;
        this.name = name;
    }

    @Override
    public void run() {
        Bukkit.getScheduler().runTask(plugin, () -> p.teleport(toWarp));
        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
        //noinspection ConstantConditions
        p.sendMessage(Utils.chat(EonCore.prefix + plugin.getConfig().getString("Warp-Message")
                .replace("<warp>", ChatColor.stripColor(name))));
    }
}

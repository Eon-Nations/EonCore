package me.squid.eoncore.tasks;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class AutoAnnouncementTask extends BukkitRunnable {

    EonCore plugin;
    Random random = new Random();

    public AutoAnnouncementTask(EonCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        int message = random.nextInt(plugin.getConfig().getStringList("Messages").size());
        Server server = Bukkit.getServer();
        server.sendMessage(Utils.chat("&6&lEon Info &r&7&l>> " + ChatColor.AQUA + plugin.getConfig().getStringList("Messages").get(message)));
    }
}

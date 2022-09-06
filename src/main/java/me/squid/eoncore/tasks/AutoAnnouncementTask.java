package me.squid.eoncore.tasks;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.Broadcaster;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.messaging.Messenger;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class AutoAnnouncementTask extends BukkitRunnable {

    EonCore plugin;
    Random random = new Random();

    public AutoAnnouncementTask(EonCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        List<String> messages = plugin.getConfig().getStringList("Messages");
        int messageIndex = random.nextInt(messages.size());
        Component message = Messaging.fromFormatString(messages.get(messageIndex));
        Broadcaster broadcaster = Messaging.broadcaster(EonPrefix.INFO);
        broadcaster.broadcast(message);
    }
}

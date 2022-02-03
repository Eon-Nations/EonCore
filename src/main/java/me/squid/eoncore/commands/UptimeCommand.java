package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UptimeCommand implements CommandExecutor {

    EonCore plugin;
    long startupTime;

    public UptimeCommand(EonCore plugin) {
        this.plugin = plugin;
        this.startupTime = System.currentTimeMillis();
        plugin.getCommand("uptime").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings) {
        commandSender.sendMessage(Utils.getPrefix("moderation") +
                Utils.getFormattedTimeString(System.currentTimeMillis() - startupTime));
        return true;
    }
}

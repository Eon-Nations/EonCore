package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class UptimeCommand implements CommandExecutor {

    EonCore plugin;
    long startupTime;

    public UptimeCommand(EonCore plugin) {
        this.plugin = plugin;
        this.startupTime = System.currentTimeMillis();
        plugin.getCommand("uptime").setExecutor(this);
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        commandSender.sendMessage(Utils.getPrefix("moderation")
                .append(Component.text(Utils.getFormattedTimeString(System.currentTimeMillis() - startupTime))));
        return true;
    }
}

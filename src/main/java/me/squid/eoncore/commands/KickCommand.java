package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCommand implements CommandExecutor {

    EonCore plugin;

    @SuppressWarnings("ConstantConditions")
    public KickCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("kick").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length > 2) {
            Player target = Bukkit.getPlayer(args[0]);
            args[0] = "";
            String message = Utils.getMessageFromArgs(args);
            if (target != null) {
                target.kickPlayer(message);
            } else {
                sender.sendMessage(Utils.chat(plugin.getConfig().getString("Target-Null")));
            }
        } else {
            sender.sendMessage(Utils.chat(EonCore.prefix + "&7Usage: /kick <player> <message>"));
        }
        return true;
    }
}

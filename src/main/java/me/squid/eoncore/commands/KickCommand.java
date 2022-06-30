package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.FunctionalBukkit;
import me.squid.eoncore.utils.Messaging;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class KickCommand implements CommandExecutor {
    EonCore plugin;

    public KickCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("kick").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 2) {
            String username = args[0];
            FunctionalBukkit.getPlayerOrSendMessage(sender, kickPlayer(reason(args)), username);
        } else {
            Messaging.sendNationsMessage(sender, "Usage: /kick <player> <message>");
        }
        return true;
    }

    private Consumer<Player> kickPlayer(Component reason) {
        return target -> target.kick(reason);
    }

    private Component reason(String[] args) {
        args[0] = "";
        String message = Utils.getMessageFromArgs(args);
        return Component.text(message);
    }
}

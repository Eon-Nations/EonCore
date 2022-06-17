package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.FunctionalBukkit;
import me.squid.eoncore.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.function.Consumer;

public class DirectMessageCommand implements CommandExecutor {
    EonCore plugin;

    public DirectMessageCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("message").setExecutor(this);
    }

    private String constructMessage(CommandSender sender, Player target, String[] args) {
        return Utils.chat("&7[&6" + sender.getName() + "&r&7 -> &6" + target.getName() + "&7] >> ") + Utils.getMessageFromArgs(args);
    }

    private void sendDirectMessage(CommandSender sender, Player target, String message) {
        target.sendMessage(message);
        sender.sendMessage(message);
    }

    private Consumer<Player> messageFunc(CommandSender sender, String[] args) {
        return target -> {
            String message = constructMessage(sender, target, args);
            sendDirectMessage(sender, target, message);
        };
    }

    private Runnable sendMissingTarget(CommandSender sender) {
        return () -> sender.sendMessage(Utils.getPrefix("nations") + "Failed to find player. Please try again.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 2) {
            Optional<Player> maybeTarget = FunctionalBukkit.getPlayerFromName(args[0]);
            args[0] = "";
            maybeTarget.ifPresentOrElse(messageFunc(sender, args), sendMissingTarget(sender));
        }
        return true;
    }
}

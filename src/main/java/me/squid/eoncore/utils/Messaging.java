package me.squid.eoncore.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.function.Consumer;

public class Messaging {
    private Messaging() { }
    private static final String NULL_MESSAGE = "That player is offline!";

    private static Consumer<String> sendMessage(Player player) {
        return message -> player.sendMessage(EonPrefix.getPrefix(EonPrefix.NATIONS) + Utils.chat(message));
    }

    public static void sendNationsMessage(Player player, String message) {
        Optional<String> coolString = Optional.ofNullable(message);
        coolString.ifPresent(sendMessage(player));
    }

    public static void sendNationsMessage(CommandSender sender, String message) {
        sendNationsMessage((Player) sender, message);
    }

    public static String getNationsMessage(String message) {
        return EonPrefix.bukkitPrefix(EonPrefix.NATIONS) + Utils.chat(message);
    }

    public static String bukkitNullMessage() {
        return EonPrefix.bukkitPrefix(EonPrefix.NATIONS) + NULL_MESSAGE;
    }

    public static void sendNullMessage(Player player) {
        sendNationsMessage(player, NULL_MESSAGE);
    }

    public static void sendNullMessage(CommandSender sender) {
        sendNullMessage((Player) sender);
    }
}

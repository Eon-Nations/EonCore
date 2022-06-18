package me.squid.eoncore.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.function.Consumer;

public class Messaging {
    private Messaging() { }

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

    public static String getNullMessage(FileConfiguration config) {
        return EonPrefix.getPrefix(EonPrefix.NATIONS) + Utils.chat(config.getString("Target-Null"));
    }

    public static String bukkitNullMessage(FileConfiguration config) {
        return EonPrefix.bukkitPrefix(EonPrefix.NATIONS) + Utils.chat(config.getString("Target-Null"));
    }

    public static void sendNullMessage(Player player, FileConfiguration config) {
        String message = getNullMessage(config);
        player.sendMessage(message);
    }

    public static void sendNullMessage(CommandSender sender, FileConfiguration config) {
        sendNullMessage((Player) sender, config);
    }
}

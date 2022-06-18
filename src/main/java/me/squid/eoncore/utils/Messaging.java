package me.squid.eoncore.utils;

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

    public static String getNationsMessage(String message) {
        Optional<String> coolString = Optional.ofNullable(message);
        return EonPrefix.getPrefix(EonPrefix.NATIONS) + coolString.orElse("");
    }

    public static void sendNullMessage(Player player, FileConfiguration config) {
        String message = EonPrefix.getPrefix(EonPrefix.NATIONS) + Utils.chat(config.getString("Target-Null"));
        player.sendMessage(message);
    }
}

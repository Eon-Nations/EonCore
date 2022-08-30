package me.squid.eoncore.messaging;

import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class Messaging {
    private Messaging() { }
    private static final String NULL_MESSAGE = "That player does not exist or is offline!";
    private static final Map<EonPrefix, Component> prefixMap = EonPrefix.mapping();

    private static Consumer<String> sendMessage(EonPrefix prefix, Player player) {
        return message -> player.sendMessage(EonPrefix.getPrefix(prefix) + Utils.chat(message));
    }

    public static Component formatDM(FileConfiguration config, String playerName, String targetName) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        String format = config.getString("DM-Format")
                .replace("<player>", playerName)
                .replace("<target>", targetName);
        return miniMessage.deserialize(format);
    }

    public static Component fromFormatString(String formatString) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        return miniMessage.deserialize(formatString);
    }

    public static void sendNationsMessage(Player player, String message) {
        Optional<String> coolString = Optional.ofNullable(message);
        coolString.ifPresent(sendMessage(EonPrefix.NATIONS, player));
    }

    public static ConfigMessenger setupConfigMessenger(FileConfiguration config, EonPrefix prefix) {
        return (target, path) -> {
            String format = config.getString(path)
                    .replace("<player>", target.getName());
            Component messagePrefix = prefixMap.get(prefix);
            Component suffix = Component.text(format);
            target.sendMessage(messagePrefix.append(suffix));
        };
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

package me.squid.eoncore.messaging;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Map;

public class Messaging {
    private Messaging() { }
    private static final String NULL_MESSAGE = "That player does not exist or is offline!";
    private static final Map<EonPrefix, Component> prefixMap = EonPrefix.mapping();

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

    public static Messenger messenger(EonPrefix prefix) {
        Component renderedPrefix = prefixMap.get(prefix);
        return (target, message) -> target.sendMessage(renderedPrefix.append(message));
    }

    public static Broadcaster broadcaster(EonPrefix prefix) {
        Server server = Bukkit.getServer();
        Component renderedPrefix = prefixMap.get(prefix);
        return message -> server.broadcast(renderedPrefix.append(message));
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

    public static void sendNullMessage(Player player) {
        Messenger messenger = messenger(EonPrefix.NATIONS);
        Component message = Component.text(NULL_MESSAGE)
                .color(TextColor.color(96, 96, 96));
        messenger.send(player, message);
    }
}

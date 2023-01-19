package me.squid.eoncore.misc.utils;

import me.squid.eoncore.messaging.Messaging;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.function.Consumer;

public class FunctionalBukkit {
    private FunctionalBukkit() { }

    public static Optional<Player> getPlayerFromName(String username) {
        Player player = Bukkit.getPlayer(username);
        return Optional.ofNullable(player);
    }

    public static void getPlayerOrSendMessage(Player receiver, Consumer<Player> playerFunction, String username) {
        Optional<Player> maybePlayer = getPlayerFromName(username);
        maybePlayer.ifPresentOrElse(playerFunction, () -> Messaging.sendNullMessage(receiver));
    }

    public static void getPlayerOrSendMessage(CommandSender sender, Consumer<Player> playerFunction, String username) {
        getPlayerOrSendMessage((Player) sender, playerFunction, username);
    }
}

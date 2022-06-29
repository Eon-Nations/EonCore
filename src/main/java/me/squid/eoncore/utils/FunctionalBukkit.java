package me.squid.eoncore.utils;

import org.bukkit.Bukkit;
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
}

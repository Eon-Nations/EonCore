package me.squid.eoncore.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;

public class FunctionalBukkit {
    private FunctionalBukkit() { }

    public static Optional<Player> getPlayerFromName(String username) {
        Player player = Bukkit.getPlayer(username);
        return player == null ? Optional.empty() : Optional.of(player);
    }

}

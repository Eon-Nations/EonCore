package me.squid.eoncore.messaging;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface ConfigMessenger {
    void sendMessage(Player target, String path);
}

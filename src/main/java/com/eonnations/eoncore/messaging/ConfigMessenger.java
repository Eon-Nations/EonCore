package com.eonnations.eoncore.messaging;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface ConfigMessenger {
    void sendMessage(Player target, String path);
}

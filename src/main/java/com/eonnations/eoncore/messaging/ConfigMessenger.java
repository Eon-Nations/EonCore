package com.eonnations.eoncore.messaging;

import io.vavr.collection.Map;
import org.bukkit.entity.Player;

@FunctionalInterface
public interface ConfigMessenger {
    void sendMessage(Player target, String path, Map<String, String> args);
}

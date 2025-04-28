package com.eonnations.eoncore.messaging;

import io.vavr.collection.Map;
import org.bukkit.entity.Player;

@FunctionalInterface
public interface SimpleConfigMessenger {
    void sendMessage(Player target, String path);
}

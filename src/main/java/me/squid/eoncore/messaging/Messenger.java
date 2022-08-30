package me.squid.eoncore.messaging;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

@FunctionalInterface
public interface Messenger {
    void send(Player target, Component message);
}

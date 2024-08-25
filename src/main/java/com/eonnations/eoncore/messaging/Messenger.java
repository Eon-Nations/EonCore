package com.eonnations.eoncore.messaging;

import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;

@FunctionalInterface
public interface Messenger {
    void send(Player target, Component message);
}

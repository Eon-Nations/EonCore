package com.eonnations.eoncore.messaging;

import net.kyori.adventure.text.Component;

@FunctionalInterface
public interface Broadcaster {
    void broadcast(Component message);
}

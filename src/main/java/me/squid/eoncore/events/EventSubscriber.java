package me.squid.eoncore.events;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

/*
Inspired by lucko's helper
 */
public class EventSubscriber<T extends Event> {


    public static <V extends Event> EventSubscriber<V> subscribe(Class<V> eventClass, EventPriority priority) {
        return null;
    }
}

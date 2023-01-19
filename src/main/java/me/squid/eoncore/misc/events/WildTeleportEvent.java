package me.squid.eoncore.misc.events;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WildTeleportEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    Player p;
    boolean isInPortal;
    World world;

    public WildTeleportEvent(Player p, World world, boolean isInPortal) {
        super(true);
        this.p = p;
        this.world = world;
        this.isInPortal = isInPortal;
    }

    public Player player() {
        return p;
    }

    public boolean isInPortal() { return isInPortal; }

    public World world() { return world; }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}

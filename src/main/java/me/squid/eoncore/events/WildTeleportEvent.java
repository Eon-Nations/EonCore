package me.squid.eoncore.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WildTeleportEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    Player p;
    Location toTp;
    boolean isInPortal;

    public WildTeleportEvent(Player p, Location toTp, boolean isInPortal) {
        super(true);
        this.p = p;
        this.toTp = toTp;
        this.isInPortal = isInPortal;
    }

    public Player getPlayer() {
        return p;
    }

    public Location getLocation() {
        return toTp;
    }

    public boolean isInPortal() { return isInPortal; }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}

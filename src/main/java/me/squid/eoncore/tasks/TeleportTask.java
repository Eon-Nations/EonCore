package me.squid.eoncore.tasks;

import me.squid.eoncore.EonCore;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportTask extends BukkitRunnable {

    EonCore plugin;
    Location toTeleport;
    Player p;

    public TeleportTask(EonCore plugin, Location toTeleport, Player p) {
        this.plugin = plugin;
        this.toTeleport = toTeleport;
        this.p = p;
    }

    @Override
    public void run() {
        p.teleport(toTeleport);
    }
}

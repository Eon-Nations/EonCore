package me.squid.eoncore.tasks;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.commands.WildTpCommand;
import me.squid.eoncore.events.WildTeleportEvent;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PortalTeleportTask extends BukkitRunnable {

    EonCore plugin;

    public PortalTeleportTask(EonCore plugin) {
        this.plugin = plugin;
    }


    @SuppressWarnings("ConstantConditions")
    @Override
    public void run() {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(Bukkit.getWorld("spawn")));

        ProtectedRegion market = regions.getRegion("marketportal");
        ProtectedRegion utilities = regions.getRegion("utilitiesportal");
        ProtectedRegion wildPortal = regions.getRegion("wildportal");

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (market.contains(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ())) {
                Bukkit.getScheduler().runTask(plugin, () -> p.teleport(new Location(Bukkit.getWorld("spawn"), -323, 90, -590, 180, 0)));
            }

            if (utilities.contains(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ())) {
                Bukkit.getScheduler().runTask(plugin, () -> p.teleport(new Location(Bukkit.getWorld("spawn"), -595, 81, -225)));
            }

            if (wildPortal.contains(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ())) {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> Bukkit.getPluginManager().callEvent(new WildTeleportEvent(p, Utils.generateLocation())));
            }

            if (WildTpCommand.isInList(p)) {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> Bukkit.getPluginManager().callEvent(new WildTeleportEvent(p, Utils.generateLocation())));
                WildTpCommand.removeFromList(p);
            }
        }
    }
}

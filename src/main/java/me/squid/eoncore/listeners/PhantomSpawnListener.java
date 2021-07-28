package me.squid.eoncore.listeners;

import me.squid.eoncore.EonCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Phantom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class PhantomSpawnListener implements Listener {

    EonCore plugin;

    public PhantomSpawnListener(EonCore plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if (e.getEntity() instanceof Phantom) {
            e.getEntity().remove();
        }

        if (e.getEntity() instanceof EnderDragon && e.getEntity().getWorld().equals(Bukkit.getWorld("spawn"))) {
            e.getEntity().remove();
        }
    }
}

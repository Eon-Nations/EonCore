package me.squid.eoncore.voting;

import com.vexsoftware.votifier.model.VotifierEvent;
import me.squid.eoncore.EonCore;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VotifierListener implements Listener {

    EonCore plugin;

    public VotifierListener(EonCore plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onVote(VotifierEvent e) {
        plugin.getLogger().info("Received Vote! " + e.getVote().toString());
    }
}

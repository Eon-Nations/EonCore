package me.squid.eoncore.listeners;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import me.squid.eoncore.EonCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.logging.Level;

public class CustomVoteListener implements Listener {

    EonCore plugin;

    public CustomVoteListener(EonCore plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onVotifierEvent(VotifierEvent e) {
        Vote vote = e.getVote();
        Player p;

        try {
            p = Bukkit.getPlayer(vote.getUsername());
        } catch (NullPointerException exception) {
            plugin.getLogger().warning("Username invalid (Player offline).");
        }



    }
}

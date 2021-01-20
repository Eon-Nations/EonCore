package me.squid.eoncore.listeners;

import com.destroystokyo.paper.Title;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.sql.MySQL;
import me.squid.eoncore.sql.SQLManager;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class CustomVoteListener implements Listener {

    EonCore plugin;
    MySQL sql;
    String prefix = "&7[&b&lEonVotes&r&7] &r";

    public CustomVoteListener(EonCore plugin) {
        this.plugin = plugin;
        sql = plugin.sql;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onVotifierEvent(VotifierEvent e) {
        Vote vote = e.getVote();
        Player p = null;
        SQLManager sqlManager = new SQLManager(plugin);

        try {
            p = Bukkit.getPlayer(vote.getUsername());
        } catch (NullPointerException exception) {
            plugin.getLogger().warning("Username invalid (Player offline).");
        }

        assert p != null;
        if (!sqlManager.playerExists(p.getUniqueId())) sqlManager.createPlayer(p);
        sqlManager.addVotes(p.getUniqueId(), 1);
        sendVotingReward(p, sqlManager.getVotes(p.getUniqueId()));
    }

    private void sendVotingReward(Player p, int votes) {
        String subtitle;
        int remainder = votes % 5;
        if (remainder == 0) {
            subtitle = Utils.chat("&bYou have received 2 Voting Keys. Go to /warps to redeem");
            // Give 2 Voting Keys
        } else subtitle = Utils.chat("&b" + (5 - remainder) + " votes until you get 2 Voting Keys");
        Title title = new Title(Utils.chat("&b&lVote Received!"), subtitle, 20, 40, 20);
        p.sendTitle(title);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(Utils.chat(prefix + "&7" + p.getDisplayName() + " has voted! Do /vote to get rewards and support the server."));
        }
    }
}

package me.squid.eoncore.misc.voting;

import com.vexsoftware.votifier.model.VotifierEvent;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.misc.utils.FunctionalBukkit;
import me.squid.eoncore.misc.utils.Utils;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Optional;

public class VotifierListener implements Listener {
    EonCore plugin;
    LuckPerms luckPerms;

    // Until Database gets implemented
    private final HashMap<Player, Integer> votePersistence = new HashMap<>();

    public VotifierListener(EonCore plugin) {
        this.plugin = plugin;
        this.luckPerms = EonCore.getPerms();
    }

    private void sendPlayerMessageReward(Player p) {
        giveReward(p);
        broadcastMessage(p);
    }

    @EventHandler
    public void subscribeToVotifierEvent(VotifierEvent e) {
        Optional<Player> maybePlayer = FunctionalBukkit.getPlayerFromName(e.getVote().getUsername());
        maybePlayer.ifPresent(this::sendPlayerMessageReward);
    }

    private void giveReward(Player p) {
        final int multiple = 5;
        int voteCount = votePersistence.getOrDefault(p, 0);
        int remainingVotes = multiple - (voteCount % multiple);
        if (voteCount % multiple == 0) {
            giveVotingReward(p);
        }
        p.sendTitle(Utils.chat("&bThank you for voting!"), Utils.chat(remainingVotes + " more votes till the next reward"), 20, 20, 20);
    }

    private void giveVotingReward(Player p) {
        int newVoteCount = votePersistence.getOrDefault(p, 0) + 1;
        votePersistence.put(p, newVoteCount);
    }

    private void broadcastMessage(Player p) {
        Bukkit.broadcastMessage(Utils.getPrefix("nations") + Utils.chat(p.getName() + " has voted!"));
    }
}
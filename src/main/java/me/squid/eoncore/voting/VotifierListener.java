package me.squid.eoncore.voting;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import me.lucko.helper.Events;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VotifierListener {
    EonCore plugin;
    LuckPerms luckPerms;
    VotePersistence votePersistence;

    public VotifierListener(EonCore plugin) {
        this.plugin = plugin;
        this.luckPerms = LuckPermsProvider.get();
        this.votePersistence = new VotePersistence();
        subscribeToVotifierEvent();
    }

    public void subscribeToVotifierEvent() {
        Events.subscribe(VotifierEvent.class)
                .filter(event -> Bukkit.getPlayer(event.getVote().getUsername()) != null)
                .handler(event -> {
                    Vote vote = event.getVote();
                    Player p = Bukkit.getPlayer(vote.getUsername());
                    votePersistence.addVoteToPlayer(p);
                    giveReward(p);
                    broadcastMessage(p);
                });
    }

    private void giveReward(Player p) {
        final int multiple = 5;
        int voteCount = votePersistence.getOnlineTotalVotes(p);
        int remainingVotes = multiple - (voteCount % multiple);
        if (voteCount % multiple == 0) {
            giveVotingReward(p);
        }
        p.sendTitle(Utils.chat("&bThank you for voting!"), Utils.chat(remainingVotes + " more votes till the next reward"), 20, 20, 20);
    }

    private void giveVotingReward(Player p) {

    }

    private void broadcastMessage(Player p) {
        Bukkit.broadcastMessage(Utils.getPrefix("nations") + Utils.chat(p.getName() + " has voted!"));
    }
}
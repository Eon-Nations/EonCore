package me.squid.eoncore.voting;

import com.vexsoftware.votifier.model.VotifierEvent;
import me.lucko.helper.Events;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.database.RedisClient;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

import java.util.function.IntFunction;

import static me.squid.eoncore.utils.FunctionalBukkit.getPlayerFromName;

public class Voting {
    public static final String VOTING_KEY = ":votes";
    private static final String MAIN_TITLE = "Thank you for voting!";
    private static final IntFunction<String> SUB_TITLE = votes -> votes + " more votes till the next reward";

    private Voting() { }

    public static void subscribeToVotifierEvent(EonCore plugin, RedisClient client) {
        var event = Events.subscribe(VotifierEvent.class)
                .filter(e -> getPlayerFromName(e.getVote().getUsername()).isPresent())
                .handler(e -> giveReward(getPlayerFromName(e.getVote().getUsername()).get(), client));
        event.bindWith(plugin);
    }

    public static void giveReward(Player p, RedisClient client) {
        final int multiple = 5;
        int voteCount = client.getKey(p.getUniqueId(), VOTING_KEY, Integer::parseInt);
        int remainingVotes = multiple - (voteCount % multiple);
        if (voteCount % multiple == 0) {
            client.setValue(p.getUniqueId(), VOTING_KEY, voteCount + 1, String::valueOf);
            // Give reward here
        }
        Title title = Title.title(Component.text(MAIN_TITLE), Component.text(SUB_TITLE.apply(remainingVotes)));
        p.showTitle(title);
    }
}
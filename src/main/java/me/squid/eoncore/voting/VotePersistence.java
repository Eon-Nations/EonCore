package me.squid.eoncore.voting;

import me.squid.eoncore.EonCore;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.MetaNode;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class VotePersistence {
    LuckPerms luckPerms;

    public VotePersistence() {
        this.luckPerms = EonCore.getPerms();
    }

    public void addVoteToPlayer(Player p) {
        CompletableFuture<Integer> voteFuture = loadOfflinePlayerVotes(p.getUniqueId());
        voteFuture.thenAcceptAsync(voteCount -> saveVotes(p, ++voteCount));
    }

    public int getOnlineTotalVotes(Player p) {
        CachedMetaData playerData = luckPerms.getPlayerAdapter(Player.class).getMetaData(p);
        String amountString = playerData.getMetaValue("total-votes");
        return amountString == null ? 0 : votesFromString(amountString);
    }

    private CompletableFuture<Integer> loadOfflinePlayerVotes(UUID playerUUID) {
        CompletableFuture<User> playerFuture = luckPerms.getUserManager().loadUser(playerUUID);
        return playerFuture.thenApplyAsync(user -> {
            Stream<MetaNode> playerNodes = setupNodeStream(user);
            Optional<MetaNode> voteNode = playerNodes.filter(votingPredicate()).findFirst();
            return voteNode.map(node -> votesFromString(node.getMetaValue())).orElse(0);
        });
    }

    private void saveVotes(Player p, int voteCount) {
        MetaNode newVoteCount = MetaNode.builder("total-votes", String.valueOf(voteCount)).build();
        luckPerms.getUserManager().modifyUser(p.getUniqueId(), user -> {
            user.data().clear(votingPredicate());
            user.data().add(newVoteCount);
        });
    }

    private int votesFromString(String voteString) {
        return Integer.parseInt(voteString);
    }

    private Predicate<Node> votingPredicate() {
        return NodeType.META.predicate(node -> node.getMetaKey().equals("total-votes"));
    }

    private Stream<MetaNode> setupNodeStream(User user) {
        Collection<MetaNode> metaNodes = user.getNodes(NodeType.META);
        return metaNodes.stream();
    }
}

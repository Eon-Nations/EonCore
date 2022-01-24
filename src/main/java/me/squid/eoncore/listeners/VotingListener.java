package me.squid.eoncore.listeners;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VotingListener implements Listener {
    EonCore plugin;

    public VotingListener(EonCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onVote(VotifierEvent e) {
        Player p = Bukkit.getPlayer(e.getVote().getUsername());
        if (p != null) {
            p.sendMessage(Utils.getPrefix("nations")
                    .append(Component.text("Vote received")));
        }
    }
}

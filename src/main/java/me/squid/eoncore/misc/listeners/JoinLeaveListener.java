package me.squid.eoncore.misc.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static java.time.Duration.ofSeconds;
import static me.squid.eoncore.messaging.Messaging.fromFormatString;

public class JoinLeaveListener implements Listener {
    EonCore plugin;

    public JoinLeaveListener(EonCore plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void sendJoinMessage(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        e.joinMessage(joinMessage(p));
        sendPlayerTitle(p);
        if (!p.hasPlayedBefore()) {
            p.teleport(Utils.getSpawnLocation());
        }
    }

    @EventHandler
    public void sendLeaveMessage(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.quitMessage(leaveMessage(p));
    }

    private void sendPlayerTitle(Player p) {
        String rawTitle = "<#66b2ff>Eon Nations</#66b2ff>";
        String rawSubTitle = p.hasPlayedBefore() ? "<blue>Welcome back!</blue>" :
                "<blue>Welcome " + p.getName() + "!</blue>";
        Title.Times titleTiming = Title.Times.times(ofSeconds(1), ofSeconds(2), ofSeconds(1));
        Title title = Title.title(fromFormatString(rawTitle), fromFormatString(rawSubTitle), titleTiming);
        p.showTitle(title);
    }

    private Component joinMessage(Player p) {
        String configPath = p.hasPlayedBefore() ? "Join-Message" : "Welcome-Message";
        String rawMessage = plugin.getConfig().getString(configPath)
                .replace("<player>", p.getName());
        return fromFormatString(rawMessage);
    }

    private Component leaveMessage(Player player) {
        String rawMessage = plugin.getConfig().getString("Leave-Message")
                .replace("<player>", player.getName());
        return fromFormatString(rawMessage);
    }
}

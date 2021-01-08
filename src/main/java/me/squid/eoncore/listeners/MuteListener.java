package me.squid.eoncore.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.managers.Cooldown;
import me.squid.eoncore.managers.CooldownManager;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MuteListener implements Listener {

    EonCore plugin;

    public MuteListener(EonCore plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMutedChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        CooldownManager cooldownManager = AdminMenuManager.cooldownManager;

        if (cooldownManager.hasCooldown(p.getUniqueId())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(Utils.chat("&7[&a&lEon Moderation&7] &4You are muted"));
        }
    }
}

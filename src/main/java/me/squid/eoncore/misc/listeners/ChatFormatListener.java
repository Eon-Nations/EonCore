package me.squid.eoncore.misc.listeners;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.misc.managers.Cooldown;
import me.squid.eoncore.misc.managers.MutedManager;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class ChatFormatListener implements Listener {

    EonCore plugin;
    MutedManager mutedManager;
    ChatRenderer eonRenderer = initializeRenderer();
    HashMap<String, String> groupColors;
    private static boolean isChatLocked = false;


    public ChatFormatListener(EonCore plugin, MutedManager mutedManager) {
        this.plugin = plugin;
        this.mutedManager = mutedManager;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        initializeGroupColors();
    }

    @EventHandler
    public void onChatSend(AsyncChatEvent e) {
        cancelChatIfMuted(e);
        cancelChatIfLocked(e);
        e.renderer(eonRenderer);
    }

    private ChatRenderer initializeRenderer() {
        return (player, displayName, message, audience) -> {
            String prefix = getPrefix(player);
            return Component.text(Utils.chat("&7[&r"))
                    .append(Component.text(Utils.chat(prefix)))
                    .append(Component.text(Utils.chat("&r&7] >> ")))
                    .append(displayName)
                    .append(Component.text(": "))
                    .append(message);
        };
    }

    private void cancelChatIfMuted(AsyncChatEvent e) {
        Player p = e.getPlayer();
        if (mutedManager.hasCooldown(p.getUniqueId())) {
            Cooldown cooldown = mutedManager.getCooldown(p.getUniqueId());
            if (!cooldown.isExpired()) {
                long timeRemaining = cooldown.getTimeRemaining();
                p.sendMessage(Utils.getPrefix("moderation") + " You are muted for "
                        + Utils.getFormattedTimeString(timeRemaining));
                e.setCancelled(true);
            }
        }
    }

    private void cancelChatIfLocked(AsyncChatEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission("eoncommands.staffchat") && isChatLocked) {
            p.sendMessage(Utils.getPrefix("moderation") + " Chat is locked. Please wait while we resolve the conflict. Thank you for your patience");
            e.setCancelled(true);
        }
    }

    private String getPrefix(Player p) {
        return "member";
    }

    private void initializeGroupColors() {
        groupColors = new HashMap<>();
        groupColors.put("member", "#e0e0e0");
        groupColors.put("traveler", "#00ffff");
        groupColors.put("explorer", "#0066cc");
        groupColors.put("ranger", "#6600cc");
        groupColors.put("spaceman", "#ffff00");
        groupColors.put("astronaut", "#ff7f00");
        groupColors.put("mythic", "#ff0000");
        groupColors.put("builder", "#00cc00");
        groupColors.put("mod", "#6600cc");
        groupColors.put("admin", "#cc0000");
        groupColors.put("owner", "#007fff");
    }

    public static boolean toggleChatLock() {
        setChatLocked(!isChatLocked);
        return isChatLocked;
    }

    public static void setChatLocked(boolean state) {
        isChatLocked = state;
    }

    public static boolean isChatLocked() {
        return isChatLocked;
    }
}

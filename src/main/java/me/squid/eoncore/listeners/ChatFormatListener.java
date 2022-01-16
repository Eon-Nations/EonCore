package me.squid.eoncore.listeners;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.managers.Cooldown;
import me.squid.eoncore.managers.MutedManager;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.context.ImmutableContextSet;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class ChatFormatListener implements Listener {

    EonCore plugin;
    MutedManager mutedManager;
    ChatRenderer customRender;
    LuckPerms lp = EonCore.getPerms();
    HashMap<String, TextColor> groupColors;
    private static boolean isChatLocked = false;


    public ChatFormatListener(EonCore plugin, MutedManager mutedManager) {
        this.plugin = plugin;
        this.mutedManager = mutedManager;
        this.customRender = getNewRenderer();
        Bukkit.getPluginManager().registerEvents(this, plugin);
        initializeGroupColors();
    }

    @EventHandler
    public void onChatSend(AsyncChatEvent e) {
        Player p = e.getPlayer();
        Server server = Bukkit.getServer();

        if (mutedManager.hasCooldown(p.getUniqueId())) {
            Cooldown cooldown = mutedManager.getCooldown(p.getUniqueId());
            if (!cooldown.isExpired()) {
                long timeRemaining = cooldown.getTimeRemaining();
                p.sendMessage(Utils.getPrefix("moderation").append(Component.text("You are muted for ").color(TextColor.color(255, 0, 0)))
                        .append(Component.text(Utils.getFormattedTimeString(timeRemaining))), MessageType.CHAT);
                for (Player staff : Bukkit.getOnlinePlayers()) {
                    if (staff.hasPermission("eoncommands.staffchat"))
                        staff.sendMessage(Utils.getPrefix("moderation")
                                .append(Component.text(e.getPlayer().getName() + " said while muted: "))
                                .append(e.originalMessage().color(TextColor.color(192, 192, 192))));
                }
                e.setCancelled(true);
                return;
            }
        }

        if (!p.hasPermission("eoncommands.staffchat") && isChatLocked) {
            p.sendMessage(Utils.getPrefix("moderation")
                    .append(Component.text("Chat is locked. Please wait while we resolve the conflict. Thank you for your patience")
                            .color(TextColor.color(255, 0, 0))));
            e.setCancelled(true);
            return;
        }
        e.renderer(customRender);
        e.renderer().render(p, p.displayName(), e.originalMessage(), server);
        //Utils.chat("&7[&r" + prefix + "&r&7] >> " + p.displayName() + ": " + e.originalMessage());
    }

    private void initializeGroupColors() {
        groupColors = new HashMap<>();
        groupColors.put("member", TextColor.color(224, 224, 224));
        groupColors.put("traveler", TextColor.color(0, 255, 255));
        groupColors.put("explorer", TextColor.color(0, 102, 204));
        groupColors.put("ranger", TextColor.color(102, 0, 204));
        groupColors.put("spaceman", TextColor.color(255, 255, 0));
        groupColors.put("astronaut", TextColor.color(255, 128, 0));
        groupColors.put("mythic", TextColor.color(255, 0, 0));
        groupColors.put("builder", TextColor.color(0, 204, 0));
        groupColors.put("mod", TextColor.color(102, 0, 204));
        groupColors.put("admin", TextColor.color(204, 0, 0));
        groupColors.put("owner", TextColor.color(0, 128, 255));
    }

    private ChatRenderer getNewRenderer() {
        return (player, sourceDisplayName, message, audience) -> {
            // Reaching into LuckPerms API to get the Prefix for the Player that has chat
            User user = lp.getUserManager().getUser(player.getUniqueId());
            ImmutableContextSet contextSet = lp.getContextManager().getContext(user).orElseGet(lp.getContextManager()::getStaticContext);
            CachedMetaData cachedMetaData = user.getCachedData().getMetaData(QueryOptions.contextual(contextSet));
            String prefix = cachedMetaData.getPrefix();
            if (prefix.equals("default")) prefix = "member";

            return Component.text("[").color(TextColor.color(128, 128, 128))
                    .append(Component.text(StringUtils.capitalize(prefix.toLowerCase()))
                            .color(groupColors.get(prefix.toLowerCase())))
                    .append(Component.text("] ")).append(sourceDisplayName)
                    .append(Component.text(": ")).append(message).color(TextColor.color(192, 192, 192));
        };
    }

    public static void setChatLocked(boolean state) {
        isChatLocked = state;
    }

    public static boolean isChatLocked() {
        return isChatLocked;
    }
}

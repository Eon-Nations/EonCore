package me.squid.eoncore.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.managers.CooldownManager;
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
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class ChatFormatListener implements Listener {

    EonCore plugin;
    LuckPerms lp = EonCore.getPerms();
    HashMap<String, TextColor> groupColors;


    public ChatFormatListener(EonCore plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        initializeGroupColors();
    }

    @SuppressWarnings("ConstantConditions")
    @EventHandler (priority = EventPriority.HIGH)
    public void onChatSend(AsyncChatEvent e) {
        Player p = e.getPlayer();
        Server server = Bukkit.getServer();
        CooldownManager cooldownManager = AdminMenuManager.cooldownManager;

        if (cooldownManager.hasCooldown(p.getUniqueId())) {
            p.sendMessage(Utils.getPrefix("").append(Component.text("You are muted").color(TextColor.color(255, 0, 0))), MessageType.CHAT);
            e.setCancelled(true);
            return;
        }
        // Reaching into LuckPerms API to get the Prefix for the Player that has chat
        User user = lp.getUserManager().getUser(p.getUniqueId());
        ImmutableContextSet contextSet = lp.getContextManager().getContext(user).orElseGet(lp.getContextManager()::getStaticContext);
        CachedMetaData cachedMetaData = user.getCachedData().getMetaData(QueryOptions.contextual(contextSet));
        String prefix = cachedMetaData.getPrefix();

        if (prefix.equals("default")) prefix = "member";

        Component message = Component.text("[").color(TextColor.color(128, 128, 128))
                .append(Component.text(StringUtils.capitalize(prefix.toLowerCase()))
                        .color(groupColors.get(prefix.toLowerCase())))
                .append(Component.text("] ").append(p.displayName())).append(Component.text(": "))
                .append(e.originalMessage()).color(TextColor.color(192, 192, 192));
        server.sendMessage(message);
        e.setCancelled(true);
        //Utils.chat("&7[&r" + prefix + "&r&7] >> " + p.displayName() + ": " + e.originalMessage());
    }

    private void initializeGroupColors() {
        groupColors = new HashMap<>();
        groupColors.put("member", TextColor.color(224, 224, 224));
        groupColors.put("traveler", TextColor.color(0, 128, 255));
        groupColors.put("explorer", TextColor.color(127, 0, 255));
        groupColors.put("ranger", TextColor.color(0, 204, 0));
        groupColors.put("spaceman", TextColor.color(102, 178, 255));
        groupColors.put("astronaut", TextColor.color(255, 128, 0));
        groupColors.put("mythic", TextColor.color(0, 153, 0));
        groupColors.put("builder", TextColor.color(0, 204, 0));
        groupColors.put("mod", TextColor.color(102, 0, 204));
        groupColors.put("admin", TextColor.color(204, 0, 0));
        groupColors.put("owner", TextColor.color(0, 128, 255));
    }
}

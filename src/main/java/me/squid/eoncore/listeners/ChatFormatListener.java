package me.squid.eoncore.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.context.ImmutableContextSet;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatFormatListener implements Listener {

    EonCore plugin;
    LuckPerms lp = EonCore.getPerms();

    public ChatFormatListener(EonCore plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @SuppressWarnings("ConstantConditions")
    @EventHandler (priority = EventPriority.HIGH)
    public void onChatSend(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        // Reaching into LuckPerms API to get the Prefix for the Player that has chat
        User user = lp.getUserManager().getUser(p.getUniqueId());
        ImmutableContextSet contextSet = lp.getContextManager().getContext(user).orElseGet(lp.getContextManager()::getStaticContext);
        CachedMetaData cachedMetaData = user.getCachedData().getMetaData(QueryOptions.contextual(contextSet));
        String prefix = cachedMetaData.getPrefix();

        e.setFormat(Utils.chat("&7[&r" + prefix + "&r&7] >> " + p.getDisplayName() + ": " + e.getMessage()));
    }
}

package me.squid.eoncore.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static java.time.Duration.ofSeconds;
import static me.squid.eoncore.messaging.Messaging.fromFormatString;
import static me.squid.eoncore.utils.Utils.createKitItem;

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
        setSleepingIgnored(p);
        if (!p.hasPlayedBefore()) {
            p.teleportAsync(Utils.getSpawnLocation());
            givePlayerStarterKit(p);
        }
    }

    @EventHandler
    public void sendLeaveMessage(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.quitMessage(leaveMessage(p));
    }

    private void setSleepingIgnored(Player player) {
        if (player.isOp()) {
            player.setSleepingIgnored(true);
        }
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

    private static void givePlayerStarterKit(Player p) {
        List<ItemStack> itemsToGive = List.of(
            createKitItem(Material.WOODEN_SWORD, "&7Stick"),
            createKitItem(Material.WOODEN_PICKAXE, "&7Pick"),
            createKitItem(Material.WOODEN_AXE, "&7Wood"),
            createKitItem(Material.WOODEN_SHOVEL, "&7Spoon"),
            new ItemStack(Material.COOKED_BEEF, 8)
        );
        itemsToGive.forEach(p.getInventory()::addItem);
    }
}

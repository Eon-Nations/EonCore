package me.squid.eoncore.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class JoinLeaveListener implements Listener {

    EonCore plugin;

    public JoinLeaveListener(EonCore plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void JoinMessage(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.hasPlayedBefore()) {
            sendPlayerTitle(p);
            e.setJoinMessage(getJoinMessage(p));
            if (p.isOp()) {
                p.setSleepingIgnored(true);
            }
        } else {
            e.setJoinMessage(getJoinMessage(p));
            p.teleport(Utils.getSpawnLocation());
            givePlayerStarterKit(p);
        }
    }

    @EventHandler
    public void LeaveMessage(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage(Utils.chat(plugin.getConfig().getString("Leave-Message")
        .replace("<player>", p.getName())));
    }

    private void sendPlayerTitle(Player p) {
        String subTitle = p.hasPlayedBefore() ? Utils.chat("&bWelcome back!") : Utils.chat("&bWelcome " + p.getName());
        p.sendTitle(Utils.chat("&5&lEon Nations"), subTitle, 20, 40, 20);
    }

    private String getJoinMessage(Player p) {
        if (p.hasPlayedBefore()) {
            return Utils.chat(plugin.getConfig().getString("Join-Message")
                    .replace("<player>", p.getName()));
        } else return Utils.chat(plugin.getConfig().getString("Welcome-Message")
                    .replace("<player>", p.getName()));
    }

    private void givePlayerStarterKit(Player p) {
        List<ItemStack> itemsToGive = new ArrayList<>();
        itemsToGive.add(Utils.createKitItem(Material.WOODEN_SWORD, 1, "&7Stick", null, null));
        itemsToGive.add(Utils.createKitItem(Material.WOODEN_PICKAXE, 1, "&7Pick", null, null));
        itemsToGive.add(Utils.createKitItem(Material.WOODEN_AXE, 1, "&7Wood", null, null));
        itemsToGive.add(Utils.createKitItem(Material.WOODEN_SHOVEL, 1, "&7Spoon", null, null));
        itemsToGive.add(new ItemStack(Material.COOKED_BEEF, 8));

        itemsToGive.forEach(p.getInventory()::addItem);
    }
}

package me.squid.eoncore.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.title.Title;
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
        if (p.hasPlayedBefore()){
            Title title = Title.title(Utils.chat("&5&lEon Nations"), Utils.chat("&bWelcome back!"));
            e.joinMessage(Utils.chat(plugin.getConfig().getString("Join-Message")
            .replace("<player>", p.getName())));
            p.showTitle(title);
            if (p.isOp()) {
                p.setSleepingIgnored(true);
                p.setAffectsSpawning(false);
            }
        } else {
            Title title = Title.title(Utils.chat("&5&lEon Nations"), Utils.chat("&bWelcome " + p.getName()));
            e.joinMessage(Utils.chat(plugin.getConfig().getString("Welcome-Message")
                    .replace("<player>", p.getName())));
            p.teleportAsync(Utils.getSpawnLocation());
            givePlayerStarterKit(p);
            p.showTitle(title);
        }
    }

    @EventHandler
    public void LeaveMessage(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.quitMessage(Utils.chat(plugin.getConfig().getString("Leave-Message")
        .replace("<player>", p.getName())));
    }

    private void givePlayerStarterKit(Player p) {
        List<ItemStack> itemsToGive = new ArrayList<>();
        itemsToGive.add(Utils.createKitItem(Material.WOODEN_SWORD, 1, "&7Stick", null, null));
        itemsToGive.add(Utils.createKitItem(Material.WOODEN_PICKAXE, 1, "&7Pick", null, null));
        itemsToGive.add(Utils.createKitItem(Material.WOODEN_AXE, 1, "&7Wood", null, null));
        itemsToGive.add(Utils.createKitItem(Material.WOODEN_SHOVEL, 1, "&7Spoon", null, null));
        itemsToGive.add(new ItemStack(Material.COOKED_BEEF, 8));

        for (ItemStack item : itemsToGive) {
            p.getInventory().addItem(item);
        }
    }
}

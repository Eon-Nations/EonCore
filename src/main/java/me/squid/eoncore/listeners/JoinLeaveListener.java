package me.squid.eoncore.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.sql.VotesManager;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JoinLeaveListener implements Listener {

    EonCore plugin;
    VotesManager votesManager;

    public JoinLeaveListener(EonCore plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        votesManager = new VotesManager(plugin);
    }

    @EventHandler
    public void JoinMessage(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.hasPlayedBefore()){
            e.joinMessage(Component.text(Utils.chat(plugin.getConfig().getString("Join-Message")
            .replace("<player>", p.getName()))));
            p.sendTitle(Utils.chat("&5&lEon Survival"), Utils.chat("&bWelcome back!"), 30, 30, 30);
            if (p.isOp()) {
                p.setSleepingIgnored(true);
                p.setAffectsSpawning(false);
            }
        } else {
            e.joinMessage(Component.text(Utils.chat(Objects.requireNonNull(plugin.getConfig().getString("Welcome-Message"))
            .replace("<player>", p.getName()))));
            p.teleportAsync(getSpawnLoc());
            givePlayerStarterKit(p);
            p.sendTitle(Utils.chat("&5&lEon Survival"), Utils.chat("&bWelcome " + p.getName()) + "!", 30, 30, 30);
        }
        createSQLPlayers(p);
    }

    @EventHandler
    public void LeaveMessage(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage(Utils.chat(plugin.getConfig().getString("Leave-Message")
        .replace("<player>", p.getName())));
    }

    private Location getSpawnLoc() {
        double x = 0;
        double y = 64;
        double z = 0;
        return new Location(Bukkit.getWorld("spawn"), x, y, z);
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

    private void createSQLPlayers(Player p) {
        votesManager.createPlayer(p);
    }
}

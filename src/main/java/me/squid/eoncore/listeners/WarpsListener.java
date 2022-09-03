package me.squid.eoncore.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.menus.MobArenaGUI;
import me.squid.eoncore.tasks.WarpTeleportTask;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class WarpsListener implements Listener {

    EonCore plugin;
    Location utilities;
    Location crates;
    Location endPortal;
    Location chilis;
    MobArenaGUI mobArenaGUI = new MobArenaGUI();

    public WarpsListener(EonCore plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        initializeWarps();
    }

    @SuppressWarnings("ConstantConditions")
    @EventHandler
    public void onWarp(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equals(Utils.chat("&5&lEon Warps"))) {
            String name = e.getCurrentItem().getItemMeta().getDisplayName();
            switch (e.getCurrentItem().getType()) {
                case ANVIL -> {
                    p.closeInventory();
                    teleportPlayerToWarp(p, utilities, name);
                }
                case CHEST -> {
                    p.closeInventory();
                    teleportPlayerToWarp(p, crates, name);
                }
                case END_PORTAL_FRAME -> {
                    p.closeInventory();
                    teleportPlayerToWarp(p, endPortal, name);
                }
                case ZOMBIE_HEAD -> {
                    p.closeInventory();
                    Bukkit.dispatchCommand(p, "ma join");
                }
                case BREAD -> {
                    p.closeInventory();
                    teleportPlayerToWarp(p, chilis, name);
                }
                default -> p.closeInventory();
            }
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
            e.setCancelled(true);
        }
    }

    public void teleportPlayerToWarp(Player player, Location location, String warpName) {
        if (player.hasPermission("eoncommands.warp.cooldown.bypass")) {
            new WarpTeleportTask(plugin, location, player, warpName).runTaskAsynchronously(plugin);
        } else {
            player.sendMessage(Utils.getPrefix("nations") + Utils.chat("&7Teleporting in 3 seconds..."));
            new WarpTeleportTask(plugin, location, player, warpName).runTaskLaterAsynchronously(plugin, plugin.getConfig().getLong("Warp-Delay") * 20);
        }
    }

    public void initializeWarps() {
        utilities = new Location(Bukkit.getWorld("spawn_void"), 64.5, 99, 25.5, 180, 0);
        crates = new Location(Bukkit.getWorld("spawn_void"), -22.5 ,87, -14.5, -130, 0);
        endPortal = new Location(Bukkit.getWorld("world"), -1381.5, -27, -1131.5, 0, 180);
        chilis = new Location(Bukkit.getWorld("spawn_void"), -54.5, 86, -3.5, 90, 0);
    }
}

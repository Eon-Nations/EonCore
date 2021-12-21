package me.squid.eoncore.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.menus.MobArenaGUI;
import me.squid.eoncore.tasks.WarpTeleportTask;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class WarpsListener implements Listener {

    EonCore plugin;
    Location utilities, crates, market, endPortal, chilis, mine;
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

        if (e.getView().title().equals(Utils.chat("&5&lEon Warps"))) {
            String name = e.getCurrentItem().getItemMeta().getDisplayName();
            switch (e.getCurrentItem().getType()) {
                case ANVIL:
                    p.closeInventory();
                    teleportPlayerToWarp(p, utilities, name);
                    break;
                case CHEST:
                    p.closeInventory();
                    teleportPlayerToWarp(p, crates, name);
                    break;
                case END_PORTAL_FRAME:
                    p.closeInventory();
                    //teleportPlayerToWarp(p, endPortal, name);
                    p.sendMessage(Utils.getPrefix("nations")
                            .append(Component.text("End Portal will unlock once the end portal in the overworld is discovered.")
                                    .color(TextColor.color(160, 160, 160))));
                    break;
                case ZOMBIE_HEAD:
                    p.closeInventory();
                    p.openInventory(mobArenaGUI.MainGUI());
                    break;
                case BREAD:
                    p.closeInventory();
                    teleportPlayerToWarp(p, chilis, name);
                    break;
            }
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
            e.setCancelled(true);
        }
    }

    public void teleportPlayerToWarp(Player player, Location location, String warpName) {
        if (player.hasPermission("eoncommands.warp.cooldown.bypass")) {
            new WarpTeleportTask(plugin, location, player, warpName).runTaskAsynchronously(plugin);
        } else {
            player.sendMessage(Utils.getPrefix("nations").append(Utils.chat("&7Teleporting in 3 seconds...")));
            new WarpTeleportTask(plugin, location, player, warpName).runTaskLaterAsynchronously(plugin, plugin.getConfig().getLong("Warp-Delay") * 20);
        }
    }

    public void initializeWarps() {
        utilities = new Location(Bukkit.getWorld("spawn_void"), 64.5, 99, 25.5, 180, 0);
        crates = new Location(Bukkit.getWorld("spawn_void"), -22.5 ,87, -14.5, -130, 0);
        endPortal = new Location(Bukkit.getWorld("world"), 21830, 23, 10306, 90, 0);
        chilis = new Location(Bukkit.getWorld("spawn_void"), -54.5, 86, -3.5, 90, 0);

    }
}

package me.squid.eoncore.menus;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class WarpsGUI implements StaleInventory {

    Map<String, Location> warps = initializeWarps();

    private String formatWithGreen(String format) {
        return "<green>" + format + "</green>";
    }

    @Override
    public Inventory buildInventory() {
        return new MenuBuilder().createMenu("<purple>Eon Warps</purple>", 27)
                .addItem(Material.BREAD, 12, formatWithGreen("Chilis"))
                .addItem(Material.ANVIL, 13, formatWithGreen("Utilities"))
                .addItem(Material.CHEST, 14, formatWithGreen("Crates"))
                .addItem(Material.END_PORTAL_FRAME, 15, formatWithGreen("End Portal"))
                .addItem(Material.ZOMBIE_HEAD, 16, formatWithGreen("Mob Arena"))
                .makeDummySlots()
                .completeInventory();
    }

    @Override
    public void clickEvent(Player clicker, ItemStack currentItem) {
        clicker.playSound(clicker.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
        String warpName = warpLocation(currentItem.getType());
        if (warpName.equals("mobArena")) {
            Bukkit.dispatchCommand(clicker, "ma join");
            return;
        }
        if (!warpName.equals("other")) {
            Location warpLocation = warps.getOrDefault(warpName, clicker.getLocation());
            clicker.teleportAsync(warpLocation);
        }
    }

    private String warpLocation(Material type) {
        return switch (type) {
            case BREAD -> "chilis";
            case ANVIL -> "utilities";
            case CHEST -> "crates";
            case END_PORTAL_FRAME -> "endPortal";
            case ZOMBIE_HEAD -> "mobArena";
            default -> "other";
        };
    }

    public Map<String, Location> initializeWarps() {
        World world = Bukkit.getWorld("spawn_void");
        return Map.of(
                "utilities", new Location(world, 64.5, 99, 25.5, 180, 0),
                "crates", new Location(world, -22.5 ,87, -14.5, -130, 0),
                "endPortal", new Location(world, -1381.5, -27, -1131.5, 0, 180),
                "chilis", new Location(world, -54.5, 86, -3.5, 90, 0)
        );
    }
}

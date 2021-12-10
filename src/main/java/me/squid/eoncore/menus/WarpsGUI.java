package me.squid.eoncore.menus;

import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class WarpsGUI {

    public Inventory SelectWarps() {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.chat("&5&lEon Warps"));

        Utils.createItem(inv, Material.BREAD, 1, 12, "&aChilis");
        Utils.createItem(inv, Material.ANVIL, 1, 13, "&bUtilities");
        Utils.createItem(inv, Material.CHEST, 1, 14, "&bCrates");
        Utils.createItem(inv, Material.END_PORTAL_FRAME, 1, 15, "&aEnd Portal");
        Utils.createItem(inv, Material.ZOMBIE_HEAD, 1, 16, "&aMob Arena");
        Utils.makeDummySlots(inv);

        return inv;
    }

}

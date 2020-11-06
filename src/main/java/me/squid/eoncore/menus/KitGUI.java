package me.squid.eoncore.menus;


import me.squid.eoncore.kit.KitManager;
import me.squid.eoncore.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class KitGUI {

    public Inventory Select() {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.chat("&5&lEon Kits"));

        for (int i = 10; i < KitManager.kits.size() + 10; i++) {
            Utils.createItem(inv, Material.ENCHANTED_BOOK, 1, i + 1, "&b" + StringUtils.capitalize(KitManager.kits.get(i - 10).getName()), KitManager.kits.get(i - 10).getDescription());
        }
        Utils.makeDummySlots(inv);

        return inv;
    }

    public Inventory ConfirmMenu(String name) {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.chat("&a&lConfirm Kit"));

        Utils.createItem(inv, Material.EMERALD_BLOCK, 1, 12, "&a&lConfirm");
        Utils.createItem(inv, Material.PAPER, 1, 14, name);
        Utils.createItem(inv, Material.BARRIER, 1, 16, "&4&lExit");

        return inv;
    }

}

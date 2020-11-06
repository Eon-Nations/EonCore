package me.squid.eoncore.menus;

import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class MobArenaGUI {

    public Inventory MainGUI() {
        Inventory inv = Bukkit.createInventory(null, 9, Utils.chat("&a&lMob Arena Selection"));

        Utils.createItem(inv, Material.ZOMBIE_HEAD, 1, 4, "&b&lHanger");
        Utils.createItem(inv, Material.SKELETON_SKULL, 1, 6, "&5&lHard &r&f(Coming Soon)");
        Utils.makeDummySlots(inv);

        return inv;
    }

}

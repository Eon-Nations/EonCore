package me.squid.eoncore.menus;

import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class WildMenu {

    public Inventory MainGUI() {
        Inventory inv = Bukkit.createInventory(null, 27, Component.text("Wild").color(TextColor.color(0, 204, 0)));

        Utils.createItem(inv, Material.JUNGLE_LEAVES, 1, 12, Component.text("Main World").color(TextColor.color(0, 102, 0)));
        Utils.createItem(inv, Material.IRON_ORE, 1, 16, Component.text("Resource World").color(TextColor.color(255, 128, 0)));
        Utils.makeDummySlots(inv);

        return inv;
    }

}

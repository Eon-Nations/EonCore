package me.squid.eoncore.menus;

import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class MenuBuilder {
    private Inventory inventory;

    public MenuBuilder createMenu(String name, int size) {
        inventory = Bukkit.createInventory(null, size, Component.text(name));
        return this;
    }

    public MenuBuilder addItem(Material material, int invSlot, String name, String... lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        List<Component> finalLore = Arrays.stream(lore)
                .map(Utils::translateHex)
                .map(Component::text)
                .map(Component::asComponent)
                .toList();
        meta.displayName(Component.text(Utils.translateHex(name)));
        meta.lore(finalLore);
        item.setItemMeta(meta);
        inventory.setItem(invSlot - 1, item);
        return this;
    }

    public MenuBuilder makeDummySlots() {
        Utils.makeDummySlots(inventory);
        return this;
    }

    public Inventory completeInventory() {
        return inventory;
    }
}

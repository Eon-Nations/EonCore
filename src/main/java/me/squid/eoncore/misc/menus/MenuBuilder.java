package me.squid.eoncore.misc.menus;

import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.misc.utils.ServerHolder;
import me.squid.eoncore.misc.utils.Utils;
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
        ServerHolder holder = new ServerHolder();
        try {
            inventory = Bukkit.createInventory(holder, size, Messaging.fromFormatString(name));
        } catch (Exception e) {
            // This is for the testing suite (Component titles are not implemented yet)
            inventory = Bukkit.createInventory(holder, size, name);
        }
        return this;
    }

    public MenuBuilder addItem(Material material, int invSlot, String name, String... lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        List<Component> finalLore = Arrays.stream(lore)
                .map(Messaging::fromFormatString)
                .toList();
        meta.displayName(Messaging.fromFormatString(name));
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

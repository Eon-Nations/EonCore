package me.squid.eoncore.menus;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface StaleInventory {

    Inventory buildInventory();
    void clickEvent(Player clicker, ItemStack currentItem);
}
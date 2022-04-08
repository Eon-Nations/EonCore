package me.squid.eoncore.managers;

import me.squid.eoncore.menus.HelpGUI;
import me.squid.eoncore.menus.StaleInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Predicate;

public class InventoryManager implements Listener {
    private final List<StaleInventory> registeredInventories;

    public InventoryManager() {
        registeredInventories = List.of(
                new HelpGUI()
        );
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        Predicate<StaleInventory> staleInventoryCheck = staleInventory -> staleInventory.buildInventory().equals(e.getClickedInventory());
        registeredInventories.stream()
                    .filter(staleInventoryCheck)
                    .findFirst()
                    .ifPresent(staleInventory -> {
                        Player clicker = (Player) e.getWhoClicked();
                        ItemStack clickedItem = e.getCurrentItem();
                        staleInventory.clickEvent(clicker, clickedItem);
                        e.setCancelled(true);
                    });
    }

}

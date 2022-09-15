package me.squid.eoncore.managers;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.menus.HelpGUI;
import me.squid.eoncore.menus.StaleInventory;
import me.squid.eoncore.menus.WarpsGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class InventoryManager implements Listener {
    private static final Map<String, StaleInventory> registeredInventories = createMap();
    private static final BiMap<Inventory, StaleInventory> inventoryMap = inventoryMap();

    private InventoryManager(EonCore plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public static void registerInventories(EonCore plugin) {
        new InventoryManager(plugin);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        inventoryMap.keySet().stream()
                    .filter(inventoryMap::containsKey)
                    .findFirst()
                    .ifPresent(cancelEvent(e));
    }

    public static Inventory staleInventory(String name) {
        var flippedMap = inventoryMap().inverse();
        StaleInventory staleInventory = registeredInventories.get(name);
        return flippedMap.get(staleInventory);
    }

    private static BiMap<Inventory, StaleInventory> inventoryMap() {
        var map = registeredInventories.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getValue().buildInventory(), Map.Entry::getValue));
        return HashBiMap.create(map);
    }

    private static Map<String, StaleInventory> createMap() {
        HelpGUI helpGUI = new HelpGUI();
        WarpsGUI warpsGUI = new WarpsGUI();
        return Map.of("help", helpGUI,
                "warps", warpsGUI);
    }

    private Consumer<Inventory> cancelEvent(InventoryClickEvent e) {
        Player clicker = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();
        e.setCancelled(true);
        return inventory -> clickEvent(clicker, clickedItem, inventory);
    }

    private void clickEvent(Player clicker, ItemStack clickedItem, Inventory inventory) {
        StaleInventory staleInventory = inventoryMap.get(inventory);
        staleInventory.clickEvent(clicker, clickedItem);
    }
}

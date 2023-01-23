package me.squid.eoncore.misc.managers;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.misc.menus.HelpGUI;
import me.squid.eoncore.misc.menus.StaleInventory;
import me.squid.eoncore.misc.menus.WarpsGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.stream.Collectors;

public class InventoryManager implements Listener {
    private static final Map<String, StaleInventory> registeredInventories = createMap();
    private static final BiMap<Inventory, StaleInventory> inventoryMap = inventoryMap();
    EonCore plugin;

    private InventoryManager(EonCore plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public static void registerInventories(EonCore plugin) {
        new InventoryManager(plugin);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if (inventoryMap.containsKey(e.getClickedInventory())) {
            Player clicker = (Player) e.getWhoClicked();
            ItemStack clickedItem = e.getCurrentItem();
            StaleInventory inventory = inventoryMap.get(e.getClickedInventory());
            inventory.clickEvent(clicker, clickedItem, plugin);
            e.setResult(Event.Result.DENY);
        }
    }

    public static Inventory staleInventory(String name) {
        StaleInventory staleInventory = registeredInventories.get(name);
        return inventoryMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(staleInventory))
                .map(Map.Entry::getKey)
                .findFirst().orElseThrow();
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
}

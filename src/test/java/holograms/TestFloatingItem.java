package holograms;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.holograms.FloatingItem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;

import mockbukkit.TestUtility;

public class TestFloatingItem extends TestUtility {
    
    private static final double ALLOWED_RADIUS = 5.0;


    @Test
    @DisplayName("The item that spawns has an item stack attached to it")
    void testSpawnMaterial() {
        PlayerMock jim = server.addPlayer("Jim");
        Material expectedMaterial = Material.DIRT;
        var floatingItem = new FloatingItem(jim.getLocation(), expectedMaterial);
        Collection<Item> floatingItems = jim.getWorld().getNearbyEntitiesByType(Item.class, jim.getLocation(), ALLOWED_RADIUS);
        assertEquals(1, floatingItems.size()); 
        Item item = (Item) floatingItems.toArray()[0];
        assertEquals(expectedMaterial, item.getItemStack().getType());
        floatingItem.close();
    }

    @Test
    @DisplayName("The item cannot be picked up by players")
    void testPlayerPickup() {
        PlayerMock jim = server.addPlayer();
        FloatingItem floatingItem = new FloatingItem(jim.getLocation(), Material.DIRT);
        Collection<Item> floatingItems = jim.getWorld().getNearbyEntitiesByType(Item.class, jim.getLocation(), ALLOWED_RADIUS);
        Item item = (Item) floatingItems.toArray()[0];
        EntityPickupItemEvent event = new EntityPickupItemEvent(jim, item, 0);
        server.getPluginManager().callEvent(event);
        floatingItem.close();
        assertEquals(0, jim.getInventory().getSize());
    }

    @Test
    @DisplayName("AutoClosable will close out the item and it will disappear")
    void testItemClose() {
        PlayerMock jim = server.addPlayer();
        try (FloatingItem item = new FloatingItem(jim.getLocation(), Material.DIRT)) {
            // Item is alive and well here
            Collection<Item> floatingItems = jim.getWorld().getNearbyEntitiesByType(Item.class, jim.getLocation(), ALLOWED_RADIUS);
            assertEquals(1, floatingItems.size());
        }
        Collection<Item> floatingItems = jim.getWorld().getNearbyEntitiesByType(Item.class, jim.getLocation(), ALLOWED_RADIUS);
        assertEquals(0, floatingItems.size());
    }
}


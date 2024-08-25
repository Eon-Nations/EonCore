package node;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.eonnations.eoncore.node.Node;
import com.eonnations.eoncore.node.Resource;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;

public class TestNode extends TestUtility {
    
    @Test
    @DisplayName("When node is created, it spawns both the output and the resource type")
    void testSpawnNode() {
        PlayerMock jim = server.addPlayer();
        Node node = new Node(jim.getLocation(), Resource.IRON); 
        Collection<ArmorStand> holograms = jim.getLocation().getNearbyEntitiesByType(ArmorStand.class, 5.0);
        Collection<Item> floatingItems = jim.getLocation().getNearbyEntitiesByType(Item.class, 5.0);
        assertEquals(2, holograms.size());
        assertEquals(1, floatingItems.size());
    }

    @Test
    @DisplayName("The floating item is of the resource type given")
    void testFloatingItemMaterial() {
        PlayerMock jim = server.addPlayer();
        Node node = new Node(jim.getLocation(), Resource.GOLD);
        Collection<Item> floatingItems = jim.getLocation().getNearbyEntitiesByType(Item.class, 5.0);
        Item item = (Item) floatingItems.toArray()[0];
        assertEquals(Material.GOLD_INGOT, item.getItemStack().getType());
    }

    @Test
    @DisplayName("Remove will kill all entities that are related to the node")
    void testRemove() {
        PlayerMock jim = server.addPlayer();
        Node node = new Node(jim.getLocation(), Resource.DIAMOND);
        node.remove();
        Collection<ArmorStand> holograms = jim.getLocation().getNearbyEntitiesByType(ArmorStand.class, 5.0);
        Collection<Item> floatingItems = jim.getLocation().getNearbyEntitiesByType(Item.class, 5.0);
        assertEquals(0, holograms.size());
        assertEquals(0, floatingItems.size());
    }
}

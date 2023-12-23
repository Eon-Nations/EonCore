package holograms;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.holograms.Hologram;
import mockbukkit.TestUtility;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.ArmorStand;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestHologram extends TestUtility {

    @Test
    @DisplayName("Creating a hologram spawns an entity with the correct name")
    void testHologramSpawn() {
        PlayerMock jim = server.addPlayer("Jim");
        String expectedLine = "Nice one Jim!";
        Hologram hologram = new Hologram(expectedLine, jim.getLocation());
        Collection<ArmorStand> holograms = jim.getWorld().getNearbyEntitiesByType(ArmorStand.class, jim.getLocation(), 10.0);
        assertEquals(1, holograms.size());
        ArmorStand armorStand = holograms.stream().toList().get(0);
        assertTrue(armorStand.isCustomNameVisible());
        assertEquals(expectedLine, MiniMessage.miniMessage().serialize(armorStand.customName()));
    }

    @Test
    @Disabled("Will re-enable once I figure out how to get the player to attack the armor stand")
    @DisplayName("Holograms can't be destroyed by players")
    void testHologramInteract() {
        PlayerMock jim = server.addPlayer("Jim");
        String expectedLine = "Nice one Jim!";
        Hologram hologram = new Hologram(expectedLine, jim.getLocation());
        Collection<ArmorStand> holograms = jim.getWorld().getNearbyEntitiesByType(ArmorStand.class, jim.getLocation(), 10.0);
        ArmorStand armorStand = holograms.stream().toList().get(0);
        double health = armorStand.getHealth();
        jim.attack(armorStand);
        assertTrue(armorStand.isInvulnerable());
        assertEquals(health, armorStand.getHealth());
    }

    @Test
    @DisplayName("Holograms are removed when the close() method is called")
    void testRemoveHologram() {
        PlayerMock jim = server.addPlayer("Jim");
        Hologram hologram = new Hologram("Testing Line", jim.getLocation());
        Collection<ArmorStand> holograms = jim.getWorld().getNearbyEntitiesByType(ArmorStand.class, jim.getLocation(), 10.0);
        assertEquals(1, holograms.size());
        hologram.close();
        Collection<ArmorStand> allHolograms = jim.getWorld().getNearbyEntitiesByType(ArmorStand.class, jim.getLocation(), 10.0);
        assertEquals(0, allHolograms.size());
    }
}

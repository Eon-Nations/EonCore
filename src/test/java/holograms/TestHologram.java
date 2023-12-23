package holograms;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import io.vavr.collection.List;
import me.squid.eoncore.holograms.Hologram;
import mockbukkit.TestUtility;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.ArmorStand;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static me.squid.eoncore.messaging.Messaging.fromFormatString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestHologram extends TestUtility {

    private static final List<Component> armorStandTitle = List.of(fromFormatString("Nice one Jim!"));
    private static final double ALLOWED_RADIUS = 5.0;

    @Test
    @DisplayName("Creating a hologram spawns an entity with the correct name")
    void testHologramSpawn() {
        PlayerMock jim = server.addPlayer("Jim");
        Hologram hologram = new Hologram(armorStandTitle, jim.getLocation());
        Collection<ArmorStand> holograms = jim.getWorld().getNearbyEntitiesByType(ArmorStand.class, jim.getLocation(), ALLOWED_RADIUS);
        assertEquals(1, holograms.size());
        ArmorStand armorStand = holograms.stream().toList().get(0);
        assertTrue(armorStand.isCustomNameVisible());
        assertEquals(armorStandTitle.get(0), armorStand.customName());
    }

    @Test
    @Disabled("Will re-enable once I figure out how to get the player to attack the armor stand")
    @DisplayName("Holograms can't be destroyed by players")
    void testHologramInteract() {
        PlayerMock jim = server.addPlayer("Jim");
        Hologram hologram = new Hologram(armorStandTitle, jim.getLocation());
        Collection<ArmorStand> holograms = jim.getWorld().getNearbyEntitiesByType(ArmorStand.class, jim.getLocation(), ALLOWED_RADIUS);
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
        Hologram hologram = new Hologram(armorStandTitle, jim.getLocation());
        Collection<ArmorStand> holograms = jim.getWorld().getNearbyEntitiesByType(ArmorStand.class, jim.getLocation(), ALLOWED_RADIUS);
        assertEquals(1, holograms.size());
        hologram.close();
        Collection<ArmorStand> allHolograms = jim.getWorld().getNearbyEntitiesByType(ArmorStand.class, jim.getLocation(), ALLOWED_RADIUS);
        assertEquals(0, allHolograms.size());
    }

    @Test
    @DisplayName("Holograms with multiple lines don't spawn on the same Y coordinate")
    void testMultiLineY() {
        PlayerMock jim = server.addPlayer();
        List<Component> lines = armorStandTitle.prepend(fromFormatString("Line 2")).prepend(fromFormatString("Line 3"));
        Hologram hologram = new Hologram(lines, jim.getLocation());
        Collection<ArmorStand> holograms = jim.getWorld().getNearbyEntitiesByType(ArmorStand.class, jim.getLocation(), ALLOWED_RADIUS);
        assertEquals(3, holograms.size());
        List<Double> yCords = List.ofAll(holograms).map(stand -> stand.getLocation().getY());
        assertEquals(yCords.distinct().size(), yCords.size());
    }

    @Test
    @DisplayName("Holograms with multiple lines spawn armor stands with all lines of the hologram")
    void testMultiLineNames() {
        PlayerMock jim = server.addPlayer();
        List<Component> lines = armorStandTitle.prepend(fromFormatString("Line 2")).prepend(fromFormatString("Line 3"));
        Hologram hologram = new Hologram(lines, jim.getLocation());
        Collection<ArmorStand> holograms = jim.getWorld().getNearbyEntitiesByType(ArmorStand.class, jim.getLocation(), ALLOWED_RADIUS);
        assertEquals(3, holograms.size());
        for (ArmorStand stand : holograms) {
            assertTrue(stand.isCustomNameVisible());
            assertTrue(lines.contains(stand.customName()));
        }
    }
}

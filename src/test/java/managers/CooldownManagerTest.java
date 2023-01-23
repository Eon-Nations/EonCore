package managers;

import me.squid.eoncore.misc.managers.Cooldown;
import me.squid.eoncore.misc.managers.CooldownManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

 class CooldownManagerTest {

    private CooldownManager cooldownManager;
    private Cooldown testCooldown;

    @BeforeEach
    void initialize() {
        cooldownManager = new CooldownManager();
        UUID randomUUID = UUID.randomUUID();
        testCooldown = new Cooldown(randomUUID, 1000 * 3, System.currentTimeMillis());
        cooldownManager.add(testCooldown);
    }

    @Test
    void addTest() {
        assertNotNull(cooldownManager.getCooldown(testCooldown.uuid()));
        assertEquals(testCooldown, cooldownManager.getCooldown(testCooldown.uuid()));
    }

    @Test
    void removeTest() {
        assertEquals(1, cooldownManager.getUUIDsFromCooldownMap().size());
        cooldownManager.remove(testCooldown.uuid());
        assertEquals(0, cooldownManager.getUUIDsFromCooldownMap().size());
    }

    @Test
    void getTest() {
        assertEquals(testCooldown, cooldownManager.getCooldown(testCooldown.uuid()));
    }

    @Test
    void hasTest() {
        assertTrue(cooldownManager.hasCooldown(testCooldown.uuid()));
    }
}

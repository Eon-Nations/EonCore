package managers;

import me.squid.eoncore.misc.managers.Cooldown;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CooldownTest {

    private Cooldown testCooldown;
    private UUID uuid;

    @BeforeEach
    public void setUp() {
        long cooldownSeconds = 600;
        uuid = UUID.randomUUID();
        testCooldown = new Cooldown(uuid, 1000 * cooldownSeconds, System.currentTimeMillis());
    }

    @Test
    void isExpiredTest() {
        assertFalse(testCooldown.isExpired());
        Cooldown lengthCooldown = new Cooldown(uuid, -1, System.currentTimeMillis());
        assertFalse(lengthCooldown.isExpired());
    }

    @Test
    void testToString() {
        assertEquals(testCooldown.time() + ";" + testCooldown.length(), testCooldown.toString());
    }
}

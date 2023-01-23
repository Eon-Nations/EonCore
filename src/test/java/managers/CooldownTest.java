package managers;

import me.squid.eoncore.misc.managers.Cooldown;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class CooldownTest {

    private Cooldown testCooldown;
    private UUID uuid;

    @Before
    public void setUp() {
        long cooldownSeconds = 600;
        uuid = UUID.randomUUID();
        testCooldown = new Cooldown(uuid, 1000 * cooldownSeconds, System.currentTimeMillis());
    }

    @Test
    public void isExpiredTest() {
        Assert.assertFalse("Normal Cooldown Test", testCooldown.isExpired());
        Cooldown lengthCooldown = new Cooldown(uuid, -1, System.currentTimeMillis());
        Assert.assertFalse("Permanent Cooldown Test", lengthCooldown.isExpired());
    }

    @Test
    public void testToString() {
        Assert.assertEquals("Cooldown toString()", testCooldown.time() + ";" + testCooldown.length(), testCooldown.toString());
    }
}

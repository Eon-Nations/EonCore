package managers;

import me.squid.eoncore.misc.managers.Cooldown;
import me.squid.eoncore.misc.managers.CooldownManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class CooldownManagerTest {

    private CooldownManager cooldownManager;
    private Cooldown testCooldown;

    @Before
    public void initialize() {
        cooldownManager = new CooldownManager();
        UUID randomUUID = UUID.randomUUID();
        testCooldown = new Cooldown(randomUUID, 1000 * 3, System.currentTimeMillis());
        cooldownManager.add(testCooldown);
    }

    @Test
    public void addTest() {
        Assert.assertNotNull(cooldownManager.getCooldown(testCooldown.uuid()));
        Assert.assertEquals(testCooldown, cooldownManager.getCooldown(testCooldown.uuid()));
    }

    @Test
    public void removeTest() {
        Assert.assertEquals(1, cooldownManager.getUUIDsFromCooldownMap().size());
        cooldownManager.remove(testCooldown.uuid());
        Assert.assertEquals(0, cooldownManager.getUUIDsFromCooldownMap().size());
    }

    @Test
    public void getTest() {
        Assert.assertEquals(testCooldown, cooldownManager.getCooldown(testCooldown.uuid()));
    }

    @Test
    public void hasTest() {
        Assert.assertTrue(cooldownManager.hasCooldown(testCooldown.uuid()));
    }
}

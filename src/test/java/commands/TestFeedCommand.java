package commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.EonCore;
import mockbukkit.TestUtility;
import org.bukkit.permissions.PermissionAttachment;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class TestFeedCommand {
    private ServerMock server;

    @Before
    public void setup() {
        server = TestUtility.setup();
    }

    private void addPermissionToPlayer(String permission, PlayerMock player) {
        EonCore plugin = MockBukkit.load(EonCore.class);
        plugin.getConfig().set("Feed-Cooldown-Message", "Eat food");
        PermissionAttachment attachment = player.addAttachment(plugin);
        attachment.setPermission(permission, true);
    }

    @Test
    @DisplayName("Feed restores full hunger and includes saturation")
    public void testFullHunger() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer("eoncommands.feed", player);
        player.setFoodLevel(2);
        player.setSaturation(0);
        player.performCommand("feed");
        Assert.assertEquals(player.getFoodLevel(), 20);
        Assert.assertTrue(player.getSaturation() > 10);
    }

    @Test
    @DisplayName("Normal feed permission should be longer than 5 minutes")
    public void testFiveCooldown() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer("eoncommands.feed", player);
        player.performCommand("feed");
        player.setFoodLevel(10);
        long FIVE_MINUTES_IN_TICKS = 5 * 60 * 20;
        server.getScheduler().performTicks(FIVE_MINUTES_IN_TICKS);
        player.performCommand("feed");
        Assert.assertTrue(player.getFoodLevel() < 20);
    }

    @After
    public void tearDown() {
        TestUtility.tearDown();
    }
}

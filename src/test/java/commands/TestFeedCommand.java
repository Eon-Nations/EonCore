package commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.EonCore;
import mockbukkit.TestUtility;
import org.bukkit.permissions.PermissionAttachment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class TestFeedCommand extends TestUtility {

    private void addPermissionToPlayer(String permission, PlayerMock player) {
        EonCore plugin = MockBukkit.load(EonCore.class);
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
        player.performCommand("feed");
        Assert.assertTrue(player.getFoodLevel() < 20);
    }

    @Test
    @DisplayName("Having the feed others permission allows for feeding others")
    public void testOthersPermission() {
        PlayerMock mod  = server.addPlayer("Mod");
        PlayerMock player = server.addPlayer("Jim");
        player.setFoodLevel(10);
        addPermissionToPlayer("eoncommands.feed", mod);
        addPermissionToPlayer("eoncommands.feed.others", mod);
        mod.performCommand("feed Jim");
        Assert.assertEquals(player.getFoodLevel(), 20);
    }
}

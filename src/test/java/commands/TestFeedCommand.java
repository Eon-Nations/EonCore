package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestFeedCommand extends TestUtility {

    @Test
    @DisplayName("Feed restores full hunger and includes saturation")
    public void testFullHunger() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer("eoncommands.feed", player);
        player.setFoodLevel(2);
        player.setSaturation(0);
        player.performCommand("feed");
        assertEquals(20, player.getFoodLevel());
        assertTrue(player.getSaturation() > 10);
    }

    @Test
    @DisplayName("Normal feed permission should be longer than 5 minutes")
    public void testFiveCooldown() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer("eoncommands.feed", player);
        player.performCommand("feed");
        player.setFoodLevel(10);
        player.performCommand("feed");
        assertTrue(player.getFoodLevel() < 20);
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
        assertEquals(20, player.getFoodLevel());
    }
}

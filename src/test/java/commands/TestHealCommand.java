package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class TestHealCommand extends TestUtility {

    @Test
    @DisplayName("Player without other perms cannot heal other players")
    public void testOtherPerms() {
        PlayerMock player = server.addPlayer("Player");
        PlayerMock other = server.addPlayer("Other");
        other.setHealth(10);
        addPermissionToPlayer("eoncommands.heal", player);
        player.performCommand("heal Other");
        Assert.assertEquals(10, other.getHealth(), 0.0);
    }

    @Test
    @DisplayName("Players have a cooldown until the next time they can heal")
    public void testCooldown() {
        PlayerMock player = server.addPlayer("Player");
        addPermissionToPlayer("eoncommands.heal", player);
        player.performCommand("heal");
        player.setHealth(10);
        player.performCommand("heal");
        Assert.assertTrue(player.getHealth() < 20);
    }

}

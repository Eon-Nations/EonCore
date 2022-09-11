package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class TestHealCommand extends TestUtility {
    static final String PERMISSION = "eoncommands.heal";

    @Test
    @DisplayName("Player without other perms cannot heal other players")
    public void testOtherPerms() {
        PlayerMock player = server.addPlayer("Player");
        PlayerMock other = server.addPlayer("Other");
        other.setHealth(10);
        addPermissionToPlayer(PERMISSION, player);
        player.performCommand("heal Other");
        Assert.assertEquals(10, other.getHealth(), 0.0);
    }

    @Test
    @DisplayName("Players have a cooldown until the next time they can heal")
    public void testCooldown() {
        PlayerMock player = server.addPlayer("Player");
        addPermissionToPlayer(PERMISSION, player);
        player.performCommand("heal");
        player.setHealth(10);
        player.performCommand("heal");
        Assert.assertTrue(player.getHealth() < 20);
    }

    @Test
    @DisplayName("Healing self succesfully heals self")
    public void testHeal() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer(PERMISSION, player);
        player.setHealth(10);
        player.performCommand("heal");
        Assertions.assertEquals(20, player.getHealth());
    }

}

package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(10, other.getHealth(), 0.0);
    }

    @Test
    @DisplayName("Healing self succesfully heals self")
    public void testHeal() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer(PERMISSION, player);
        player.setHealth(10);
        player.performCommand("heal");
        assertEquals(20, player.getHealth());
    }

}

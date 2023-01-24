package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestKickCommand extends TestUtility {

    @Test
    @DisplayName("Without a sufficient number of arguments, a usage message is sent")
    public void testNotEnoughArgs() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer("eoncommands.kick", player);
        player.performCommand("kick");
        Assertions.assertTrue(player.nextMessage().contains("Usage"));
    }

    @Test
    @Disabled("Ignore due to MockBukkit not having the method implemented")
    @DisplayName("Player is successfully removed if kicked")
    public void testKick() {
        PlayerMock owner = server.addPlayer("Owner");
        server.addPlayer("Jim");
        addPermissionToPlayer("eoncommands.kick", owner);
        owner.performCommand("kick Jim Nice one jimbo");
        assertEquals(1, server.getPlayerList().getOnlinePlayers().size());
    }

    @Test
    @DisplayName("Invalid player does not get kicked")
    public void testInvalidPlayer() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer("eoncommands.kick", player);
        player.performCommand("kick Invalid Nice one jimbo");
        assertEquals(1, server.getPlayerList().getOnlinePlayers().size());
    }
}

package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDirectMessageCommand extends TestUtility {

    @Test()
    @DisplayName("Typing the command with no arguments does not message")
    public void noArgs() {
        PlayerMock player = server.addPlayer();
        player.performCommand("message");
        player.assertNoMoreSaid();
    }

    @Test
    @DisplayName("Player receives message on send")
    public void testMessage() {
        PlayerMock owner = server.addPlayer("Owner");
        PlayerMock player = server.addPlayer("Player");
        owner.performCommand("message Player Nice");
        assertTrue(owner.nextMessage().contains("Nice"));
        assertTrue(player.nextMessage().contains("Nice"));
    }

    @Test
    @DisplayName("Sending to a non-existent player sends a message")
    public void testNoTarget() {
        PlayerMock owner = server.addPlayer("Owner");
        owner.performCommand("message OfflinePlayer Nice");
        assertTrue(owner.nextMessage().contains("offline"));
    }
}

package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.utils.Utils;
import mockbukkit.TestUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestDirectMessageCommand extends TestUtility {

    @Test()
    @DisplayName("Typing the command with no arguments does not message")
    public void noArgs() {
        PlayerMock player = server.addPlayer();
        Assertions.assertThrows(NullPointerException.class, () -> {
            player.performCommand("message");
            player.assertNoMoreSaid();
        });
    }

    @Test
    @DisplayName("Player receives message on send")
    public void testMessage() {
        PlayerMock owner = server.addPlayer("Owner");
        PlayerMock player = server.addPlayer("Player");
        owner.performCommand("message Player Nice");
        String expectedMessage = Utils.chat("&7[&6Owner&7 -> &6Player&7] >> Nice");
        player.assertSaid(expectedMessage);
        owner.assertSaid(expectedMessage);
    }

    @Test
    @DisplayName("Sending to a non-existent player sends a message")
    public void testNoTarget() {
        PlayerMock owner = server.addPlayer("Owner");
        owner.performCommand("message Player Nice");
        String message = owner.nextMessage();
        Assertions.assertTrue(message.contains("offline"));
    }
}

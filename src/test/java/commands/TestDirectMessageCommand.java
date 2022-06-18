package commands;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.utils.EonPrefix;
import me.squid.eoncore.utils.Utils;
import mockbukkit.TestUtility;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class TestDirectMessageCommand extends TestUtility {

    @Test
    @DisplayName("Typing the command with no arguments does not message")
    public void noArgs() {
        PlayerMock player = server.addPlayer();
        player.performCommand("message");
        try {
            player.assertNoMoreSaid();
        } catch (NullPointerException e) {
            // Expected behavior
            // This will continue
        }
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
        owner.assertSaid(EonPrefix.bukkitPrefix(EonPrefix.NATIONS) + "Failed to find player. Please try again.");
    }
}

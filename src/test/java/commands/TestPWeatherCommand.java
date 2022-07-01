package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class TestPWeatherCommand extends TestUtility {
    static final String PERMISSION = "eoncommands.pweather";

    @Test
    @DisplayName("Usage message gets sent when wrong weather type is given")
    public void testUsage() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer(PERMISSION, player);
        player.performCommand("pweather nice");
        String messageSent = player.nextMessage();
        Assert.assertTrue(messageSent.contains("Usage"));
    }
}

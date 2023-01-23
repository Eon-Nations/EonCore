package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.bukkit.WeatherType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPWeatherCommand extends TestUtility {
    static final String PERMISSION = "eoncommands.pweather";

    @Test
    @DisplayName("Usage message gets sent when wrong weather type is given")
    public void testUsage() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer(PERMISSION, player);
        player.performCommand("pweather nice");
        String messageSent = player.nextMessage();
        assertTrue(messageSent.contains("Usage"));
    }

    @Test
    @Disabled("MockBukkit not implemented yet")
    @DisplayName("Weather changes with the command change")
    public void testWeather() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer(PERMISSION, player);
        player.performCommand("pweather rain");
        Assertions.assertEquals(WeatherType.DOWNFALL, player.getPlayerWeather());
    }
}

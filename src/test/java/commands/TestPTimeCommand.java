package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPTimeCommand extends TestUtility {
    static final String PERMISSION = "eoncommands.ptime";

    @Test
    @DisplayName("Invalid time string sends usage message")
    public void testInvalid() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer(PERMISSION, player);
        player.performCommand("ptime Invalid");
        String messageSent = player.nextMessage();
        assertTrue(messageSent.contains("Usage"));
    }

    @Test
    @Disabled("Ignored until player.setPlayerTime() is implemented in MockBukkit")
    @DisplayName("Player changes personal time to night")
    public void testPtimeNight() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer(PERMISSION, player);
        player.performCommand("ptime night");
        assertNotEquals(0, player.getPlayerTimeOffset());
    }

    @Test
    @Disabled("Ignored until player.setPlayerTime() is implemented in MockBukkit")
    @DisplayName("Resetting time will reset the players time back to normal")
    public void testReset() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer(PERMISSION, player);
        player.performCommand("ptime night");
        player.performCommand("ptime reset");
        assertEquals(0, player.getPlayerTimeOffset());
    }
}

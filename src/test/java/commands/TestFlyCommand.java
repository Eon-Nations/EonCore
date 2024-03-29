package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.bukkit.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestFlyCommand extends TestUtility {

    @Test
    @DisplayName("Fly is toggled upon command entered")
    public void testToggleFly() {
        PlayerMock player = server.addPlayer();
        player.teleport(new Location(otherWorld, 0, 30, 0));
        player.setOp(true);
        player.performCommand("fly");
       assertTrue(player.getAllowFlight());
    }

    @Test
    @DisplayName("Fly is not toggled for another player if the others permission is not there")
    public void testOtherPermission() {
        PlayerMock someone = server.addPlayer("Someone");
        PlayerMock jim = server.addPlayer("Jim");
        addPermissionToPlayer("eoncommands.fly", someone);
        someone.performCommand("fly Jim");
        assertFalse(jim.getAllowFlight());
    }

    @Test
    @DisplayName("Fly is toggled if others permission is there")
    public void testOPOthers() {
        PlayerMock someone = server.addPlayer();
        addPermissionToPlayer("eoncommands.fly", someone);
        addPermissionToPlayer("eoncommands.fly.others", someone);
        PlayerMock jim = server.addPlayer("Jim");
        jim.teleport(new Location(otherWorld, 0, 30, 0));
        someone.performCommand("fly Jim");
        assertTrue(jim.getAllowFlight());
    }

    @Test
    @DisplayName("Someone already flying toggled with not be able to fly")
    public void testOffToggle() {
        PlayerMock flyingPlayer = server.addPlayer();
        addPermissionToPlayer("eoncommands.fly", flyingPlayer);
        flyingPlayer.teleport(new Location(otherWorld, 0, 30, 0));
        flyingPlayer.performCommand("fly");
        flyingPlayer.performCommand("fly");
        assertFalse(flyingPlayer.getAllowFlight());
    }

    @Test
    @DisplayName("Get null message if player is offline")
    public void testNullMessage() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer("eoncommands.fly", player);
        addPermissionToPlayer("eoncommands.fly.others", player);
        player.performCommand("fly Timmy");
        assertTrue(player.nextMessage().contains("offline"));
    }
}

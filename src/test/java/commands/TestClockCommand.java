package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import static me.squid.eoncore.misc.listeners.ChatFormatListener.isChatLocked;

public class TestClockCommand extends TestUtility {

    @Test
    @DisplayName("OP successfully triggers chat lock")
    public void testClockMessage() {
        PlayerMock owner = server.addPlayer();
        addPermissionToPlayer("eoncommands.clock", owner);
        owner.performCommand("clock");
        Assertions.assertTrue(isChatLocked());
    }

    @Test
    @DisplayName("Toggle twice makes chat available again")
    public void testTwiceToggle() {
        PlayerMock locker = server.addPlayer();
        addPermissionToPlayer("eoncommands.clock", locker);
        locker.performCommand("clock");
        locker.performCommand("clock");
        Assertions.assertFalse(isChatLocked());
    }
}

package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static me.squid.eoncore.misc.listeners.ChatFormatListener.isChatLocked;

public class TestClockCommand extends TestUtility {

    @Test
    @Disabled("Disabled until chat format listener is implemented")
    @DisplayName("OP successfully triggers chat lock")
    public void testClockMessage() {
        PlayerMock owner = server.addPlayer();
        addPermissionToPlayer("eoncommands.clock", owner);
        owner.performCommand("clock");
        Assertions.assertTrue(isChatLocked());
    }

    @Test
    @Disabled("Disabled until chat format listener is implemented")
    @DisplayName("Toggle twice makes chat available again")
    public void testTwiceToggle() {
        PlayerMock locker = server.addPlayer();
        addPermissionToPlayer("eoncommands.clock", locker);
        locker.performCommand("clock");
        locker.performCommand("clock");
        Assertions.assertFalse(isChatLocked());
    }
}

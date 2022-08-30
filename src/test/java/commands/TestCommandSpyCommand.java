package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class TestCommandSpyCommand extends TestUtility {

    private void callCommandEvent(PlayerMock player, String command) {
        PlayerCommandPreprocessEvent preprocessEvent = new PlayerCommandPreprocessEvent(player, command);
        Bukkit.getPluginManager().callEvent(preprocessEvent);
    }

    @Test
    @DisplayName("OP Player are succesfully toggled")
    public void testToggle() {
        PlayerMock player = server.addPlayer();
        player.setOp(true);
        player.performCommand("commandspy");
        player.nextMessage();
        PlayerMock dummy = server.addPlayer("Dummy");
        callCommandEvent(dummy, "/help");
        String message = player.nextMessage();
        Assertions.assertTrue(message.contains("/help"));
    }

}

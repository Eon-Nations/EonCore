package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.messaging.EonPrefix;
import mockbukkit.TestUtility;
import org.bukkit.GameMode;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class TestGamemodeCheckCommand extends TestUtility {

    @Test
    @DisplayName("Usage message gets sent if not enough arguments are given")
    public void testNotEnoughArgs() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer("eoncommands.gamemodecheck", player);
        player.performCommand("gamemodecheck");
        Assertions.assertTrue(player.nextMessage().contains("Usage"));
    }

    @Test
    @DisplayName("Gamemode check correctly gets players gamemode")
    public void testGamemode() {
        PlayerMock player = server.addPlayer("Jim");
        player.setGameMode(GameMode.CREATIVE);
        addPermissionToPlayer("eoncommands.gamemodecheck", player);
        player.performCommand("gamemodecheck Jim");
        Assertions.assertTrue(player.nextMessage().contains("Creative"));
    }
}

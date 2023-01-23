package commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.bukkit.GameMode;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

public class TestCreativeCommand extends TestUtility {

    @Test
    public void noPerms() {
        PlayerMock player = server.addPlayer();
        player.setGameMode(GameMode.SURVIVAL);
        try {
            player.performCommand("gmc");
        } catch (UnimplementedOperationException e) {
            Logger logger = Logger.getLogger("TestCreative");
            logger.info("Command performing is unimplemented for commands with permissions");
        }
        player.assertGameMode(GameMode.SURVIVAL);
    }

    @Test
    public void withPerms() {
        PlayerMock player = server.addPlayer();
        player.setOp(true);
        player.setGameMode(GameMode.SURVIVAL);
        player.performCommand("gmc");
        player.assertGameMode(GameMode.CREATIVE);
    }

    @Test
    public void withTarget() {
        PlayerMock player = server.addPlayer();
        PlayerMock target = server.addPlayer("Target");
        player.setOp(true);
        player.performCommand("gmc Target");
        target.assertGameMode(GameMode.CREATIVE);
        player.assertGameMode(GameMode.SURVIVAL);
    }
}

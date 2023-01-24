package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.utils.Utils;
import mockbukkit.TestUtility;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestSpawnCommand extends TestUtility {

    @Test
    @Disabled("MockBukkit not implemented yet")
    @DisplayName("No arguments without perms brings player back to spawn")
    public void testNotOp() {
        PlayerMock player = server.addPlayer();
        player.performCommand("spawn");
        server.getScheduler().performTicks(100);
        player.assertTeleported(Utils.getSpawnLocation(), 20);
    }

    @Test
    @Disabled("MockBukkit not implemented yet")
    @DisplayName("OP Player can successfully go to spawn")
    public void testOPPlayer() {
        PlayerMock player = server.addPlayer();
        player.setOp(true);
        player.performCommand("spawn");
        player.assertTeleported(Utils.getSpawnLocation(), 20);
    }
}

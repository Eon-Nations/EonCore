package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.misc.utils.Utils;
import mockbukkit.TestUtility;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class TestSpawnCommand extends TestUtility {

    @Test
    @Ignore("MockBukkit not implemented yet")
    @DisplayName("No arguments without perms brings player back to spawn")
    public void testNotOp() {
        PlayerMock player = server.addPlayer();
        player.performCommand("spawn");
        server.getScheduler().performTicks(100);
        player.assertTeleported(Utils.getSpawnLocation(), 20);
    }

    @Test
    @Ignore("MockBukkit not implemented yet")
    @DisplayName("OP Player can successfully go to spawn")
    public void testOPPlayer() {
        PlayerMock player = server.addPlayer();
        player.setOp(true);
        player.performCommand("spawn");
        player.assertTeleported(Utils.getSpawnLocation(), 20);
    }
}

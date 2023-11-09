package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestClearChatCommand extends TestUtility {

    @Test
    @DisplayName("OP Doesn't get spammed with chat")
    public void testOPClearChat() {
        PlayerMock player = server.addPlayer();
        player.setOp(true);
        player.performCommand("clearchat");
        player.assertNoMoreSaid();
    }

    @Test
    @DisplayName("Normal people do get spammed with chat")
    public void testClearChat() {
        PlayerMock owner = server.addPlayer();
        owner.setOp(true);
        PlayerMock normie = server.addPlayer();
        owner.performCommand("clearchat");
        normie.assertSaid("");
    }
}

package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.utils.Utils;
import mockbukkit.TestUtility;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class TestClearChatCommand extends TestUtility {

    @Test
    @DisplayName("OP Doesn't get spammed with chat")
    public void testOPClearChat() {
        PlayerMock player = server.addPlayer();
        player.setOp(true);
        player.performCommand("clearchat");
        player.assertSaid(Utils.chat(EonPrefix.bukkitPrefix(EonPrefix.NATIONS) + "You are immune to chat clear"));
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

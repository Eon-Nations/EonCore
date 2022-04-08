package commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class TestClearChatCommand {

    private ServerMock server;
    private EonCore plugin;

    @Before
    public void setUp() {
        server = MockBukkit.mock();
        MockBukkit.createMockPlugin("LuckPerms");
        server.addSimpleWorld("spawn_void");
        plugin = MockBukkit.load(EonCore.class);
    }

    @Test
    @DisplayName("OP Doesn't get spammed with chat")
    public void testOPClearChat() {
        PlayerMock player = server.addPlayer();
        player.setOp(true);
        player.performCommand("clearchat");
        player.assertSaid(Utils.chat(Utils.getPrefix("nations") + "&bYou are immune to chat clear"));
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

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }
}

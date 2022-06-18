package commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.EonCore;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class TestCommandSpyCommand {

    private ServerMock server;

    @Before
    public void setUp() {
        server = MockBukkit.mock();
        MockBukkit.createMockPlugin("LuckPerms");
        server.addSimpleWorld("spawn_void");
        MockBukkit.load(EonCore.class);
    }

    private void callCommandEvent(PlayerMock player, String command) {
        PlayerCommandPreprocessEvent preprocessEvent = new PlayerCommandPreprocessEvent(player, command);
        Bukkit.getPluginManager().callEvent(preprocessEvent);
    }

    @Test
    @Ignore
    @DisplayName("Players cannot access commandspy")
    public void testNoPerms() {
        PlayerMock player = server.addPlayer();
        PlayerMock other = server.addPlayer();
        player.performCommand("commandspy");
        callCommandEvent(other, "/hat");
        other.assertNoMoreSaid();
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
        player.assertSaid("§7[§6§lCommandSpy§r§7] Dummy: /help");
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }

}

package commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.listeners.ChatFormatListener;
import me.squid.eoncore.utils.Utils;
import org.junit.*;
import org.junit.jupiter.api.DisplayName;

public class TestClockCommand {
    private ServerMock server;
    private EonCore plugin;

    @Before
    public void setup() {
        server = MockBukkit.mock();
        MockBukkit.createMockPlugin("LuckPerms");
        server.addSimpleWorld("spawn_void");
        plugin = MockBukkit.load(EonCore.class);
    }

    @Test
    @Ignore
    @DisplayName("Normal people should not be able to access /clock")
    public void testNoPerms() {
        if (ChatFormatListener.isChatLocked()) ChatFormatListener.setChatLocked(false);
        PlayerMock player = server.addPlayer();
        player.performCommand("clock");
        Assert.assertFalse(ChatFormatListener.isChatLocked());
    }

    @Test
    @DisplayName("OP successfully triggers chat lock")
    public void testClockMessage() {
        PlayerMock owner = server.addPlayer();
        PlayerMock normie = server.addPlayer();
        owner.setOp(true);
        owner.performCommand("clock");
        normie.assertSaid(Utils.getPrefix("moderation") +
                Utils.translateHex("#ff0000Chat is locked. Please wait while we resolve the conflict. Thank you for your patience"));
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }
}

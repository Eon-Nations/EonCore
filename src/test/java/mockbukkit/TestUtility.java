package mockbukkit;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.EonCore;
import org.bukkit.permissions.PermissionAttachment;
import org.junit.After;
import org.junit.Before;

public class TestUtility {
    protected ServerMock server;
    protected EonCore plugin;
    protected WorldMock otherWorld;

    @Before
    public void setup() {
        server = MockBukkit.mock();
        MockBukkit.createMockPlugin("LuckPerms");
        server.addSimpleWorld("spawn_void");
        otherWorld = server.addSimpleWorld("other");
        plugin = MockBukkit.load(EonCore.class);
    }

    protected void addPermissionToPlayer(String permission, PlayerMock player) {
        PermissionAttachment attachment = player.addAttachment(plugin);
        attachment.setPermission(permission, true);
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }

}

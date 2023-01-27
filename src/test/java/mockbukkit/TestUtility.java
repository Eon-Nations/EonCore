package mockbukkit;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.EonCore;
import org.bukkit.permissions.PermissionAttachment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class TestUtility {
    protected ServerMock server;
    protected EonCore plugin;
    protected WorldMock otherWorld;

    @BeforeEach
    public void setup() {
        server = MockBukkit.mock();
        server.addSimpleWorld("spawn_void");
        otherWorld = server.addSimpleWorld("other");
        plugin = MockBukkit.load(EonCore.class);
    }

    protected void addPermissionToPlayer(String permission, PlayerMock player) {
        PermissionAttachment attachment = player.addAttachment(plugin);
        attachment.setPermission(permission, true);
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

}

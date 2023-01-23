package mockbukkit;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.EonCore;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.ServicePriority;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class TestUtility {
    protected ServerMock server;
    protected EonCore plugin;
    protected WorldMock otherWorld;
    protected LuckPerms luckPerms;

    @BeforeEach
    public void setup() {
        server = MockBukkit.mock();
        MockPlugin fakeLP = MockBukkit.createMockPlugin("LuckPerms");
        Bukkit.getServicesManager().register(LuckPerms.class, luckPerms, fakeLP, ServicePriority.Normal);
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

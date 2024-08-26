package mockbukkit;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionAttachment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.eonnations.eoncore.EonCore;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;

public class TestUtility {
    protected ServerMock server;
    protected EonCore plugin;
    protected WorldMock otherWorld;

    // Bukkit is a static class, and JUnit is making multiple threads to test the code
    // In order for MockBukkit to try not to override Bukkit, the server is set to null every time
    // Bukkit.setServer(Server) throws an error when attempting, so reflection is needed
    private void setBukkitServerNull() throws NoSuchFieldException, IllegalAccessException {
        Field serverField = Bukkit.class.getDeclaredField("server");
        serverField.setAccessible(true);
        serverField.set(serverField, null);
    }

    @BeforeEach
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        setBukkitServerNull();
        server = MockBukkit.getOrCreateMock();
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

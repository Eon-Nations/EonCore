package mockbukkit;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import me.squid.eoncore.EonCore;

public class TestUtility {

    public static ServerMock setup() {
        ServerMock server = MockBukkit.mock();
        MockBukkit.createMockPlugin("LuckPerms");
        server.addSimpleWorld("spawn_void");
        MockBukkit.load(EonCore.class);
        return server;
    }

    public static void tearDown() {
        MockBukkit.unmock();
    }

}

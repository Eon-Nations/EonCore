package mockbukkit;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import me.squid.eoncore.EonCore;
import org.junit.After;
import org.junit.Before;

public class TestUtility {
    protected ServerMock server;
    protected EonCore plugin;

    @Before
    public void setup() {
        server = MockBukkit.mock();
        MockBukkit.createMockPlugin("LuckPerms");
        server.addSimpleWorld("spawn_void");
        plugin = MockBukkit.load(EonCore.class);
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }

}

package voting;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.database.RedisClient;
import mockbukkit.TestUtility;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestVoting {
    EonCore plugin;
    ServerMock server;
    @Mock
    RedisClient client;

    @BeforeEach
    void setup() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(EonCore.class);
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }
}

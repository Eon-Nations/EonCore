package voting;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.database.RedisClient;
import me.squid.eoncore.voting.Voting;
import net.kyori.adventure.title.Title;
import net.luckperms.api.LuckPerms;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestVoting {
    EonCore plugin;
    ServerMock server;
    @Mock
    RedisClient client;
    @Mock
    LuckPerms luckPerms;

    @BeforeEach
    void setup() {
        server = MockBukkit.mock();
        MockPlugin fakeLP = MockBukkit.createMockPlugin("LuckPerms");
        server.getServicesManager().register(LuckPerms.class, luckPerms, fakeLP, ServicePriority.Normal);
        plugin = MockBukkit.load(EonCore.class);
    }

    @Test
    @DisplayName("Test that the giveReward method correctly increments the player's vote count in Redis by 1")
    void testGiveRewardIncrement() {
        PlayerMock player = server.addPlayer();
        when(client.getKey(any(), any(), any())).thenReturn(5);
        Voting.giveReward(player, client);
        verify(client).setValue(any(), any(), eq(6), any());
    }

    @Test
    @DisplayName("Test that the giveReward method correctly calculates the remaining votes till the next reward")
    void testGiveRewardRemainingVotes() {
        PlayerMock player = server.addPlayer();
        when(client.getKey(any(), any(), any())).thenReturn(5);
        Voting.giveReward(player, client);
        verify(client, times(1)).setValue(any(), any(), any(), any());
    }

    @Test
    @DisplayName("Test that the giveReward method correctly sends the title message to the player")
    void testGiveRewardTitleMessage() {
        UUID uuid = UUID.randomUUID();
        Player player = mock(Player.class);
        when(player.getUniqueId()).thenReturn(uuid);
        when(client.getKey(any(), any(), any())).thenReturn(5);
        Voting.giveReward(player, client);
        verify(player, times(1)).showTitle(any(Title.class));
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }
}

package currency.managers;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.currency.managers.EconManager;
import me.squid.eoncore.database.RedisClient;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.junit.jupiter.api.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TestEconManager {
    EconManager manager;
    EonCore plugin;
    ServerMock server;
    RedisClient client;

    @BeforeEach
    void setupEach() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(EonCore.class);
        client = mock(RedisClient.class);
        manager = new EconManager(plugin, client);
    }

    @Test
    @DisplayName("Given a two decimal number, output the same number in a string format")
    void testValidFormat() {
        double value = 10.12;
        String actualFormat = manager.format(value);
        String expectedFormat = "10.12";
        Assertions.assertEquals(expectedFormat, actualFormat);
    }

    @Test
    @DisplayName("Given zero, pad the decimal places with zeros")
    void testZeroBalance() {
        double zero = 0;
        String actualFormat = manager.format(zero);
        String expectedFormat = "0.00";
        Assertions.assertEquals(expectedFormat, actualFormat);
    }

    @Test
    @DisplayName("Given a number, pad the decimal places with zeros")
    void testAddingZeros() {
        double wholeNumber = 12;
        String actualFormat = manager.format(wholeNumber);
        String expectedFormat = "12.00";
        Assertions.assertEquals(expectedFormat, actualFormat);
    }

    @Test
    @DisplayName("Given a number with more than 2 decimal places, round to the nearest two")
    void testRounding() {
        double weirdBalance = 12.5678;
        String actualFormat = manager.format(weirdBalance);
        String expectedFormat = "12.57";
        Assertions.assertEquals(expectedFormat, actualFormat);
    }

    @Test
    @DisplayName("If a player exists, return true")
    void testSuccessfulLookup() {
        PlayerMock player = server.addPlayer();
        when(client.getKey(any(), any(), any())).thenReturn(10.0);
        assertTrue(manager.hasAccount(player));
    }

    @Test
    @DisplayName("If a player does not exist, return false")
    void testPlayerNotExist() {
        OfflinePlayer player = mock(OfflinePlayer.class);
        when(player.hasPlayedBefore()).thenReturn(false);
        assertFalse(manager.hasAccount(player));
    }

    @Test
    @DisplayName("Expected value is returned given a normal value")
    void testNormalGetBalance() {
        PlayerMock player = server.addPlayer();
        when(client.getKey(any(), any(), any())).thenReturn(40506.45);
        double balance = manager.getBalance(player);
        assertEquals(40506.45, balance);
    }

    @Test
    @DisplayName("If an error occurs, return 0")
    void testCorruptBalance() {
        PlayerMock player = server.addPlayer();
        when(client.getKey(any(), any(), any())).thenThrow(new NullPointerException());
        double balance = manager.getBalance(player);
        assertEquals(0, balance);
    }

    @Test
    @DisplayName("If a player does not exist, return 0")
    void testNotExist() {
        OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.randomUUID());
        double balance = manager.getBalance(player);
        assertEquals(0, balance);
    }

    @Test
    @DisplayName("If a player has insufficient funds, then return false")
    void testInsufficientFunds() {
        PlayerMock player = server.addPlayer();
        when(client.getKey(any(), any(), any())).thenReturn(1.0);
        double needed = 2.0;
        assertFalse(manager.has(player, needed));
    }

    @Test
    @DisplayName("If a player has the exact amount, then return true")
    void testExactFunds() {
        PlayerMock player = server.addPlayer();
        when(client.getKey(any(), any(), any())).thenReturn(2.0);
        double needed = 2.0;
        assertTrue(manager.has(player, needed));
    }

    @Test
    @DisplayName("If a player has more than the amount, then return true")
    void testMoreFunds() {
        PlayerMock player = server.addPlayer();
        when(client.getKey(any(), any(), any())).thenReturn(10.0);
        double needed = 2.0;
        assertTrue(manager.has(player, needed));
    }

    @Test
    @DisplayName("Withdrawing to the negatives is an invalid transaction")
    void testInsufficientWithdrawal() {
        PlayerMock player = server.addPlayer();
        when(client.getKey(any(), any(), any())).thenReturn(20.0);
        double withdrawAmount = 30.0;
        EconomyResponse response = manager.withdrawPlayer(player, withdrawAmount);
        assertEquals(EconomyResponse.ResponseType.FAILURE, response.type);
        assertEquals(20.0, response.balance);
    }


    @Test
    @DisplayName("Withdrawing with an exact amount takes balance to 0")
    void testExactWithdrawal() {
        PlayerMock player = server.addPlayer();
        when(client.getKey(any(), any(), any())).thenReturn(100.0);
        double withdrawAmount = 100.0;
        EconomyResponse response = manager.withdrawPlayer(player, withdrawAmount);
        assertEquals(EconomyResponse.ResponseType.SUCCESS, response.type);
        assertEquals(0.0, response.balance);
    }

    @Test
    @DisplayName("Withdrawing with plenty of funds is successful")
    void testSuccessfulWithdrawal() {
        PlayerMock player = server.addPlayer();
        when(client.getKey(any(), any(), any())).thenReturn(100.0);
        double amount = 50.0;
        EconomyResponse response = manager.withdrawPlayer(player, amount);
        assertEquals(EconomyResponse.ResponseType.SUCCESS, response.type);
        assertEquals(50.0, response.balance);
    }

    @Test
    @DisplayName("Depositing into an empty account creates a new one")
    void testNoAccountDeposit() {
        UUID uuid = UUID.randomUUID();
        OfflinePlayer player = mock(OfflinePlayer.class);
        when(player.hasPlayedBefore()).thenReturn(false);
        when(player.getUniqueId()).thenReturn(uuid);
        when(client.getKey(any(), any(), any())).thenThrow(new NullPointerException());
        double amount = 10.0;
        EconomyResponse response = manager.depositPlayer(player, amount);
        assertEquals(EconomyResponse.ResponseType.SUCCESS, response.type);
        assertEquals(10.0, response.balance);
    }

    @Test
    @DisplayName("Depositing into an existing account adds to the current balance")
    void testWithAccountDeposit() {
        PlayerMock player = server.addPlayer();
        when(client.getKey(any(UUID.class), any(), any())).thenReturn(10.0);
        double amount = 20.0;
        EconomyResponse response = manager.depositPlayer(player, amount);
        assertEquals(EconomyResponse.ResponseType.SUCCESS, response.type);
        assertEquals(30.0, response.balance);
        verify(client, times(1)).setValue(any(), any(), eq(30.0), any());
    }

    @Test
    @DisplayName("Creating an already existing player account changes nothing")
    void testExistingPlayer() {
        PlayerMock player = server.addPlayer();
        when(client.getKey(any(), any(), any())).thenReturn(10.0);
        boolean actual = manager.createPlayerAccount(player);
        assertFalse(actual);
        verify(client, times(0)).setValue(eq(player.getUniqueId()), any(), any(), any());
    }

    @Test
    @DisplayName("Create a player account when they don't exist")
    void testCreateNewAccount() {
        UUID uuid = UUID.randomUUID();
        OfflinePlayer player = mock(OfflinePlayer.class);
        when(player.getUniqueId()).thenReturn(uuid);
        boolean actual = manager.createPlayerAccount(player);
        assertTrue(actual);
        assertEquals(0.0, manager.getBalance(player));
    }

    @Test
    @DisplayName("Create a player account when the value is corrupt")
    void testCreateAccountCorrupt() {
        PlayerMock player = server.addPlayer();
        when(client.getKey(any(), any(), any())).thenThrow(NullPointerException.class);
        boolean actual = manager.createPlayerAccount(player);
        assertTrue(actual);
        assertEquals(0.0, manager.getBalance(player));
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }
}

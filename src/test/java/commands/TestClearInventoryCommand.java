package commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.EonCore;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class TestClearInventoryCommand {

    private ServerMock server;
    private EonCore plugin;

    @Before
    public void setup() {
        server = MockBukkit.mock();
        MockBukkit.createMockPlugin("LuckPerms");
        server.addSimpleWorld("spawn_void");
        plugin = MockBukkit.load(EonCore.class);
    }

    private void addItemsToPlayer(PlayerMock player) {
        player.getInventory().addItem(new ItemStack(Material.DIRT, 32));
    }

    private void assertInventoryNotEmpty(PlayerMock player) {
        final int inventorySize = player.getInventory().getSize();
        Assert.assertTrue(inventorySize > 0);
    }

    private void assertInventoryEmpty(PlayerMock player) {
        BiFunction<Integer, ItemStack, Integer> addItemAmount = (previous, item) -> {
            int add = item == null ? 0 : item.getAmount();
            return previous + add;
        };
        Inventory inventory = player.getInventory();
        Stream<ItemStack> items = Arrays.stream(inventory.getContents());
        final int inventorySize = items.reduce(0, addItemAmount, Integer::sum);
        Assert.assertEquals(0, inventorySize);
    }

    @Test
    @DisplayName("Normal players cannot access this command")
    public void testNoPerms() {
        PlayerMock player = server.addPlayer();
        addItemsToPlayer(player);
        try {
            player.performCommand("clearinventory");
        } catch (UnimplementedOperationException ignored) { }
        assertInventoryNotEmpty(player);
    }

    @Test
    @DisplayName("Self clearing inventory with access clears inventory")
    public void testWithPerms() {
        PlayerMock player = server.addPlayer();
        player.setOp(true);
        addItemsToPlayer(player);
        player.performCommand("clearinventory");
        assertInventoryEmpty(player);
    }

    @Test
    @DisplayName("OP Players will succesfully clear others inventory")
    public void testOthersWithPerms() {
        PlayerMock player = server.addPlayer();
        PlayerMock other = server.addPlayer("other");
        addItemsToPlayer(other);
        player.setOp(true);
        player.performCommand("clearinventory other");
        assertInventoryEmpty(other);
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }
}

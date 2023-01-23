package commands;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestClearInventoryCommand extends TestUtility {

    private void addItemsToPlayer(PlayerMock player) {
        player.getInventory().addItem(new ItemStack(Material.DIRT, 32));
    }

    private void assertInventoryNotEmpty(PlayerMock player) {
        final int inventorySize = player.getInventory().getSize();
        assertTrue(inventorySize > 0);
    }

    private void assertInventoryEmpty(PlayerMock player) {
        BiFunction<Integer, ItemStack, Integer> addItemAmount = (previous, item) -> {
            int add = item == null ? 0 : item.getAmount();
            return previous + add;
        };
        Inventory inventory = player.getInventory();
        Stream<ItemStack> items = Arrays.stream(inventory.getContents());
        final int inventorySize = items.reduce(0, addItemAmount, Integer::sum);
        assertEquals(0, inventorySize);
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
}

package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.bukkit.event.inventory.InventoryType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEnderChestCommand extends TestUtility {

    @Test
    @DisplayName("Personal ender chest successfully opens when called")
    public void testPersonal() {
        PlayerMock player = server.addPlayer();
        player.setOp(true);
        player.performCommand("enderchest");
        player.assertInventoryView(InventoryType.ENDER_CHEST);
        assertEquals(player.getOpenInventory().getTopInventory(), player.getEnderChest());
    }

}

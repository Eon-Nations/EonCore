package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.bukkit.event.inventory.InventoryType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestDisposalCommand extends TestUtility {

    @Test
    @DisplayName("Opens an inventory that has nothing in it")
    public void testInventory() {
        PlayerMock player = server.addPlayer();
        player.performCommand("disposal");
        player.assertInventoryView(InventoryType.CHEST);
    }

}

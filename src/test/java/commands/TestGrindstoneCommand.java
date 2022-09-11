package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.bukkit.event.inventory.InventoryType;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class TestGrindstoneCommand extends TestUtility {

    @Test
    @DisplayName("Grindstone inventory visible after command open")
    public void testInventory() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer("eoncommands.grindstone", player);
        player.performCommand("grindstone");
        player.assertInventoryView(InventoryType.GRINDSTONE);
    }

}

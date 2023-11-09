package managers;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.utils.Utils;
import mockbukkit.TestUtility;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestInventoryManager extends TestUtility {

    @Test
    @DisplayName("Normal inventory interaction doesn't cancel event")
    public void testNoCancel() {
        PlayerMock player = server.addPlayer();
        player.assertTeleported(Utils.getSpawnLocation(), 10.0);
        player.openInventory(player.getInventory());
        player.assertInventoryView(InventoryType.PLAYER);
        InventoryClickEvent event = player.simulateInventoryClick(10);
        player.assertNotTeleported();
        Assertions.assertFalse(event.isCancelled());
    }
}

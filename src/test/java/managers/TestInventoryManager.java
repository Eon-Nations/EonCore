package managers;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.bukkit.Sound;
import org.bukkit.event.inventory.ClickType;
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
        player.openInventory(player.getInventory());
        player.assertInventoryView(InventoryType.PLAYER);
        InventoryClickEvent event = player.simulateInventoryClick(10);
        player.assertNotTeleported();
        Assertions.assertFalse(event.isCancelled());
    }

    @Test
    @DisplayName("Opening a stale inventory cancels the click event")
    public void testYesCancel() {
        PlayerMock player = server.addPlayer();
        player.performCommand("warps");
        InventoryClickEvent clickGlassEvent = player.simulateInventoryClick(player.getOpenInventory(), ClickType.LEFT, 1);
        player.assertSoundHeard(Sound.BLOCK_NOTE_BLOCK_HARP);
        Assertions.assertEquals(clickGlassEvent.getClickedInventory(), player.getOpenInventory().getTopInventory());
        Assertions.assertTrue(clickGlassEvent.isCancelled());
    }
}

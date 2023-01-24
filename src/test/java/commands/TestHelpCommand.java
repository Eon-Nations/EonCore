package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.misc.managers.InventoryManager;
import mockbukkit.TestUtility;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestHelpCommand extends TestUtility {

    @Test
    @DisplayName("Help displays the correct inventory")
    public void testHelp() {
        PlayerMock player = server.addPlayer();
        player.performCommand("help");
        player.assertInventoryView(InventoryType.CHEST);
        Inventory expected = InventoryManager.staleInventory("help");
        Assertions.assertArrayEquals(expected.getContents(), player.getOpenInventory().getTopInventory().getContents());
    }

    @Test
    @DisplayName("Sound is played on inventory open")
    public void testSound() {
        PlayerMock player = server.addPlayer();
        player.performCommand("help");
        player.assertSoundHeard(Sound.BLOCK_NOTE_BLOCK_HARP);
    }
}

package commands;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.bukkit.event.inventory.InventoryType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class TestEnderChestCommand extends TestUtility {

    @Test
    @DisplayName("Personal ender chest successfully opens when called")
    public void testPersonal() {
        PlayerMock player = server.addPlayer();
        player.setOp(true);
        player.performCommand("enderchest");
        player.assertInventoryView(InventoryType.ENDER_CHEST);
        Assert.assertEquals(player.getOpenInventory().getTopInventory(), player.getEnderChest());
    }

}

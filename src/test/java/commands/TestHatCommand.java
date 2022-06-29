package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.utils.Messaging;
import mockbukkit.TestUtility;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class TestHatCommand extends TestUtility {

    @Test
    @DisplayName("Message is sent if player already has a helmet")
    public void testHelmet() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer("eoncommands.hat", player);
        player.getInventory().setHelmet(new ItemStack(Material.DIRT, 1));
        player.performCommand("hat");
        player.assertSaid(Messaging.getNationsMessage("Take off your helmet to put on the hat"));
    }

    @Test
    @DisplayName("Hat is correct placed into players helmet slot")
    public void testNoHelmet() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer("eoncommands.hat", player);
        ItemStack hat = new ItemStack(Material.DIAMOND_AXE, 1);
        player.getInventory().setItemInMainHand(hat);
        player.performCommand("hat");
        Assert.assertEquals(hat, player.getInventory().getHelmet());
    }
}

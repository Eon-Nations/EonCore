package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.messaging.Messaging;
import mockbukkit.TestUtility;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class TestHatCommand extends TestUtility {

    @Test
    @DisplayName("The current hat is not replaced if /hat is done again")
    public void testHelmet() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer("eoncommands.hat", player);
        ItemStack invalidHat = new ItemStack(Material.DIAMOND_AXE, 1);
        player.getInventory().setHelmet(new ItemStack(Material.DIRT, 1));
        player.getInventory().setItemInMainHand(invalidHat);
        player.performCommand("hat");
        Assertions.assertEquals(Material.DIRT, player.getInventory().getHelmet().getType());
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

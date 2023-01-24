package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestRenameCommand extends TestUtility {
    static final String PERMISSION = "eoncommands.rename";

    @Test
    @DisplayName("Giving no args won't change the name of the item")
    public void testItemName() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer(PERMISSION, player);
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
        player.getInventory().setItemInMainHand(sword);
        player.performCommand("rename");
        Assertions.assertNull(sword.getItemMeta().displayName());
    }

}

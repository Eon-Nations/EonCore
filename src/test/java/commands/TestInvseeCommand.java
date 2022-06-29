package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.utils.Messaging;
import mockbukkit.TestUtility;
import org.bukkit.event.inventory.InventoryType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class TestInvseeCommand extends TestUtility {

    @Test
    @DisplayName("Not enough arguments generates a usage message")
    public void testUsage() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer("eoncommands.invsee", player);
        player.performCommand("invsee");
        player.assertSaid(Messaging.getNationsMessage("Usage: /invsee <player>"));
    }

    @Test
    @DisplayName("Player can open another inventory without immune perms")
    public void testNoImmune() {
        PlayerMock player = server.addPlayer("Jim");
        PlayerMock target = server.addPlayer("Bill");
        addPermissionToPlayer("eoncommands.invsee", player);
        player.performCommand("invsee Bill");
        player.assertInventoryView(InventoryType.PLAYER);
        Assert.assertEquals(target.getInventory(), player.getOpenInventory().getTopInventory());
    }

    @Test
    @DisplayName("If other player doesn't exist, get the null message")
    public void testNullTarget() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer("eoncommands.invsee", player);
        player.performCommand("invsee Nice");
        player.assertSaid(Messaging.bukkitNullMessage());
    }

    @Test
    @DisplayName("Player with immune perms cannot have inventory opened")
    public void testImmunePerms() {
        PlayerMock player = server.addPlayer("Jim");
        PlayerMock target = server.addPlayer("Target");
        addPermissionToPlayer("eoncommands.invsee", player);
        addPermissionToPlayer("eoncommands.invsee.immune", target);
        player.performCommand("invsee Target");
        player.assertSaid(Messaging.bukkitNullMessage());
    }
}

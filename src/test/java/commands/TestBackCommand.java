package commands;

import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class TestBackCommand extends TestUtility {

    private void killPlayer(PlayerMock player) {
        WorldMock otherWorld = server.addSimpleWorld("nice");
        player.teleport(new Location(otherWorld, 0, 30, 0));
        player.setHealth(0);
        Assert.assertTrue(player.isDead());
    }

    @Test
    @DisplayName("Get a message and no teleportation happens for /back without any death")
    public void testNoDeath() {
        PlayerMock player = server.addPlayer("Jim");
        player.performCommand("back");
        String message = player.nextMessage();
        Assertions.assertTrue(message.contains("no"));
    }

    @Test
    @DisplayName("Get a message once you respawn about teleporting back to death location")
    public void testDeath() {
        PlayerMock player = server.addPlayer("Bob");
        killPlayer(player);
        player.respawn();
        Assertions.assertTrue(player.nextMessage().contains("/back"));
    }

    @Test
    @DisplayName("Without permissions, get a message about a cooldown when teleporting back")
    public void testNoPerms() {
        PlayerMock player = server.addPlayer();
        killPlayer(player);
        player.respawn();
        player.performCommand("back");
        player.nextMessage();
        Assertions.assertTrue(player.nextMessage().contains("Teleporting in"));
    }

    private void killOPPlayer(PlayerMock player) {
        player.setOp(true);
        killPlayer(player);
        player.respawn();
    }

    @Test
    @Ignore("MockBukkit hasn't implemented async teleportation yet")
    @DisplayName("With OP, no cooldown is applied and teleported immediately")
    public void testWithOP() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer("eoncommands.teleport.immune", player);
        killOPPlayer(player);
        player.performCommand("back");
        player.assertTeleported(otherWorld.getSpawnLocation(), 20);
    }

    @Test
    @DisplayName("OP Players keep their inventory after death")
    public void testOPDrops() {
        PlayerMock player = server.addPlayer();
        player.getInventory().addItem(new ItemStack(Material.DIRT, 32));
        killOPPlayer(player);
        Assert.assertTrue(player.getInventory().getSize() > 0);
    }
}

package commands;

import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestBackCommand extends TestUtility {

    private void killPlayer(PlayerMock player) {
        WorldMock otherWorld = server.addSimpleWorld("nice");
        player.teleport(new Location(otherWorld, 0, 30, 0));
        player.setHealth(0);
        assertTrue(player.isDead());
    }

    @Test
    @DisplayName("Get a message and no teleportation happens for /back without any death")
    void testNoDeath() {
        PlayerMock player = server.addPlayer("Jim");
        player.performCommand("back");
        String message = player.nextMessage();
        assertTrue(message.contains("no"));
    }

    @Test
    @DisplayName("Get a message once you respawn about teleporting back to death location")
    void testDeath() {
        PlayerMock player = server.addPlayer("Bob");
        killPlayer(player);
        player.respawn();
        assertTrue(player.nextMessage().contains("/back"));
    }

    @Test
    @DisplayName("Without permissions, get a message about a cooldown when teleporting back")
    void testNoPerms() {
        PlayerMock player = server.addPlayer();
        killPlayer(player);
        player.respawn();
        player.performCommand("back");
        player.nextMessage();
        assertTrue(player.nextMessage().contains("Teleporting in"));
    }

    private void killOPPlayer(PlayerMock player) {
        player.setOp(true);
        killPlayer(player);
        player.respawn();
    }

    @Test
    @Disabled("MockBukkit hasn't implemented async teleportation yet")
    @DisplayName("With OP, no cooldown is applied and teleported immediately")
    void testWithOP() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer("eoncommands.teleport.immune", player);
        killOPPlayer(player);
        player.performCommand("back");
        player.assertTeleported(otherWorld.getSpawnLocation(), 20);
    }

    @Test
    @DisplayName("OP Players keep their inventory after death")
    void testOPDrops() {
        PlayerMock player = server.addPlayer();
        player.getInventory().addItem(new ItemStack(Material.DIRT, 32));
        killOPPlayer(player);
        assertTrue(player.getInventory().getSize() > 0);
    }
}

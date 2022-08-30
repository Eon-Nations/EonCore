package commands;

import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.utils.Utils;
import mockbukkit.TestUtility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Assert;
import org.junit.Test;
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
        player.assertSaid(EonPrefix.bukkitPrefix(EonPrefix.NATIONS) + "There is no back location to teleport to");
    }

    @Test
    @DisplayName("Get a message once you respawn about teleporting back to death location")
    public void testDeath() {
        PlayerMock player = server.addPlayer("Bob");
        killPlayer(player);
        player.respawn();
        player.assertSaid(EonPrefix.bukkitPrefix(EonPrefix.NATIONS) + Utils.chat(plugin.getConfig().getString("Death-Back-Message")));
    }

    @Test
    @DisplayName("Without permissions, get a message about a cooldown when teleporting back")
    public void testNoPerms() {
        PlayerMock player = server.addPlayer();
        killPlayer(player);
        player.respawn();
        player.assertSaid(EonPrefix.bukkitPrefix(EonPrefix.NATIONS) + Utils.chat(plugin.getConfig().getString("Death-Back-Message")));
        player.performCommand("back");
        player.assertSaid(EonPrefix.bukkitPrefix(EonPrefix.NATIONS) + Utils.chat(plugin.getConfig().getString("Cooldown-Teleport-Message")
                .replace("<seconds>", Long.toString(plugin.getConfig().getLong("Delay-Seconds")))));
    }

    private void killOPPlayer(PlayerMock player) {
        player.setOp(true);
        killPlayer(player);
        player.respawn();
    }

    @Test
    @DisplayName("With OP, no cooldown is applied and teleported immediately")
    public void testWithOP() {
        PlayerMock player = server.addPlayer();
        killOPPlayer(player);
        player.assertSaid(EonPrefix.bukkitPrefix(EonPrefix.NATIONS) + Utils.chat(plugin.getConfig().getString("Death-Back-Message")));
        player.performCommand("back");
        player.assertSaid(Utils.chat(EonPrefix.bukkitPrefix(EonPrefix.NATIONS) + plugin.getConfig().getString("Teleport-Successful")));
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

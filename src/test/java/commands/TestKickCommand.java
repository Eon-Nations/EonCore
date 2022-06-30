package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.utils.Messaging;
import mockbukkit.TestUtility;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class TestKickCommand extends TestUtility {

    @Test
    @DisplayName("Without a sufficient number of arguments, a usage message is sent")
    public void testNotEnoughArgs() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer("eoncommands.kick", player);
        player.performCommand("kick");
        player.assertSaid(Messaging.getNationsMessage("Usage: /kick <player> <message>"));
    }

    @Test
    @Ignore("Ignore due to MockBukkit not having the method implemented")
    @DisplayName("Player is successfully removed if kicked")
    public void testKick() {
        PlayerMock owner = server.addPlayer("Owner");
        server.addPlayer("Jim");
        addPermissionToPlayer("eoncommands.kick", owner);
        owner.performCommand("kick Jim Nice one jimbo");
        Assert.assertEquals(1, server.getPlayerList().getOnlinePlayers().size());
    }

    @Test
    @DisplayName("Invalid player does not get kicked")
    public void testInvalidPlayer() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer("eoncommands.kick", player);
        player.performCommand("kick Invalid Nice one jimbo");
        player.assertSaid(Messaging.bukkitNullMessage());
    }
}

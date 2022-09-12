package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.bukkit.GameMode;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class TestSurvivalCommand extends TestUtility {

    @Test
    @DisplayName("Self setting survival")
    public void testSelf() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer("eoncommands.gms", player);
        player.setGameMode(GameMode.ADVENTURE);
        player.performCommand("gms");
        Assertions.assertEquals(GameMode.SURVIVAL, player.getGameMode());
    }

    @Test
    @DisplayName("Setting other with perms")
    public void testOthers() {
        PlayerMock player = server.addPlayer();
        PlayerMock other = server.addPlayer("Other");
        addPermissionToPlayer("eoncommands.gms", player);
        addPermissionToPlayer("eoncommands.gms.others", player);
        other.setGameMode(GameMode.ADVENTURE);
        player.performCommand("gms Other");
        Assertions.assertEquals(GameMode.SURVIVAL, other.getGameMode());
    }

    @Test
    @DisplayName("Setting others without perms")
    public void testNoOtherPerms() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer("eoncommands.gms", player);
        PlayerMock other = server.addPlayer("Other");
        other.setGameMode(GameMode.ADVENTURE);
        player.performCommand("gms Other");
        Assertions.assertNotEquals(GameMode.SURVIVAL, other.getGameMode());
    }
}

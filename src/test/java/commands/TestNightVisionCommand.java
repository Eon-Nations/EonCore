package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import mockbukkit.TestUtility;
import org.bukkit.potion.PotionEffectType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestNightVisionCommand extends TestUtility {
    static final String NV_PERMISSION = "eoncommands.nightvision";
    static final String NV_OTHERS_PERMISSION = "eoncommands.nightvision.others";

    @Test
    @DisplayName("Sender with no other permissions cannot toggle other players")
    public void testNoPerms() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer(NV_PERMISSION, player);
        PlayerMock jim = server.addPlayer("Jim");
        player.performCommand("nightvision Jim");
        assertFalse(jim.hasPotionEffect(PotionEffectType.NIGHT_VISION));
    }

    @Test
    @DisplayName("No arguments toggles night vision for the player")
    public void testToggle() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer(NV_PERMISSION, player);
        player.performCommand("nightvision");
        assertTrue(player.hasPotionEffect(PotionEffectType.NIGHT_VISION));
    }

    @Test
    @DisplayName("Sender with other permission can toggle other players")
    public void testOtherToggle() {
        PlayerMock player = server.addPlayer();
        PlayerMock jim = server.addPlayer("Jim");
        addPermissionToPlayer(NV_PERMISSION, player);
        addPermissionToPlayer(NV_OTHERS_PERMISSION, player);
        player.performCommand("nightvision Jim");
        assertTrue(jim.hasPotionEffect(PotionEffectType.NIGHT_VISION));
    }
}

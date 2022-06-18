package commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.squid.eoncore.utils.EonPrefix;
import mockbukkit.TestUtility;
import org.bukkit.GameMode;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class TestGamemodeCheckCommand extends TestUtility {

    @Test
    @DisplayName("Usage message gets sent if not enough arguments are given")
    public void testNotEnoughArgs() {
        PlayerMock player = server.addPlayer();
        addPermissionToPlayer("eoncommands.gamemodecheck", player);
        player.performCommand("gamemodecheck");
        player.assertSaid(EonPrefix.bukkitPrefix(EonPrefix.NATIONS) + "Usage: /gmcheck <player>");
    }

    @Test
    @DisplayName("Gamemode check correctly gets players gamemode")
    public void testGamemode() {
        PlayerMock player = server.addPlayer("Jim");
        player.setGameMode(GameMode.CREATIVE);
        addPermissionToPlayer("eoncommands.gamemodecheck", player);
        player.performCommand("gamemodecheck Jim");
        player.assertSaid(EonPrefix.bukkitPrefix(EonPrefix.NATIONS) + "Jim is in Creative");
    }

    @Test
    @Ignore
    @DisplayName("Config missing message is sent if the config isn't proper")
    public void testConfig() {
        PlayerMock player = server.addPlayer("Jim");
        addPermissionToPlayer("eoncommands.gamemodecheck", player);
        plugin.getConfig().set("GamemodeCheck-Message", null);
        plugin.saveConfig();
        plugin.reloadConfig();
        player.performCommand("gamemodecheck Jim");
        player.assertSaid(EonPrefix.bukkitPrefix(EonPrefix.NATIONS) + "Config message missing. Let the devs know");
    }
}

package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Command;

@Command(name = "spawn", usage = "/spawn")
public class SpawnCommand extends EonCommand {
    static final String OTHERS_PERM = "eoncommands.spawn.others";
    static final String SPAWN_PATH = "Spawn-Message";

    public SpawnCommand(EonCore plugin) {
        super(plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        teleport.delayedTeleport(player, Utils.getSpawnLocation(), SPAWN_PATH);
    }
}

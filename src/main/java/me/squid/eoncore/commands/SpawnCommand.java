package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.entity.Player;

import static me.squid.eoncore.utils.FunctionalBukkit.getPlayerOrSendMessage;

@RegisterCommand
public class SpawnCommand extends EonCommand {
    static final String OTHERS_PERM = "eoncommands.spawn.others";
    static final String SPAWN_PATH = "Spawn-Message";

    public SpawnCommand(EonCore plugin) {
        super("spawn", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 0) {
            teleport.delayedTeleport(player, Utils.getSpawnLocation(), SPAWN_PATH);
        } else if (args.length == 1 && player.hasPermission(OTHERS_PERM)) {
            getPlayerOrSendMessage(player, target -> teleport.delayedTeleport(target, Utils.getSpawnLocation(), SPAWN_PATH), args[0]);
        }
    }
}

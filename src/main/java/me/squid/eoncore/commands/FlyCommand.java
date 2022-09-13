package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.utils.FunctionalBukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RegisterCommand
public class FlyCommand extends EonCommand {
    static final String OTHERS_PERM_NODE = "eoncommands.fly.others";
    private static final Set<UUID> playersFlying = new HashSet<>();

    public FlyCommand(EonCore plugin) {
        super("fly", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.NATIONS);
        if (args.length == 0) {
            toggleFly(player, messenger);
        } else if (args.length == 1 && player.hasPermission(OTHERS_PERM_NODE)) {
            FunctionalBukkit.getPlayerOrSendMessage(player, target -> toggleFly(target, messenger), args[0]);
            messenger.sendMessage(player, "Target-Fly");
        }
    }

    private void toggleFly(Player player, ConfigMessenger messenger) {
        if (playersFlying.contains(player.getUniqueId()))
            turnOffFly(player, messenger);
        else turnOnFly(player, messenger);
    }

    private void turnOnFly(Player player, ConfigMessenger messenger) {
        if (player.getWorld().getName().equals("spawn_void")) return;
        player.setAllowFlight(true);
        playersFlying.add(player.getUniqueId());
        messenger.sendMessage(player, "Fly-On");
    }

    private void turnOffFly(Player player, ConfigMessenger messenger) {
        player.setAllowFlight(false);
        playersFlying.remove(player.getUniqueId());
        messenger.sendMessage(player, "Fly-Off");
    }
}

package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.utils.FunctionalBukkit;
import org.bukkit.entity.Player;

@RegisterCommand
public class FlyCommand extends EonCommand {
    static final String OTHERS_PERM_NODE = "eoncommands.fly.others";

    public FlyCommand(EonCore plugin) {
        super("fly", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 0) {
            toggleFly(player);
        } else if (args.length == 1 && player.hasPermission(OTHERS_PERM_NODE)) {
            FunctionalBukkit.getPlayerOrSendMessage(player, this::toggleFly, args[0]);
        }
    }

    private void toggleFly(Player player) {
        if (player.getAllowFlight())
            turnOffFly(player);
        else turnOnFly(player);
    }

    private void turnOnFly(Player player) {
        if (player.getWorld().getName().equals("spawn_void")) return;
        player.setAllowFlight(true);
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.NATIONS);
        messenger.sendMessage(player, "Fly-On");
    }

    private void turnOffFly(Player player) {
        player.setAllowFlight(false);
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.NATIONS);
        messenger.sendMessage(player, "Fly-Off");
    }
}

package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Command;

import io.vavr.control.Option;

@Command(name = "fly", usage = "/fly <player>", permission = "eoncommands.fly")
public class FlyCommand extends EonCommand {
    static final String OTHERS_PERM_NODE = "eoncommands.fly.others";

    public FlyCommand(EonCore plugin) {
        super(plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.NATIONS);
        if (args.length == 0) {
            toggleFly(player, messenger);
        } else if (args.length == 1 && player.hasPermission(OTHERS_PERM_NODE)) {
            Option<Player> targetOpt = Option.of(Bukkit.getPlayer(args[0]))
                .map(target -> toggleFly(target, messenger))
                .onEmpty(() -> Messaging.sendNullMessage(player));
            if (targetOpt.isDefined()) {
                messenger.sendMessage(player, "Target-Fly");
            }
        }
    }

    private Player toggleFly(Player player, ConfigMessenger messenger) {
        if (player.getAllowFlight()) turnOffFly(player, messenger);
        else turnOnFly(player, messenger);
        return player;
    }

    private void turnOnFly(Player player, ConfigMessenger messenger) {
        player.setAllowFlight(true);
        messenger.sendMessage(player, "Fly-On");
    }

    private void turnOffFly(Player player, ConfigMessenger messenger) {
        player.setAllowFlight(false);
        messenger.sendMessage(player, "Fly-Off");
    }
}

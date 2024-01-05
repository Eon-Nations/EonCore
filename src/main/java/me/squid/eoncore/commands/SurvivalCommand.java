package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Command;

import io.vavr.control.Option;

@Command(name = "gms", usage = "/gms <player>", permission = "eoncommands.gms")
public class SurvivalCommand extends EonCommand {
    static final String OTHERS_NODE = "eoncommands.gms.others";

    public SurvivalCommand(EonCore plugin) {
        super(plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 0) {
            setSurvival(player);
        } else if (args.length == 1 && player.hasPermission(OTHERS_NODE)) {
            Option.of(Bukkit.getPlayer(args[0]))
                .map(this::setSurvival)
                .onEmpty(() -> Messaging.sendNullMessage(player));
        }
    }

    private Player setSurvival(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.MODERATION);
        messenger.sendMessage(player, "Survival-Message");
        return player;
    }
}

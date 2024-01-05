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

@Command(name = "gmsp", usage = "/gmsp <player>", permission = "eoncommands.gmsp")
public class SpectatorCommand extends EonCommand {
    static final String OTHERS_NODE = "eoncommands.gmsp.others";

    public SpectatorCommand(EonCore plugin) {
        super(plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 0) {
            changeToSpectator(player);
        } else if (args.length == 1 && player.hasPermission(OTHERS_NODE)) {
            Option.of(Bukkit.getPlayer(args[0]))
                .map(this::changeToSpectator)
                .onEmpty(() -> Messaging.sendNullMessage(player));
        }
    }

    private Player changeToSpectator(Player player) {
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.MODERATION);
        player.setGameMode(GameMode.SPECTATOR);
        messenger.sendMessage(player, "Spectator-Message");
        return player;
    }
}

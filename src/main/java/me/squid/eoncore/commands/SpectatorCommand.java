package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import static me.squid.eoncore.utils.FunctionalBukkit.getPlayerOrSendMessage;

@RegisterCommand
public class SpectatorCommand extends EonCommand {
    static final String OTHERS_NODE = "eoncommands.gmsp.others";

    public SpectatorCommand(EonCore plugin) {
        super("gmsp", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 0) {
            spectator(player);
        } else if (args.length == 1 && player.hasPermission(OTHERS_NODE)) {
            getPlayerOrSendMessage(player, this::spectator, args[0]);
        }
    }

    private void spectator(Player player) {
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.MODERATION);
        player.setGameMode(GameMode.SPECTATOR);
        messenger.sendMessage(player, "Spectator-Message");
    }
}

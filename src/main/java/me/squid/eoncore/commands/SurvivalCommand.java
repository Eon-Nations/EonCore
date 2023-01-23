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
public class SurvivalCommand extends EonCommand {
    static final String OTHERS_NODE = "eoncommands.gms.others";

    public SurvivalCommand(EonCore plugin) {
        super("gms", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 0) {
            setSurvival(player);
        } else if (args.length == 1 && player.hasPermission(OTHERS_NODE)) {
            getPlayerOrSendMessage(player, this::setSurvival, args[0]);
        }
    }

    private void setSurvival(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.MODERATION);
        messenger.sendMessage(player, "Survival-Message");
    }
}

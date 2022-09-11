package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.messaging.Messenger;
import me.squid.eoncore.utils.FunctionalBukkit;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

@RegisterCommand
public class GamemodeCheckCommand extends EonCommand {

    public GamemodeCheckCommand(EonCore plugin) {
        super("gamemodecheck", plugin);
    }

    private void sendGamemodeMessage(Player sender, Player target) {
        String gamemodeMessage = core.getConfig().getString("GamemodeCheck-Message")
                .replace("<player>", target.getName())
                .replace("<gamemode>", StringUtils.capitalize(target.getGameMode().toString().toLowerCase()));
        Component message = Messaging.fromFormatString(gamemodeMessage);
        Messenger messenger = Messaging.messenger(EonPrefix.MODERATION);
        messenger.send(sender, message);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 1) {
            FunctionalBukkit.getPlayerOrSendMessage(player, target -> sendGamemodeMessage(player, target), args[0]);
        } else {
            Messenger messenger = Messaging.messenger(EonPrefix.MODERATION);
            messenger.send(player, Component.text("Usage: /gmcheck <player>"));
        }
    }
}

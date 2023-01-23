package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.messaging.Messenger;
import me.squid.eoncore.utils.FunctionalBukkit;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.function.Consumer;

@RegisterCommand
public class KickCommand extends EonCommand {

    public KickCommand(EonCore plugin) {
        super("kick", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length > 2) {
            String username = args[0];
            FunctionalBukkit.getPlayerOrSendMessage(player, kickPlayer(reason(args)), username);
        } else {
            Component usageMessage = Component.text("Usage: /kick <player> <message>");
            Messenger messenger = Messaging.messenger(EonPrefix.NATIONS);
            messenger.send(player, usageMessage);
        }
    }

    private Consumer<Player> kickPlayer(Component reason) {
        return target -> target.kick(reason);
    }

    private Component reason(String[] args) {
        String[] copyArgs = Arrays.copyOf(args, args.length);
        copyArgs[0] = "";
        String message = Utils.getMessageFromArgs(copyArgs);
        return Component.text(message);
    }
}

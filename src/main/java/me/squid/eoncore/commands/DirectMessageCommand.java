package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.utils.FunctionalBukkit;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

@RegisterCommand
public class DirectMessageCommand extends EonCommand {

    public DirectMessageCommand(EonCore plugin) {
        super("message", plugin);
    }

    private Component constructMessage(Player sender, Player target, String[] args) {
        Component format = Messaging.formatDM(core.getConfig(), sender.getName(), target.getName());
        String message = Utils.getMessageFromArgs(args);
        return format.append(Component.text(message));
    }

    private void sendDirectMessage(Player sender, Player target, Component message) {
        target.sendMessage(message);
        sender.sendMessage(message);
    }

    private Consumer<Player> messageFunc(Player sender, String[] args) {
        return target -> {
            Component message = constructMessage(sender, target, args);
            sendDirectMessage(sender, target, message);
        };
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length >= 2) {
            String targetName = args[0];
            args[0] = "";
            FunctionalBukkit.getPlayerOrSendMessage(player, messageFunc(player, args), targetName);
        }
    }
}

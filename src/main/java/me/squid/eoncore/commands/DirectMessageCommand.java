package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Alias;
import org.eonnations.eonpluginapi.api.Command;
import io.vavr.control.Option;

@Command(name = "message",
        usage = "/message <player> <message>",
        aliases = {@Alias(name = "pm"), @Alias(name = "msg"), @Alias(name = "whisper")})
public class DirectMessageCommand extends EonCommand {

    public DirectMessageCommand(EonCore plugin) {
        super(plugin);
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

    private Player message(Player sender, Player target, String[] args) {
        Component message = constructMessage(sender, target, args);
        sendDirectMessage(sender, target, message);
        return target;
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length >= 2) {
            String targetName = args[0];
            args[0] = "";
            Option.of(Bukkit.getPlayer(targetName))
                .map(target -> message(player, target, args))
                .onEmpty(() -> Messaging.sendNullMessage(player));
        }
    }
}

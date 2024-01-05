package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.messaging.Messenger;
import net.kyori.adventure.text.Component;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Alias;
import org.eonnations.eonpluginapi.api.Command;

import io.vavr.control.Option;

@Command(name = "teleport",
        usage = "/teleport <player>",
        aliases = {@Alias(name = "tp")},
        permission = "eoncommands.teleport")
public class TeleportCommand extends EonCommand {
    static final String MESSAGE_PATH = "Teleport-Successful";

    public TeleportCommand(EonCore plugin) {
        super(plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 0) {
            String rawUsage = "<green>Usage: /teleport <player></green>";
            Component message = Messaging.fromFormatString(rawUsage);
            Messenger messenger = Messaging.messenger(EonPrefix.MODERATION);
            messenger.send(player, message);
        } else if (args.length == 1) {
            Option.of(Bukkit.getPlayer(args[0]))
                .map(target -> { teleport.teleport(player, target.getLocation(), MESSAGE_PATH); return target; })
                .onEmpty(() -> Messaging.sendNullMessage(player));
        }
    }
}

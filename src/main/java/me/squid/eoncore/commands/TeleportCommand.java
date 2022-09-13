package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.messaging.Messenger;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.Optional;

import static me.squid.eoncore.utils.FunctionalBukkit.getPlayerFromName;

@RegisterCommand
public class TeleportCommand extends EonCommand {
    static final String MESSAGE_PATH = "Teleport-Successful";

    public TeleportCommand(EonCore plugin) {
        super("teleport", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 0) {
            String rawUsage = "<green>Usage: /teleport <player></green>";
            Component message = Messaging.fromFormatString(rawUsage);
            Messenger messenger = Messaging.messenger(EonPrefix.MODERATION);
            messenger.send(player, message);
        } else if (args.length == 1) {
            Optional<Player> target = getPlayerFromName(args[0]);
            target.ifPresent(toTeleport -> teleport.delayedTeleport(player, toTeleport.getLocation(), MESSAGE_PATH));
        }
    }
}

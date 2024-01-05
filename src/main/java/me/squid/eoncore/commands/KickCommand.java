package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.messaging.Messenger;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Command;

import java.util.Arrays;

import io.vavr.control.Option;

@Command(name = "kick",
        usage = "/kick <player>",
        permission = "eoncommands.kick")
public class KickCommand extends EonCommand {

    private static final String KICK_IMMUNE = "eoncommands.kick.immune";

    public KickCommand(EonCore plugin) {
        super(plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length > 2) {
            String username = args[0];
            Option.of(Bukkit.getPlayer(username))
                .map(target -> kickPlayer(target, reason(args)))
                .onEmpty(() -> Messaging.sendNullMessage(player));
        } else {
            Component usageMessage = Component.text("Usage: /kick <player> <message>");
            Messenger messenger = Messaging.messenger(EonPrefix.NATIONS);
            messenger.send(player, usageMessage);
        }
    }

    private Player kickPlayer(Player player, Component reason) {
        if (player.hasPermission(KICK_IMMUNE)) {
            return player;
        }
        player.kick(reason);    
        return player;
    }

    private Component reason(String[] args) {
        String[] copyArgs = Arrays.copyOf(args, args.length);
        copyArgs[0] = "";
        String message = Utils.getMessageFromArgs(copyArgs);
        return Component.text(message);
    }
}

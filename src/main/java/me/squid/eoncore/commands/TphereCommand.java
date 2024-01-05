package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.messaging.Messenger;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Command;

import io.vavr.control.Option;

import static me.squid.eoncore.messaging.Messaging.fromFormatString;

@Command(name = "tphere", usage = "/tphere <player>", permission = "eoncommands.tphere")
public class TphereCommand extends EonCommand {

    public TphereCommand(EonCore plugin) {
        super(plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 1) {
            Option.of(Bukkit.getPlayer(args[0]))
                .map(target -> teleportHere(player, target))
                .onEmpty(() -> Messaging.sendNullMessage(player));
        } else {
            String rawUsage = "<gray>Usage: /tphere <player></gray>";
            Messenger messenger = Messaging.messenger(EonPrefix.NATIONS);
            messenger.send(player, fromFormatString(rawUsage));
        }
    }

    private Player teleportHere(Player player, Player target) {
        target.teleportAsync(player.getLocation());
        target.playSound(target.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
        player.playSound(target.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
        return target;
    }
}

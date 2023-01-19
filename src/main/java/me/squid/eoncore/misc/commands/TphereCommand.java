package me.squid.eoncore.misc.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.messaging.Messenger;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import static me.squid.eoncore.messaging.Messaging.fromFormatString;
import static me.squid.eoncore.misc.utils.FunctionalBukkit.getPlayerOrSendMessage;

@RegisterCommand
public class TphereCommand extends EonCommand {

    public TphereCommand(EonCore plugin) {
        super("tphere", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 1) {
            getPlayerOrSendMessage(player, target -> teleportHere(player, target), args[0]);
        } else {
            String rawUsage = "<gray>Usage: /tphere <player></gray>";
            Messenger messenger = Messaging.messenger(EonPrefix.NATIONS);
            messenger.send(player, fromFormatString(rawUsage));
        }
    }

    private void teleportHere(Player player, Player target) {
        target.teleportAsync(player.getLocation());
        target.playSound(target.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
        player.playSound(target.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
    }
}

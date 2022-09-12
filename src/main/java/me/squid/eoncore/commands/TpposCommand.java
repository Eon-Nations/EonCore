package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.messaging.Messenger;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static me.squid.eoncore.messaging.Messaging.fromFormatString;

@RegisterCommand
public class TpposCommand extends EonCommand {

    public TpposCommand(EonCore plugin) {
        super("tppos", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 3) {
            double x = Double.parseDouble(args[0]);
            double y = Double.parseDouble(args[1]);
            double z = Double.parseDouble(args[2]);
            Location location = new Location(player.getWorld(), x, y, z);
            teleport.delayedTeleport(player, location);
        } else {
            String rawUsage = "<gray>Usage: /tppos x y z</gray>";
            Messenger messenger = Messaging.messenger(EonPrefix.NATIONS);
            messenger.send(player, fromFormatString(rawUsage));
        }
    }
}

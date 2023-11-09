package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.messaging.Messenger;
import me.squid.eoncore.utils.Utils;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Command;

import static me.squid.eoncore.messaging.Messaging.fromFormatString;

@Command(name = "uptime", usage = "/uptime", permission = "eoncommands.uptime")
public class UptimeCommand extends EonCommand {
    long startupTime;

    public UptimeCommand(EonCore plugin) {
        super(plugin);
        this.startupTime = System.currentTimeMillis();
    }


    @Override
    protected void execute(Player player, String[] args) {
        String rawMessage = Utils.getFormattedTimeString(System.currentTimeMillis() - startupTime);
        Messenger messenger = Messaging.messenger(EonPrefix.MODERATION);
        messenger.send(player, fromFormatString(rawMessage));
    }
}

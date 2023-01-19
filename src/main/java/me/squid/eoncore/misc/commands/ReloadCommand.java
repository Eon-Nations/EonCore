package me.squid.eoncore.misc.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.messaging.Messenger;
import org.bukkit.entity.Player;

import static me.squid.eoncore.messaging.Messaging.fromFormatString;

@RegisterCommand
public class ReloadCommand extends EonCommand {

    public ReloadCommand(EonCore plugin) {
        super("eonreload", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        core.reloadConfig();
        Messenger messenger = Messaging.messenger(EonPrefix.MODERATION);
        messenger.send(player, fromFormatString("<green>Successfully reloaded!</green>"));
    }
}

package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import org.bukkit.entity.Player;

@RegisterCommand
public class DiscordCommand extends EonCommand {

    public DiscordCommand(EonCore plugin) {
        super("discord", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.NATIONS);
        messenger.sendMessage(player, "Discord-Message");
    }
}

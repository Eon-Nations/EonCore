package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import org.bukkit.entity.Player;

@RegisterCommand
public class HatCommand extends EonCommand {

    public HatCommand(EonCore plugin) {
        super("hat", plugin);
    }


    @Override
    protected void execute(Player player, String[] args) {
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.NATIONS);
        if (player.getInventory().getHelmet() == null) {
            player.getInventory().setHelmet(player.getInventory().getItemInMainHand());
            player.getInventory().remove(player.getInventory().getItemInMainHand());
            messenger.sendMessage(player, "Hat-On");
        } else {
            messenger.sendMessage(player, "Hat-Full");
        }
    }
}

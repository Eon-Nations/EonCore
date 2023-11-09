package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.messaging.Messenger;
import me.squid.eoncore.utils.FunctionalBukkit;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.eonnations.eonpluginapi.api.Command;

import java.util.Optional;

@Command(name = "invsee", usage = "/invsee <player>", permission = "eoncommands.invsee")
public class InvseeCommand extends EonCommand {
    static final String IMMUNE_NODE = "eoncommands.invsee.immune";

    public InvseeCommand(EonCore plugin) {
        super(plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 0) {
            Component usageMessage = Component.text("Usage: /invsee <player>");
            Messenger messenger = Messaging.messenger(EonPrefix.NATIONS);
            messenger.send(player, usageMessage);
        } else if (args.length == 1) {
            Optional<Player> maybeTarget = FunctionalBukkit.getPlayerFromName(args[0])
                    .filter(target -> !target.hasPermission(IMMUNE_NODE));
            maybeTarget.ifPresentOrElse(target -> openInventory(player, target), () -> Messaging.sendNullMessage(player));
        }
    }

    private void openInventory(Player receiver, Player target) {
        Inventory targetInv = target.getInventory();
        receiver.openInventory(targetInv);
    }
}

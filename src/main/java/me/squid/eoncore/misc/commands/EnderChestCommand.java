package me.squid.eoncore.misc.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.misc.utils.FunctionalBukkit;
import org.bukkit.entity.Player;

import java.util.Optional;

@RegisterCommand
public class EnderChestCommand extends EonCommand {
    static final String IMMUNE_NODE = "eoncommands.enderchest.others";

    public EnderChestCommand(EonCore plugin) {
        super("enderchest", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 0) {
            player.openInventory(player.getEnderChest());
        } else if (args.length == 1 && player.hasPermission(IMMUNE_NODE)) {
            Optional<Player> maybeTarget = FunctionalBukkit.getPlayerFromName(args[0]);
            maybeTarget.ifPresentOrElse(target -> player.openInventory(target.getEnderChest()),
                    () -> Messaging.sendNullMessage(player));
        }
    }
}

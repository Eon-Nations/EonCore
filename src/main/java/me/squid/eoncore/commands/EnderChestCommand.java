package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.utils.FunctionalBukkit;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Alias;
import org.eonnations.eonpluginapi.api.Command;

import java.util.Optional;

@Command(name = "enderchest",
        usage = "/enderchest <player>",
        aliases = {@Alias(name = "ec"), @Alias(name = "echest")},
        permission = "eoncommands.enderchest")
public class EnderChestCommand extends EonCommand {
    static final String OTHERS_NODE = "eoncommands.enderchest.others";

    public EnderChestCommand(EonCore plugin) {
        super(plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 0) {
            player.openInventory(player.getEnderChest());
        } else if (args.length == 1 && player.hasPermission(OTHERS_NODE)) {
            Optional<Player> maybeTarget = FunctionalBukkit.getPlayerFromName(args[0]);
            maybeTarget.ifPresentOrElse(target -> player.openInventory(target.getEnderChest()),
                    () -> Messaging.sendNullMessage(player));
        }
    }
}

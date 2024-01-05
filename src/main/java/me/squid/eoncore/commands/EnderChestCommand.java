package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.Messaging;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Alias;
import org.eonnations.eonpluginapi.api.Command;

import io.vavr.control.Option;

@Command(name = "enderchest",
        usage = "/enderchest <player>",
        aliases = {@Alias(name = "ec"), @Alias(name = "echest")},
        permission = "eoncommands.enderchest")
public class EnderChestCommand extends EonCommand {
    static final String OTHERS_NODE = "eoncommands.enderchest.others";

    public EnderChestCommand(EonCore plugin) {
        super(plugin);
    }

    private Player openEnderChest(Player player, Player target) {
        player.openInventory(target.getEnderChest());
        return target;
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 0) {
            player.openInventory(player.getEnderChest());
        } else if (args.length == 1 && player.hasPermission(OTHERS_NODE)) {
            Option.of(Bukkit.getPlayer(args[0]))
                .map(target -> openEnderChest(player, target))
                .onEmpty(() -> Messaging.sendNullMessage(player));
        }
    }
}

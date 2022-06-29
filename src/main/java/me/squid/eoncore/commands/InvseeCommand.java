package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.FunctionalBukkit;
import me.squid.eoncore.utils.Messaging;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Optional;

public class InvseeCommand implements CommandExecutor {

    EonCore plugin;
    static final String IMMUNE_NODE = "eoncommands.invsee.immune";

    public InvseeCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("invsee").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 0) {
                Messaging.sendNationsMessage(p, "Usage: /invsee <player>");
            } else if (args.length == 1) {
                Optional<Player> maybeTarget = FunctionalBukkit.getPlayerFromName(args[0])
                        .filter(target -> !target.hasPermission(IMMUNE_NODE));
                maybeTarget.ifPresentOrElse(target -> openInventory(p, target), () -> Messaging.sendNullMessage(p));
            }
        }
        return true;
    }

    private void openInventory(Player receiver, Player target) {
        Inventory targetInv = target.getInventory();
        receiver.openInventory(targetInv);
    }
}

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
import java.util.function.Consumer;

public class EnderChestCommand implements CommandExecutor {

    EonCore plugin;
    static final String IMMUNE_NODE = "eoncommands.enderchest.others";

    public EnderChestCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("enderchest").setExecutor(this);
    }

    private Consumer<Player> targetOpenEC(Player sender) {
        return target -> {
            Inventory targetEC = target.getEnderChest();
            sender.openInventory(targetEC);
        };
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            p.openInventory(p.getEnderChest());
        } else if (args.length == 1 && p.hasPermission(IMMUNE_NODE)) {
            Optional<Player> maybeTarget = FunctionalBukkit.getPlayerFromName(args[0]);
            maybeTarget.ifPresentOrElse(targetOpenEC(p), () -> Messaging.sendNullMessage(p));
        }
        return true;
    }
}

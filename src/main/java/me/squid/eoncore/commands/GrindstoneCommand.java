package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class GrindstoneCommand implements CommandExecutor {

    EonCore plugin;

    public GrindstoneCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("grindstone").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            Inventory inv = Bukkit.createInventory(null, InventoryType.GRINDSTONE);
            p.openInventory(inv);
        }

        return true;
    }
}

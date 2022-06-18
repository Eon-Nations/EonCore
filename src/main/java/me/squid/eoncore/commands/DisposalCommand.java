package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class DisposalCommand implements CommandExecutor {

    EonCore plugin;

    public DisposalCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("disposal").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String label, String[] args) {
        Player p = (Player) commandSender;
        Inventory trash = Bukkit.createInventory(null, 54);
        p.openInventory(trash);
        return true;
    }
}

package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.menus.AdminGUI;
import me.squid.eoncore.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminGUICommand implements CommandExecutor {

    EonCore plugin;
    AdminGUI adminGUI;

    public AdminGUICommand(EonCore plugin, AdminGUI adminGUI) {
        this.plugin = plugin;
        this.adminGUI = adminGUI;
        plugin.getCommand("admin").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            p.openInventory(adminGUI.GUI());
        }

        return true;
    }

    public String getPermissionNode(){
        return "eoncommands.admin";
    }
}

package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class EnderChestCommand implements CommandExecutor {

    EonCore plugin;

    public EnderChestCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("enderchest").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (p.hasPermission(getPermissionNode())){
                if (args.length == 0){
                    p.openInventory(p.getEnderChest());
                } else if (args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null && p.hasPermission(getOthersPermNode())){
                        Inventory targetEC = target.getEnderChest();
                        p.openInventory(targetEC);
                    } else {
                        p.sendMessage(Utils.chat(plugin.getConfig().getString("Target-Null")));
                    }
                }
            } else {
                p.sendMessage(Utils.chat(plugin.getConfig().getString("No-Perms")));
            }
        }

        return true;
    }

    public String getPermissionNode(){
        return "eoncommands.enderchest";
    }

    public String getOthersPermNode(){
        return "eoncommands.enderchest.others";
    }
}

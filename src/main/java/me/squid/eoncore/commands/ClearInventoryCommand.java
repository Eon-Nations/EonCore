package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearInventoryCommand implements CommandExecutor {

    EonCore plugin;

    public ClearInventoryCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("clearinventory").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0){
            if (sender instanceof Player p) {
                p.getInventory().clear();
                p.sendMessage(Utils.chat(Utils.getPrefix("nations") + plugin.getConfig().getString("Clear-Self-Inventory")));
            }
        } else if (args.length == 1){
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null && sender.hasPermission(getOthersPermNode())) {
                target.getInventory().clear();
                target.sendMessage(Utils.chat(plugin.getConfig().getString("Target-Clear-Inventory")));
                sender.sendMessage(Utils.chat(plugin.getConfig().getString("Clear-Other-Inventory")
                .replace("<target>", target.getName())));
            }
        }
        return true;
    }

    public String getPermissionNode(){
        return "eoncommands.clearinventory";
    }

    public String getOthersPermNode(){
        return "eoncommands.clearinventory.others";
    }
}

package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class InvseeCommand implements CommandExecutor {

    EonCore plugin;

    public InvseeCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("invsee")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (p.hasPermission(getPermissionNode())){
                if (args.length == 0){
                    p.sendMessage(Utils.chat("&bUsage: /invsee <player>"));
                } else if (args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null && !target.hasPermission(getImmuneNode())){
                        Inventory targetInv = target.getInventory();
                        p.openInventory(targetInv);
                    }
                }
            } else {
                p.sendMessage(Utils.chat(plugin.getConfig().getString("No-Perms")));
            }
        }

        return true;
    }

    public String getPermissionNode(){
        return "eoncommands.invsee";
    }

    public String getImmuneNode(){
        return "eoncommands.invsee.immune";
    }
}

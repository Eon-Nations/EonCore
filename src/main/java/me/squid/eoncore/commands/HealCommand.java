package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class HealCommand implements CommandExecutor {

    EonCore plugin;

    public HealCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("heal")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (p.hasPermission(getPermissionNode())){
                if (args.length == 0){
                    p.setHealth(20);
                    p.setFoodLevel(20);
                    p.sendMessage(Utils.chat(plugin.getConfig().getString("Heal-Message")));
                } else if (args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null && p.hasPermission(getOthersPermNode())) {
                        target.setFoodLevel(20);
                        target.setHealth(20);
                        target.sendMessage(Utils.chat(plugin.getConfig().getString("Heal-Message")));
                        p.sendMessage(Utils.chat(Objects.requireNonNull(plugin.getConfig().getString("Heal-Other")).replace("<player>", target.getName())));
                    } else {
                        p.sendMessage(Utils.chat(EonCore.prefix + plugin.getConfig().getString("Target-Null")));
                    }
                }
            } else {
                p.sendMessage(Utils.chat(plugin.getConfig().getString("No-Perms")));
            }
        }

        return true;
    }

    public String getPermissionNode(){
        return "eoncommands.heal";
    }

    public String getOthersPermNode(){
        return "eoncommands.heal.others";
    }
}

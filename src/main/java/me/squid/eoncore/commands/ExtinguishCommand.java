package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExtinguishCommand implements CommandExecutor {

    EonCore plugin;

    public ExtinguishCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("extinguish").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0){
            if (sender instanceof Player){
                Player p = (Player) sender;
                if (p.hasPermission(getPermissionNode())){
                    p.setFireTicks(0);
                    p.sendMessage(Utils.chat(EonCore.prefix + plugin.getConfig().getString("Extinguish-Message")));
                } else {
                    p.sendMessage(Utils.chat(EonCore.prefix + plugin.getConfig().getString("No-Perms")));
                }
            }
        } else if (args.length == 1){
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null && sender.hasPermission(getOthersPermNode())) {
                target.setFireTicks(0);
                target.sendMessage(Utils.chat(EonCore.prefix + plugin.getConfig().getString("Target-Extinguish-Message")));
                sender.sendMessage(Utils.chat(EonCore.prefix + plugin.getConfig().getString("Extinguish-Other-Message")
                .replace("<target>", target.getDisplayName())));
            }
        }

        return true;
    }

    public String getPermissionNode(){
        return "eoncommands.extinguish";
    }

    public String getOthersPermNode(){
        return "eoncommands.extinguish.others";
    }
}

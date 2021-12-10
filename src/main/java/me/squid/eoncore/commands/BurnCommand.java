package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BurnCommand implements CommandExecutor {

    EonCore plugin;

    public BurnCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("burn").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String label, String[] args) {

        if (commandSender instanceof Player){
            Player p = (Player) commandSender;
            if (p.hasPermission(getPermissionNode())){
                if (args.length == 0){
                    p.sendMessage(Utils.chat("&4Burn fools!"));
                    p.sendMessage(Utils.chat("&bUsage: /burn <player> <seconds>"));
                } else if (args.length == 2){
                    Player target = Bukkit.getPlayer(args[0]);
                    int seconds = Integer.parseInt(args[1]);
                    if (target != null) {
                        target.setFireTicks(seconds * 20);
                        target.sendMessage(Utils.getPrefix("nations").append(Utils.chat(plugin.getConfig().getString("Target-Burn-Message"))));
                        p.sendMessage(Utils.getPrefix("nations").append(Utils.chat(plugin.getConfig().getString("Burn-Message")
                        .replace("<target>", target.getDisplayName()).replace("<seconds>", String.valueOf(seconds)))));
                    } else {
                        p.sendMessage(Utils.getPrefix("nations").append(Utils.chat(plugin.getConfig().getString("Target-Null"))));
                    }
                }
            } else {
                p.sendMessage(Utils.chat(plugin.getConfig().getString("No-Perms")));
            }
        }
        return true;
    }

    public String getPermissionNode(){
        return "eoncommands.burn";
    }
}

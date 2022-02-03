package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SurvivalCommand implements CommandExecutor{

    EonCore plugin;

    public SurvivalCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("gms").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player p){
            if (args.length == 0) {
                p.setGameMode(GameMode.SURVIVAL);
                p.sendMessage(Utils.getPrefix("nations") + Utils.chat(plugin.getConfig().getString("Survival-Message")));
            } else if (args.length == 1 && p.hasPermission(getOthersPermNode())) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    target.setGameMode(GameMode.SURVIVAL);
                    target.sendMessage(Utils.getPrefix("nations")
                            + Utils.chat(plugin.getConfig().getString("Survival-Message")));
                    p.sendMessage(Utils.getPrefix("nations") + Utils.chat(plugin.getConfig().getString("Survival-Other")
                            .replace("<target>", target.getName())));
                }
            }
        }

        return true;
    }

    public String getPermissionNode(){
        return "eoncommands.gms";
    }

    public String getOthersPermNode(){
        return "eoncommands.gms.others";
    }
}

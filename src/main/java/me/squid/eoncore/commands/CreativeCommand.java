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

public class CreativeCommand implements CommandExecutor {

    EonCore plugin;

    public CreativeCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("gmc").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (args.length == 0){
                p.setGameMode(GameMode.CREATIVE);
                p.sendMessage(Utils.chat(plugin.getConfig().getString("Creative-Message")));
            } else if (args.length == 1 && p.hasPermission(getOthersPermNode())) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    target.setGameMode(GameMode.CREATIVE);
                    target.sendMessage(Utils.chat(plugin.getConfig().getString("Creative-Message")));
                    p.sendMessage(Utils.chat(plugin.getConfig().getString("Creative-Other")
                            .replace("<target>", target.getName())));
                }
            }
        }
        return true;
    }

    public String getOthersPermNode(){
        return "eoncommands.gmc.others";
    }
}

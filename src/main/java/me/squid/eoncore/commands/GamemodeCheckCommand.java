package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCheckCommand implements CommandExecutor {

    EonCore plugin;

    public GamemodeCheckCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("gamemodecheck").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(Utils.chat("&bUsage: /gamemodecheck <player>"));
        } else if (args.length == 1){
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null){
                sender.sendMessage(Utils.chat(plugin.getConfig().getString("GamemodeCheck-Message")
                .replace("<target>", target.getName())
                .replace("<gamemode>", StringUtils.capitalize(target.getGameMode().toString().toLowerCase()))));
            } else {
                sender.sendMessage(Utils.chat(plugin.getConfig().getString("Target-Null")));
            }
        }

        return true;
    }
}

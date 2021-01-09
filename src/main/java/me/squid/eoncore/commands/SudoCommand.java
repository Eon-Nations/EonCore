package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SudoCommand implements CommandExecutor {

    EonCore plugin;

    public SudoCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("sudo").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player) {
            if (args.length > 2) {
                Player target = Bukkit.getPlayer(args[0]);
                if (args[1].contains("/") && target != null) {
                    if (target.isOp()) return true;
                    args[1] = args[1].replace("/", "");
                    Bukkit.dispatchCommand(target, getMessage(args));
                } else if (target != null) {
                    args[0] = "";
                    target.chat(getMessage(args));
                }
            }
        }

        return true;
    }

    private String getMessage(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append(" ");
        }
        String allArgs = sb.toString().trim();
        return Utils.chat(allArgs);
    }
}

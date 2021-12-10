package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class DirectMessageCommand implements CommandExecutor {
    EonCore plugin;

    public DirectMessageCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("message")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length >= 2) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    args[0] = "";
                    target.sendMessage(Utils.chat("&7[&6" + sender.getName() + "&r&7 -> &6" + target.getName() + "&7] >> ")
                            .append(getMessage(args)));
                    sender.sendMessage(Utils.chat("&7[&6" + sender.getName() + "&r&7 -> &6" + target.getName() + "&7] >> ")
                            .append(getMessage(args)));
                } else return false;
            }
        }
        return true;
    }

    private Component getMessage(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append(" ");
        }
        String allArgs = sb.toString().trim();
        return Utils.chat(allArgs);
    }
}

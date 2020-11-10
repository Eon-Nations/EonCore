package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Objects;

public class StaffChatCommand implements CommandExecutor {

    EonCore plugin;

    public StaffChatCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("staffchat")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length > 0) {
            String message = getMessage(args);
            String prefix = "&5&lStaffChat &7>> " + sender.getName() + ": ";
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (online.hasPermission("eoncommands.staffchat")) {
                    online.sendMessage(Utils.chat(prefix + message));
                }
            }
        } else {
            sender.sendMessage(Utils.chat(EonCore.prefix + "&7Usage: /sc <message>"));
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

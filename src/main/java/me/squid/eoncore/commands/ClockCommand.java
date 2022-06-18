package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.listeners.ChatFormatListener;
import me.squid.eoncore.utils.EonPrefix;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static me.squid.eoncore.utils.EonPrefix.getPrefix;

public class ClockCommand implements CommandExecutor {

    EonCore plugin;

    public ClockCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("clock").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!ChatFormatListener.isChatLocked()) {
            ChatFormatListener.setChatLocked(true);
            Bukkit.broadcastMessage(getPrefix(EonPrefix.MODERATION) +
                    Utils.translateHex("#ff0000Chat is locked. Please wait while we resolve the conflict. Thank you for your patience"));
        } else ChatFormatListener.setChatLocked(false);
        return true;
    }
}

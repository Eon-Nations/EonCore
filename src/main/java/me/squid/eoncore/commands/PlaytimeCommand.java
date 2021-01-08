package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Calendar;

public class PlaytimeCommand implements CommandExecutor {

    EonCore plugin;

    public PlaytimeCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("playtime").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {



        return true;
    }
}

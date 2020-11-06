package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class WorkbenchCommand implements CommandExecutor {

    EonCore plugin;

    public WorkbenchCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("workbench")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (p.hasPermission("eoncommands.workbench")) {
                p.openWorkbench(p.getLocation(), true);
            }
        }

        return true;
    }
}

package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.events.WildTeleportEvent;
import me.squid.eoncore.menus.WildMenu;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class WildTpCommand implements CommandExecutor {

    EonCore plugin;

    public WildTpCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("wild").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        WildMenu menu = new WildMenu();

        if (sender instanceof Player) { ((Player) sender).openInventory(menu.MainGUI()); }
        return true;
    }
}


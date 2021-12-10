package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.menus.AdminGUI;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MutedCommand implements CommandExecutor {

    EonCore plugin;
    AdminGUI adminGUI;

    public MutedCommand(EonCore plugin, AdminGUI adminGUI) {
        this.plugin = plugin;
        this.adminGUI = adminGUI;
        plugin.getCommand("muted").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;

        if (p.hasPermission("eoncommands.muted")) {
            p.openInventory(adminGUI.getMutedInventory());
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
        }
        return true;
    }
}

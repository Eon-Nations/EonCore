package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.menus.HelpGUI;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class HelpCommand implements CommandExecutor {

    EonCore plugin;
    HelpGUI helpGUI = new HelpGUI();

    public HelpCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("help").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
            p.openInventory(helpGUI.buildInventory());
        }
        return true;
    }
}

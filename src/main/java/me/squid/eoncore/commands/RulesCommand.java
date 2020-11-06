package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.menus.RulesGUI;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class RulesCommand implements CommandExecutor {

    EonCore plugin;
    RulesGUI rulesGUI = new RulesGUI();

    public RulesCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("rules")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
            p.openInventory(rulesGUI.Categories());
        }

        return true;
    }
}

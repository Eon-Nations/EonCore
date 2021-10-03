package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.menus.RanksMenu;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class RanksCommand implements CommandExecutor {

    EonCore plugin;
    RanksMenu ranksMenu;

    public RanksCommand(EonCore plugin) {
        this.plugin = plugin;
        this.ranksMenu = new RanksMenu(plugin);
        Objects.requireNonNull(plugin.getCommand("ranks")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            p.openInventory(ranksMenu.getInv());
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
        }

        return true;
    }
}

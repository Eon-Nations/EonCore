package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.menus.VoteRanksGUI;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoteRanksCommand implements CommandExecutor {

    EonCore plugin;
    VoteRanksGUI voteRanksGUI = new VoteRanksGUI();

    public VoteRanksCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("voteranks").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            p.openInventory(voteRanksGUI.MainGUI());
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
        }
        return true;
    }
}

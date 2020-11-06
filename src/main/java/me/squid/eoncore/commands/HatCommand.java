package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HatCommand implements CommandExecutor {

    EonCore plugin;

    public HatCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("hat").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.getInventory().getHelmet() == null) {
                p.getInventory().setHelmet(p.getInventory().getItemInMainHand());
                p.sendMessage(Utils.chat(EonCore.prefix + "&bHat On"));
                p.getInventory().remove(p.getInventory().getItemInMainHand());
            } else {
                p.sendMessage(Utils.chat(EonCore.prefix + "&bTake off your helmet to put on the hat"));
            }
        }

        return true;
    }
}

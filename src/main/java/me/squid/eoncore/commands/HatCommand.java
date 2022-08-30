package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.Messaging;
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
        if (sender instanceof Player p) {
            if (p.getInventory().getHelmet() == null) {
                p.getInventory().setHelmet(p.getInventory().getItemInMainHand());
                p.getInventory().remove(p.getInventory().getItemInMainHand());
            } else {
                Messaging.sendNationsMessage(p, "Take off your helmet to put on the hat");
            }
        }
        return true;
    }
}

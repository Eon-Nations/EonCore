package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class PickupCommand implements CommandExecutor {

    EonCore plugin;

    public PickupCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("pickup")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0){
            sender.sendMessage(Utils.chat("&bUsage: /pickup <player>"));
        } else if (args.length == 1){
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null){
                if (target.getCanPickupItems()){
                    target.setCanPickupItems(false);
                    sender.sendMessage(Utils.chat(Objects.requireNonNull(plugin.getConfig().getString("Pickup-Off"))
                    .replace("<target>", target.getName())));
                } else {
                    target.setCanPickupItems(true);
                    sender.sendMessage(Utils.chat(Objects.requireNonNull(plugin.getConfig().getString("Pickup-On"))
                    .replace("<target>", target.getName())));
                }
            } else {
                sender.sendMessage(Utils.chat(plugin.getConfig().getString("Target-Null")));
            }
        }

        return true;
    }
}

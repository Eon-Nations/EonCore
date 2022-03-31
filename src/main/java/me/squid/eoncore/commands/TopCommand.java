package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TopCommand implements CommandExecutor {

    EonCore plugin;

    public TopCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("top")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;

            Location top = p.getWorld().getHighestBlockAt(p.getLocation()).getLocation();

            p.teleport(top.add(0, 1, 0));
            p.sendMessage(Utils.chat(Utils.getPrefix("nations") + plugin.getConfig().getString("Top-Message")));
        }
        return true;
    }
}

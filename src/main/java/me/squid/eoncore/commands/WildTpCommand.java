package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.events.WildTeleportEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WildTpCommand implements CommandExecutor {

    EonCore plugin;

    public WildTpCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("wild").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            World world = Bukkit.getWorld("world");
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> Bukkit.getPluginManager().callEvent(new WildTeleportEvent(p, world, false)));
        }
        return true;
    }
}


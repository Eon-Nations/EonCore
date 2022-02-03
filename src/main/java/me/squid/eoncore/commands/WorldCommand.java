package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class WorldCommand implements CommandExecutor {

    EonCore plugin;

    public WorldCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("worldlist").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (commandSender instanceof Player p) {
            if (args.length == 0) {
                List<World> worlds = Bukkit.getWorlds();
                p.sendMessage("Worlds: " + worlds);
            } else if (args.length == 1) {
                World world = Bukkit.getWorld(args[0]);
                p.teleport(world.getSpawnLocation());
                p.sendMessage("Succesfully teleported to " + world.getName());
            }
        }
        return true;
    }
}

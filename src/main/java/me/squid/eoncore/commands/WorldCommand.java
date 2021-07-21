package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WorldCommand implements CommandExecutor {

    EonCore plugin;

    public WorldCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("worldlist").setExecutor(this);
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            List<World> worlds = Bukkit.getWorlds();

            if (args.length == 0) {
                p.sendMessage(Component.text("Worlds: " + worlds).color(TextColor.color(128, 128, 128)));
            } else if (args.length == 1) {
                World world = Bukkit.getWorld(args[0]);
                p.teleportAsync(world.getSpawnLocation());
                p.sendMessage(Component.text("Succesfully teleported to " + world.getName()).color(TextColor.color(128, 128, 128)));
            }
        }

        return true;
    }
}

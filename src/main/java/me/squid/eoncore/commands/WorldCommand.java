package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Command;

import java.util.List;

@Command(name = "world", usage = "/world <worldname>", permission = "eoncommands.world")
public class WorldCommand extends EonCommand {

    public WorldCommand(EonCore plugin) {
        super(plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 0) {
            List<String> worlds = Bukkit.getWorlds().stream()
                    .map(World::getName)
                    .toList();
            player.sendMessage("Worlds: " + worlds);
        } else if (args.length == 1) {
            World world = Bukkit.getWorld(args[0]);
            player.teleportAsync(world.getSpawnLocation());
            player.sendMessage("Succesfully teleported to " + world.getName());
        }
    }
}

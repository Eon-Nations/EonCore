package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.holograms.FloatingItem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Command;

@Command(name = "spawnitem",
        usage = "/spawnitem <material>")
public class SpawnItemCommand extends EonCommand {

    EonCore plugin;

    public SpawnItemCommand(EonCore plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    protected void execute(Player player, String[] args) {
        Material material = Material.valueOf(args[0]);
        FloatingItem item = new FloatingItem(player.getLocation(), material);
        Bukkit.getScheduler().runTaskLater(plugin, () -> item.close(), 300L);
    }
}

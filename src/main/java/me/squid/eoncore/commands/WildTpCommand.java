package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.events.WildTeleportEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

@RegisterCommand
public class WildTpCommand extends EonCommand {

    public WildTpCommand(EonCore plugin) {
        super("wild", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        World world = Bukkit.getWorld("world");
        WildTeleportEvent wildTeleportEvent = new WildTeleportEvent(player, world, false);
        Bukkit.getScheduler().runTaskAsynchronously(core, () -> Bukkit.getPluginManager().callEvent(wildTeleportEvent));
    }
}


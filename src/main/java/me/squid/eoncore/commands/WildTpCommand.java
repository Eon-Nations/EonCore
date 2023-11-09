package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.misc.events.WildTeleportEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Command;

@Command(name = "wild", usage = "/wild")
public class WildTpCommand extends EonCommand {

    public WildTpCommand(EonCore plugin) {
        super(plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        World world = Bukkit.getWorld("world");
        WildTeleportEvent wildTeleportEvent = new WildTeleportEvent(player, world, false);
        Bukkit.getScheduler().runTaskAsynchronously(core, () -> Bukkit.getPluginManager().callEvent(wildTeleportEvent));
    }
}


package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Command;

@Command(name = "top", usage = "/top", permission = "eoncommands.top")
public class TopCommand extends EonCommand {

    public TopCommand(EonCore plugin) {
        super(plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        Location top = player.getWorld().getHighestBlockAt(player.getLocation()).getLocation().add(0, 1, 0);
        teleport.delayedTeleport(player, top, "Top-Message");
    }
}

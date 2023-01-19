package me.squid.eoncore.misc.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@RegisterCommand
public class TopCommand extends EonCommand {

    public TopCommand(EonCore plugin) {
        super("top", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        Location top = player.getWorld().getHighestBlockAt(player.getLocation()).getLocation().add(0, 1, 0);
        teleport.delayedTeleport(player, top, "Top-Message");
    }
}

package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import org.bukkit.entity.Player;

@RegisterCommand
public class WorkbenchCommand extends EonCommand {

    public WorkbenchCommand(EonCore plugin) {
        super("workbench", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        player.openWorkbench(player.getLocation(), true);
    }
}

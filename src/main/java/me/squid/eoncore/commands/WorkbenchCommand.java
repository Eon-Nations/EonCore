package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Command;

@Command(name = "workbench", usage = "/workbench <player>", permission = "eoncommands.workbench")
public class WorkbenchCommand extends EonCommand {

    public WorkbenchCommand(EonCore plugin) {
        super(plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        player.openWorkbench(player.getLocation(), true);
    }
}

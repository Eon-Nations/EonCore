package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@RegisterCommand
public class DisposalCommand extends EonCommand {

    public DisposalCommand(EonCore plugin) {
        super("disposal", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        Inventory trash = Bukkit.createInventory(null, 54);
        player.openInventory(trash);
    }
}

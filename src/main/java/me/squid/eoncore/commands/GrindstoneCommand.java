package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

@RegisterCommand
public class GrindstoneCommand extends EonCommand {

    public GrindstoneCommand(EonCore plugin) {
        super("grindstone", plugin);
        plugin.getCommand("grindstone").setExecutor(this);
    }


    @Override
    protected void execute(Player player, String[] args) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.GRINDSTONE);
        player.openInventory(inv);
    }
}

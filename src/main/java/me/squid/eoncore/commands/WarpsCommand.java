package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.misc.managers.InventoryManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@RegisterCommand
public class WarpsCommand extends EonCommand {

    public WarpsCommand(EonCore plugin) {
        super("warps", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        Inventory inventory = InventoryManager.staleInventory("warps");
        player.openInventory(inventory);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
    }
}

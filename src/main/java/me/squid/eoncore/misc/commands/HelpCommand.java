package me.squid.eoncore.misc.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.misc.managers.InventoryManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@RegisterCommand
public class HelpCommand extends EonCommand {

    public HelpCommand(EonCore plugin) {
        super("help", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
        Inventory inventory = InventoryManager.staleInventory("help");
        player.openInventory(inventory);
    }
}

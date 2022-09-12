package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.menus.WarpsGUI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@RegisterCommand
public class WarpsCommand extends EonCommand {
    WarpsGUI warpsGUI = new WarpsGUI();

    public WarpsCommand(EonCore plugin) {
        super("warps", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        player.openInventory(warpsGUI.SelectWarps());
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
    }
}

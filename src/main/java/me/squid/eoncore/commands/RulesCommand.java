package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class RulesCommand extends EonCommand {

    public RulesCommand(EonCore plugin) {
        super("rules", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
    }
}

package me.squid.eoncore.tasks;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.commands.FixCommand;
import me.squid.eoncore.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FixCommandCooldownTask extends BukkitRunnable {

    Player p;

    public FixCommandCooldownTask(Player p) {
        this.p = p;
    }

    @Override
    public void run() {
        FixCommand.removePlayerFromList(p);
        p.sendMessage(Utils.chat(EonCore.prefix + "&7You can use /fix again"));
    }
}

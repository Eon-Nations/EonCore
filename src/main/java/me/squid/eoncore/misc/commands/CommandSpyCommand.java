package me.squid.eoncore.misc.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashSet;
import java.util.Set;

@RegisterCommand
public class CommandSpyCommand extends EonCommand implements Listener {
    private final Set<Player> peopleSpying = new HashSet<>();
    private static final String IMMUNE_PERM = "eoncommands.commandspy.immune";

    public CommandSpyCommand(EonCore plugin) {
        super("commandspy", plugin);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.MODERATION);
        if (!peopleSpying.contains(player)) {
            peopleSpying.add(player);
            messenger.sendMessage(player, "CommandSpy-On");
        } else {
            peopleSpying.remove(player);
            messenger.sendMessage(player, "CommandSpy-Off");
        }
    }

    @EventHandler
    public void onCommandSend(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String cmd = e.getMessage();
        String commandFormat = core.getConfig().getString("CommandSpy-Message")
                .replace("<player>", p.getName())
                .replace("<command>", cmd);
        Component message = Messaging.fromFormatString(commandFormat);

        if (!p.hasPermission(IMMUNE_PERM)) {
            peopleSpying.forEach(spy -> spy.sendMessage(message));
        }
    }
}

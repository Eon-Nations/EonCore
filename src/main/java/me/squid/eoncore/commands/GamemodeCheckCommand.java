package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.FunctionalBukkit;
import me.squid.eoncore.utils.Messaging;
import me.squid.eoncore.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class GamemodeCheckCommand implements CommandExecutor {
    EonCore plugin;

    public GamemodeCheckCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("gamemodecheck").setExecutor(this);
    }

    private void sendGamemodeMessage(CommandSender sender, Player target) {
        Optional<String> maybeGamemodeMessage = Optional.ofNullable(plugin.getConfig().getString("GamemodeCheck-Message"));
        if (maybeGamemodeMessage.isEmpty()) {
            Messaging.sendNationsMessage(sender, "Config message missing. Let the devs know");
            return;
        }
        String gamemodeMessage = maybeGamemodeMessage.get();
        gamemodeMessage = gamemodeMessage.replace("<target>", target.getName());
        gamemodeMessage = gamemodeMessage.replace("<gamemode>", StringUtils.capitalize(target.getGameMode().toString().toLowerCase()));
        Messaging.sendNationsMessage(sender, gamemodeMessage);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            Optional<Player> maybeTarget = FunctionalBukkit.getPlayerFromName(args[0]);
            maybeTarget.ifPresentOrElse(target -> sendGamemodeMessage(sender, target),
                    () -> Messaging.sendNullMessage(sender, plugin.getConfig()));
        } else Messaging.sendNationsMessage(sender, "Usage: /gmcheck <player>");
        return true;
    }
}

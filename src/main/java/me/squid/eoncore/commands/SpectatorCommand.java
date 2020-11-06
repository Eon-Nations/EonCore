package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SpectatorCommand implements CommandExecutor {

    EonCore plugin;

    public SpectatorCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("gmsp")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission(getPermissionNode())) {
                if (args.length == 0) {
                    p.setGameMode(GameMode.SPECTATOR);
                    p.sendMessage(Utils.chat(plugin.getConfig().getString("Spectator-Message")));
                } else if (args.length == 1 && p.hasPermission(getOthersPermNode())) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null && target.getGameMode() != GameMode.SPECTATOR) {
                        target.setGameMode(GameMode.SPECTATOR);
                        target.sendMessage(Utils.chat(plugin.getConfig().getString("Spectator-Message")));
                        p.sendMessage(Utils.chat(Objects.requireNonNull(plugin.getConfig().getString("Spectator-Other"))
                        .replace("<target>", target.getName())));
                    }
                }
            } else {
                p.sendMessage(Utils.chat(plugin.getConfig().getString("No-Perms")));
            }
        }
        return true;
    }

    public String getPermissionNode() {
        return "eoncommands.gmsp";
    }

    public String getOthersPermNode() {
        return "eoncommands.gmsp.others";
    }
}

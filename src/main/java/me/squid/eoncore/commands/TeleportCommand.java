package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TeleportCommand implements CommandExecutor {

    EonCore plugin;

    public TeleportCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("teleport")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (commandSender instanceof Player){
            Player p = (Player) commandSender;
            if (args.length == 0){
                p.sendMessage(Utils.chat("&7[&5&lEon Survival&r&7] Usage: /teleport <player>"));
            } else if (args.length == 1){
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null){
                    p.teleport(target.getLocation());
                    p.sendMessage(Utils.chat(Utils.getPrefix("nations") + Objects.requireNonNull(plugin.getConfig().getString("Teleport-Message"))
                    .replace("<player>", target.getDisplayName())));
                }
            }
        }

        return true;
    }
}

package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TphereCommand implements CommandExecutor {

    EonCore plugin;

    public TphereCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("tphere")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;

            if (args.length == 1){
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    target.teleport(p.getLocation());
                    target.playSound(target.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    p.playSound(target.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    p.sendMessage(Utils.chat(EonCore.prefix + Objects.requireNonNull(plugin.getConfig().getString("Tphere-Message"))
                    .replace("<target>", target.getDisplayName())));
                } else {
                    p.sendMessage(Utils.chat(plugin.getConfig().getString("Target-Null")));
                }
            } else {
                p.sendMessage(Utils.chat("&7[&5&lEon Survival&r&7] Usage: /tphere <player>"));
            }
        }

        return true;
    }
}

package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.events.BackToDeathLocationEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class BackCommand implements CommandExecutor {

    EonCore plugin;

    public BackCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("back")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            boolean hasCooldown;
            hasCooldown = !p.hasPermission("eoncommands.back.cooldown.bypass");
            Bukkit.getPluginManager().callEvent(new BackToDeathLocationEvent(p, hasCooldown));
        }
        return true;
    }
}

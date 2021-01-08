package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TpDenyCommand implements CommandExecutor {

    EonCore plugin;

    final String prefix = "&7[&5&lEon Survival&7]&r ";

    public TpDenyCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("tpdeny")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (TpaCommand.getRequests().containsKey(p.getUniqueId())) {
                Player requester = Bukkit.getPlayer(TpaCommand.getRequests().get(p.getUniqueId()));
                p.sendMessage(Utils.chat(plugin.getConfig().getString("Teleport-Deny-Message")));
                requester.sendMessage(Utils.chat(prefix + plugin.getConfig().getString("Teleport-Deny-Message")));
                TpaCommand.getRequests().remove(p.getUniqueId());
            } else p.sendMessage(Utils.chat(prefix + plugin.getConfig().getString("No-Pending-Requests")));
        }

        return true;
    }
}

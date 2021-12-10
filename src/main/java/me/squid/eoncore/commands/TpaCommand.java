package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class TpaCommand implements CommandExecutor {

    EonCore plugin;
    final String prefix = "&7[&5&lEon Survival&7]&r ";

    private static HashMap<UUID, UUID> requests = new HashMap<>();

    public TpaCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("tpa")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    if (requests.get(target.getUniqueId()) != p.getUniqueId()) {
                        requests.put(target.getUniqueId(), p.getUniqueId());
                        p.sendMessage(Utils.chat(prefix + plugin.getConfig().getString("Tpa-Player-Message")));
                        target.sendMessage(Utils.chat(prefix + Objects.requireNonNull(plugin.getConfig().getString("Tpa-Target-Message"))
                        .replace("<player>", p.getDisplayName())));
                    } else p.sendMessage(Utils.chat(prefix + plugin.getConfig().getString("Pending-Request")));
                } else p.sendMessage(Utils.chat(prefix + plugin.getConfig().getString("Target-Null")));
            } else p.sendMessage(Utils.chat(prefix + "&7Usage: /tpa <player>"));
        } else System.out.println("Bruh you can't do this");


        return true;
    }

    public static HashMap<UUID, UUID> getRequests() {
        return requests;
    }
}

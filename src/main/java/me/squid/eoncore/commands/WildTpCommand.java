package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class WildTpCommand implements CommandExecutor {

    EonCore plugin;
    private static ArrayList<UUID> list = new ArrayList<>();

    public WildTpCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("wild")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (list.contains(p.getUniqueId())) {
                return true;
            } else {
                list.add(p.getUniqueId());
            }
        }
        return true;
    }

    public static boolean isInList(Player p) {
        return list.contains(p.getUniqueId());
    }

    public static void removeFromList(Player p) {
        list.remove(p.getUniqueId());
    }
}

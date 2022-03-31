package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TpposCommand implements CommandExecutor {

    EonCore plugin;

    public TpposCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("tppos")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (p.hasPermission("eoncommands.tppos")) {
                if (args.length > 0) {
                    if (args.length == 3) {
                        double x = Double.parseDouble(args[0]);
                        double y = Double.parseDouble(args[1]);
                        double z = Double.parseDouble(args[2]);

                        p.teleport(new Location(p.getWorld(), x, y, z));
                        p.sendMessage(Utils.chat(Utils.getPrefix("nations") + "&bTeleport to x:" + x + " y:" + y + " z:" + z));
                    } else {
                        p.sendMessage(Utils.chat(Utils.getPrefix("nations") + "&7Usage: /tppos x y z"));
                        return true;
                    }
                } else {
                    p.sendMessage(Utils.chat(Utils.getPrefix("nations") + "&7Usage: /tppos x y z"));
                }
            }
        }

        return true;
    }
}

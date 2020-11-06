package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PTimeCommand implements CommandExecutor {

    EonCore plugin;

    public PTimeCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("ptime").setExecutor(this);
        plugin.getCommand("ptime").setTabCompleter(getTabComplete());
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 1) {
                for (String times : getTimes()) {
                    if (times.equalsIgnoreCase(args[0])) {
                        p.setPlayerTime(getTimeFromString(times), false);
                        p.sendMessage(Utils.chat(EonCore.prefix + "&7Time set to " + getTimeFromString(times)));
                        if (times.equalsIgnoreCase("reset")) {
                            p.resetPlayerTime();
                            p.sendMessage(Utils.chat(EonCore.prefix + "&7Time reset to the normal cycle"));
                        }
                    }
                }
            }
        }
        return true;
    }

    public TabCompleter getTabComplete() {
        return (sender, command, alias, args) -> getTimes();
    }

    public List<String> getTimes() {
        List<String> list = new ArrayList<>();
        list.add("day");
        list.add("night");
        list.add("reset");
        return list;
    }

    public int getTimeFromString(String s) {
        if (s.equalsIgnoreCase("day")) return 1000;
        if (s.equalsIgnoreCase("night")) return 13000;
        return 0;
    }
}

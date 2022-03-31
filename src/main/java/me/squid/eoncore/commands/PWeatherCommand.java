package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.WeatherType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PWeatherCommand implements CommandExecutor {

    EonCore plugin;

    public PWeatherCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("pweather").setExecutor(this);
        plugin.getCommand("pweather").setTabCompleter(getTabComplete());
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reset")) {
                    p.resetPlayerWeather();
                    return true;
                }
                try {
                    WeatherType weather = WeatherType.valueOf(args[0].toUpperCase());
                    p.setPlayerWeather(weather);
                    p.sendMessage(Utils.chat(Utils.getPrefix("nations") + "&7Set weather to " + weather.name().toLowerCase()));
                } catch (NullPointerException e) {
                    p.sendMessage(Utils.chat(Utils.getPrefix("nations") + "&7Invalid weather type"));
                    return true;
                }
            } else p.sendMessage(Utils.chat(Utils.getPrefix("nations") + "&7Usage: /pweather <clear/downfall/reset>"));
        }

        return true;
    }

    public TabCompleter getTabComplete() {
        return (sender, command, alias, args) -> {
            List<String> list = new ArrayList<>();
            list.add("clear");
            list.add("downfall");
            list.add("reset");
            return list;
        };
    }
}

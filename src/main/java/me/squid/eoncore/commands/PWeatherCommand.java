package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Messaging;
import org.bukkit.WeatherType;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

@RegisterCommand
public class PWeatherCommand extends EonCommand {

    public PWeatherCommand(EonCore plugin) {
        super("pweather", plugin);
        plugin.getCommand("pweather").setTabCompleter(tabComplete());
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 1) {
            switch (args[0]) {
                case "downfall" -> setPlayerWeather(player, WeatherType.DOWNFALL);
                case "clear" -> setPlayerWeather(player, WeatherType.CLEAR);
                case "reset" -> resetPlayerWeather(player);
                default -> sendUsageMessage(player);
            }
        } else sendUsageMessage(player);
    }

    private void sendUsageMessage(Player player) {
        Messaging.sendNationsMessage(player, "Usage: /pweather <clear/downfall/reset>");
    }

    private void resetPlayerWeather(Player player) {
        player.resetPlayerWeather();
        Messaging.sendNationsMessage(player, "Reset weather to server's weather");
    }

    private void setPlayerWeather(Player player, WeatherType weather) {
        player.setPlayerWeather(weather);
        Messaging.sendNationsMessage(player, "Set weather to " + weather.name().toLowerCase());
    }

    public TabCompleter tabComplete() {
        return (sender, command, alias, args) -> List.of("clear", "downfall", "reset");
    }
}

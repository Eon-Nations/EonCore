package me.squid.eoncore.misc.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.messaging.Messenger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
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
        Component usageMessage = Component.text("Usage: /pweather <clear/downfall/reset>")
                .color(TextColor.color(96, 96, 96));
        Messenger messenger = Messaging.messenger(EonPrefix.NATIONS);
        messenger.send(player, usageMessage);
    }

    private void resetPlayerWeather(Player player) {
        player.resetPlayerWeather();
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.NATIONS);
        messenger.sendMessage(player, "PWeather-Reset");
    }

    private void setPlayerWeather(Player player, WeatherType weather) {
        player.setPlayerWeather(weather);
        String rawWeatherString = core.getConfig().getString("PWeather-Set")
                        .replace("<weather>", weather.name().toLowerCase());
        Component message = Messaging.fromFormatString(rawWeatherString);
        Messenger messenger = Messaging.messenger(EonPrefix.NATIONS);
        messenger.send(player, message);
    }

    public TabCompleter tabComplete() {
        return (sender, command, alias, args) -> List.of("clear", "downfall", "reset");
    }
}

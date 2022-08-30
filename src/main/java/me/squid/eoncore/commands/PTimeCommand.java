package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.Messaging;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

@RegisterCommand
public class PTimeCommand extends EonCommand {

    public PTimeCommand(EonCore plugin) {
        super("ptime", plugin);
        plugin.getCommand("ptime").setTabCompleter(getTabComplete());
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 1 && isValidTime(args[0])) {
            int time = getTimeFromString(args[0]);
            if (time == 0) {
                player.resetPlayerTime();
                Messaging.sendNationsMessage(player, "Time reset to server cycle");
            } else {
                player.setPlayerTime(time, false);
                Messaging.sendNationsMessage(player, "Time set to " + args[0]);
            }
        } else {
            Messaging.sendNationsMessage(player, "Usage: /ptime <day/night/reset>");
        }
    }

    private boolean isValidTime(String argument) {
        return times().contains(argument);
    }

    private TabCompleter getTabComplete() {
        return (sender, command, alias, args) -> times();
    }

    private static List<String> times() {
        return List.of("day", "night", "reset");
    }

    public int getTimeFromString(String s) {
        if (s.equalsIgnoreCase("day")) return 1000;
        if (s.equalsIgnoreCase("night")) return 13000;
        return 0;
    }
}

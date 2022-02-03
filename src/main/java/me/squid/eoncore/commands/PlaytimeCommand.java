package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlaytimeCommand implements CommandExecutor {

    EonCore plugin;
    HashMap<UUID, Long> playMap;

    public PlaytimeCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("playtime").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player p) {
            long timePlayedOnServer = loadPlayer(p.getUniqueId());
            long timePlayed = System.currentTimeMillis() - playMap.get(p.getUniqueId());
            p.sendMessage(String.valueOf(timePlayed + timePlayedOnServer));
        }
        return true;
    }

    private long loadPlayer(UUID uuid) {
        return 0;
    }

    private void savePlayer(UUID uuid) {

    }
}

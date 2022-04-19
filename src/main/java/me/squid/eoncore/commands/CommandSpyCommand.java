package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class CommandSpyCommand implements CommandExecutor, Listener {
    EonCore plugin;
    private final Set<Player> peopleSpying = new HashSet<>();

    public CommandSpyCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("commandspy").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (!peopleSpying.contains(p)) {
                peopleSpying.add(p);
                p.sendMessage(Utils.chat(plugin.getConfig().getString("CommandSpy-On")));
            } else {
                peopleSpying.remove(p);
                p.sendMessage(Utils.chat(plugin.getConfig().getString("CommandSpy-Off")));
            }
        }
        return true;
    }

    @EventHandler
    public void onCommandSend(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String cmd = e.getMessage();
        String commandMessage = Utils.chat(plugin.getConfig().getString("CommandSpy-Message")
                .replace("<player>", p.getName()).replace("<command>", cmd));
        Consumer<Player> sendCommandMessage = player -> player.sendMessage(commandMessage);

        if (!p.hasPermission(getImmunePerm())) {
            peopleSpying.forEach(sendCommandMessage);
        }
    }

    public String getImmunePerm(){
        return "eoncommands.commandspy.immune";
    }
}

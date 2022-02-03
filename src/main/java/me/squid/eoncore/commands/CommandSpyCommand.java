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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CommandSpyCommand implements CommandExecutor, Listener {
    EonCore plugin;

    private final List<Player> peopleSpying = new ArrayList<>();

    public CommandSpyCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("commandspy").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (!peopleSpying.contains(p)){
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
    public void onCommandSend(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        String cmd = e.getMessage();

        if (!p.hasPermission(getImmunePerm())) {
            Stream<? extends Player> playerStream = Bukkit.getOnlinePlayers().stream();
            playerStream.filter(peopleSpying::contains)
                        .forEach(player -> player.sendMessage(Utils.chat(plugin.getConfig().getString("CommandSpy-Message")
                            .replace("<player>", p.getName()).replace("<command>", cmd))));
        }
    }

    public String getPermissionNode(){
        return "eoncommands.commandspy";
    }

    public String getImmunePerm(){
        return "eoncommands.commandspy.immune";
    }
}

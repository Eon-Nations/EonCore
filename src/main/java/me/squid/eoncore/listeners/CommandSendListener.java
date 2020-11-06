package me.squid.eoncore.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.commands.CommandSpyCommand;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Objects;

public class CommandSendListener implements Listener {

    EonCore plugin;

    public CommandSendListener(EonCore plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCommandSend(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        String cmd = e.getMessage();

        if (!p.hasPermission(getImmunePerm())) {
            for (Player online : Bukkit.getOnlinePlayers()){
                if (p != online && online.hasPermission(getPermissionNode()) && CommandSpyCommand.getSpying().contains(online)){
                    online.sendMessage(Utils.chat(Objects.requireNonNull(plugin.getConfig().getString("CommandSpy-Message"))
                    .replace("<player>", p.getName()).replace("<command>", cmd)));
                }
            }
        }
    }

    public String getPermissionNode(){
        return "eoncommands.commandspy";
    }
    public String getImmunePerm(){
        return "eoncommands.commandspy.immune";
    }
}

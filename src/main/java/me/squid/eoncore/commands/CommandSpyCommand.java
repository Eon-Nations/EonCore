package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Objects;

public class CommandSpyCommand implements CommandExecutor {

    EonCore plugin;

    private static ArrayList<Player> online = new ArrayList<>();

    public CommandSpyCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("commandspy")).setExecutor(this);
    }

    public static ArrayList<Player> getSpying() {
        return online;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (p.hasPermission(getPermissionNode())){
                if (!online.contains(p)){
                    online.add(p);
                    p.sendMessage(Utils.chat(plugin.getConfig().getString("CommandSpy-On")));
                } else {
                    online.remove(p);
                    p.sendMessage(Utils.chat(plugin.getConfig().getString("CommandSpy-Off")));
                }
            } else {
                p.sendMessage(Utils.chat(plugin.getConfig().getString("No-Perms")));
            }
        }

        return true;
    }

    public String getPermissionNode(){
        return "eoncommands.commandspy";
    }
}

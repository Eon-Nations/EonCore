package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Objects;

public class VanishCommand implements CommandExecutor {

    EonCore plugin;

    private ArrayList<Player> list = new ArrayList<>();

    public VanishCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("vanish")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (p.hasPermission(getPermissionNode())){
                if (!list.contains(p)){
                    addPlayer(p);
                    p.sendMessage(Utils.chat(plugin.getConfig().getString("Vanish-Message")));
                } else if (list.contains(p)){
                    removePlayer(p);
                    p.sendMessage(Utils.chat(plugin.getConfig().getString("Unvanish-Message")));
                }
            } else {
                p.sendMessage(Utils.chat(plugin.getConfig().getString("No-Perms")));
            }
        }

        return true;
    }

    public String getPermissionNode(){
        return "eoncommands.vanish";
    }

    public void addPlayer(Player p){
        list.add(p);
        for (Player online : Bukkit.getOnlinePlayers()){
            online.hidePlayer(plugin, p);
        }
    }

    public void removePlayer(Player p){
        list.remove(p);
        for (Player online : Bukkit.getOnlinePlayers()){
            online.showPlayer(plugin, p);
        }
    }
}

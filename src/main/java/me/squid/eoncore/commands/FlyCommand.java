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

public class FlyCommand implements CommandExecutor {

    EonCore plugin;

    private ArrayList<Player> list = new ArrayList<>();

    public FlyCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("fly").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (args.length == 0){
                if (p.hasPermission(getPermissionNode())) {
                    if (!list.contains(p)){
                        if (p.getWorld().getName().equals("spawn_void")) return true;
                        p.setAllowFlight(true);
                        p.sendMessage(Utils.chat(plugin.getConfig().getString("Fly-On")));
                        list.add(p);
                    } else {
                        p.setAllowFlight(false);
                        p.sendMessage(Utils.chat(plugin.getConfig().getString("Fly-Off")));
                        list.remove(p);
                    }
                } else {
                    p.sendMessage(Utils.chat(plugin.getConfig().getString("No-Perms")));
                }
            } else if (args.length == 1){
                if (p.hasPermission(getOthersPermNode())) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null && !target.hasPermission(getImmuneNode())){
                        if (!list.contains(target)){
                            list.add(target);
                            target.sendMessage(Utils.chat(plugin.getConfig().getString("Fly-On")));
                            target.setAllowFlight(true);
                            p.sendMessage(Utils.chat(Objects.requireNonNull(plugin.getConfig().getString("Target-Fly-On"))
                            .replace("<target>", target.getName())));
                        } else {
                            list.remove(target);
                            target.sendMessage(Utils.chat(plugin.getConfig().getString("Fly-Off")));
                            target.setAllowFlight(false);
                            p.sendMessage(Utils.chat(Objects.requireNonNull(plugin.getConfig().getString("Target-Fly-Off"))
                            .replace("<target>", target.getName())));
                        }
                    } else {
                        p.sendMessage(Utils.chat(plugin.getConfig().getString("Target-Null")));
                    }
                } else {
                    p.sendMessage(Utils.chat(plugin.getConfig().getString("No-Perms")));
                }
            }
        }
        return true;
    }

    public String getPermissionNode(){
        return "eoncommands.fly";
    }

    public String getOthersPermNode(){
        return "eoncommands.fly.others";
    }

    public String getImmuneNode(){
        return "eoncommands.fly.others.immune";
    }
}

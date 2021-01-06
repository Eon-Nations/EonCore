package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class AnvilCommand implements CommandExecutor {

    EonCore plugin;

    public AnvilCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("anvil")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (p.hasPermission(getPermissionNode())) p.sendMessage(Utils.chat(EonCore.prefix + "&bFor the time being, /anvil is disabled. Expect this to change very soon"));
            else p.sendMessage(Utils.chat(plugin.getConfig().getString("No-Perms")));
        }

        return true;
    }

    public String getPermissionNode(){
        return "eoncommands.anvil";
    }

}

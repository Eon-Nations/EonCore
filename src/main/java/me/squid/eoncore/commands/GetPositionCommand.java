package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetPositionCommand implements CommandExecutor {

    EonCore plugin;

    public GetPositionCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("getpos").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            int x = p.getLocation().getBlockX();
            int y = p.getLocation().getBlockY();
            int z = p.getLocation().getBlockZ();
            float yaw = p.getLocation().getYaw();
            float pitch = p.getLocation().getPitch();
            p.sendMessage(Utils.chat(EonCore.prefix + "&bCurrent position is x:" + x + " y:" + y + " z:" + z + " yaw: " + yaw + " pitch:" + pitch));
        }

        return true;
    }
}

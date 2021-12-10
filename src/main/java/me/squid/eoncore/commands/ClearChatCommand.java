package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class ClearChatCommand implements CommandExecutor {

    EonCore plugin;

    public ClearChatCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("clearchat")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        for (Player online : Bukkit.getOnlinePlayers()) {
            if (!online.hasPermission("eoncommands.clearchat")) {
                for (int i = 0; i < 100; i++) { online.sendMessage(""); }
            } else {
                sender.sendMessage(Utils.chat(EonCore.prefix + "&bYou are immune to chat clear"));
            }
            sendClearMessage(online, sender.getName());
        }
        return true;
    }

    private void sendClearMessage(Player p, String senderName) {
        p.sendMessage(Utils.chat("[--------------------------]"));
        p.sendMessage(Utils.chat("Chat has been cleared by " + senderName));
        p.sendMessage(Utils.chat("[--------------------------]"));
    }
}

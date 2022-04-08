package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ClearChatCommand implements CommandExecutor {

    EonCore plugin;

    public ClearChatCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("clearchat").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String immuneMessage = Utils.chat(Utils.getPrefix("nations") + "&bYou are immune to chat clear");
        Stream<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers().stream();
        Consumer<Player> sendEmptyMessage = player -> IntStream.range(0, 100).forEach(index -> player.sendMessage(""))  ;
        Consumer<Player> sendImmuneMessage = player -> player.sendMessage(immuneMessage);
        Predicate<Player> normalPlayer = player -> !player.hasPermission("eoncommands.clearchat");

        onlinePlayers.filter(normalPlayer).forEach(sendEmptyMessage);
        Bukkit.getOnlinePlayers().stream().filter(normalPlayer.negate()).forEach(sendImmuneMessage);
        Bukkit.getOnlinePlayers().forEach(player -> sendClearMessage(player, sender.getName()));
        return true;
    }

    private void sendClearMessage(Player p, String senderName) {
        p.sendMessage(Utils.chat("[--------------------------]"));
        p.sendMessage(Utils.chat("Chat has been cleared by " + senderName));
        p.sendMessage(Utils.chat("[--------------------------]"));
    }
}

package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.function.Predicate;
import java.util.stream.IntStream;

@RegisterCommand
public class ClearChatCommand extends EonCommand {

    public ClearChatCommand(EonCore plugin) {
        super("clearchat", plugin);
    }

    public void execute(Player player, String[] args) {
        Predicate<Player> normalPlayer = online -> !online.hasPermission("eoncommands.clearchat");
        Bukkit.getOnlinePlayers().stream()
                .filter(normalPlayer)
                .forEach(ClearChatCommand::sendEmptyMessage);
        Bukkit.getOnlinePlayers().stream()
                .filter(normalPlayer.negate())
                .forEach(this::sendImmuneMessage);
        Bukkit.getOnlinePlayers().forEach(online -> sendClearMessage(online, player.getName()));
    }

    private void sendImmuneMessage(Player player) {
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.MODERATION);
        messenger.sendMessage(player, "Clear-Chat-Immune");
    }

    private static void sendEmptyMessage(Player player) {
        IntStream stream = IntStream.range(0, 100);
        stream.forEach(index -> player.sendMessage(""));
    }

    private static void sendClearMessage(Player p, String senderName) {
        p.sendMessage(Utils.chat("[--------------------------]"));
        p.sendMessage(Utils.chat("Chat has been cleared by " + senderName));
        p.sendMessage(Utils.chat("[--------------------------]"));
    }
}

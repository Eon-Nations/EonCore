package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.Optional;

import static me.squid.eoncore.listeners.ChatFormatListener.isChatLocked;
import static me.squid.eoncore.listeners.ChatFormatListener.toggleChatLock;

@RegisterCommand
public class ClockCommand extends EonCommand {
    static final String DEFAULT_MESSAGE = "Chat is locked. Please wait while we resolve the conflict. Thank you for your patience!";

    public ClockCommand(EonCore plugin) {
        super("clock", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        toggleChatLock();
        String path = path(isChatLocked());
        broadcastMessage(Bukkit.getServer(), path);
    }

    private void broadcastMessage(Server server, String path) {
        Optional<String> message = Optional.ofNullable(core.getConfig().getString(path));
        String clearMessage = message.orElse(DEFAULT_MESSAGE);
        MiniMessage miniMessage = MiniMessage.miniMessage();
        Component finalMessage = miniMessage.deserialize(clearMessage);
        server.broadcast(finalMessage);
    }

    private String path(boolean state) {
        return state ? "Clock-Lock" : "Clock-Unlock";
    }
}

package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.listeners.ChatFormatListener;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ClockCommand implements CommandExecutor {

    EonCore plugin;

    public ClockCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("clock").setExecutor(this);
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!ChatFormatListener.isChatLocked()) {
            ChatFormatListener.setChatLocked(true);
            Bukkit.getServer().sendMessage(Utils.getPrefix("moderation")
                    .append(Component.text("Chat is locked. Please wait while we resolve the conflict. Thank you for your patience")
                            .color(TextColor.color(255, 0, 0))));

            if (commandSender.equals(Bukkit.getConsoleSender())) {
                commandSender.sendMessage("Chat lock successful");
            }
        } else ChatFormatListener.setChatLocked(false);

        return true;
    }
}

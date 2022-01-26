package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEventSource;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.net.URL;

public class DiscordCommand implements CommandExecutor {

    EonCore plugin;

    public DiscordCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("discord").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        sender.sendMessage(Utils.getPrefix("nations")
                .append(Component.text(plugin.getConfig().getString("Discord-Message"))
                        .clickEvent(ClickEvent.copyToClipboard(plugin.getConfig().getString("Discord-URL")))));
        return true;
    }
}

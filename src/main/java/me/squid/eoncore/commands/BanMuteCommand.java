package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.managers.Cooldown;
import me.squid.eoncore.sql.AdminSQLManager;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class BanMuteCommand implements CommandExecutor {

    EonCore plugin;
    AdminSQLManager adminSQLManager;

    public BanMuteCommand(EonCore plugin, AdminSQLManager adminSQLManager) {
        this.plugin = plugin;
        this.adminSQLManager = adminSQLManager;
        plugin.getCommand("ban").setExecutor(this);
        plugin.getCommand("mute").setExecutor(this);
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            // /ban <player> 30d reason reason reason
            if (args.length >= 3) {
                OfflinePlayer playerToBan = Bukkit.getOfflinePlayerIfCached(args[0]);
                if (playerToBan != null) {
                    String[] parseLength = args[1].split("\\D");
                    String[] parseMultiplier = args[1].split("\\d");
                    long amount = Long.parseLong(parseLength[0]);
                    String type = parseMultiplier[parseMultiplier.length - 1];
                    plugin.getLogger().info("Amount: " + amount);
                    plugin.getLogger().info("Type: " + type);

                    // Resetting the first two arguments to allow for the getMessage() method to grab everything else
                    args[0] = ""; args[1] = "";
                    String reason = Utils.getMessage(args);

                    switch (type) {
                        case "h" -> amount *= 60 * 1000 * 60;
                        case "d" -> amount *= 60 * 1000 * 60 * 24;
                        default -> {
                            p.sendMessage(Utils.getPrefix("moderation")
                                    .append(Component.text("Got: " + type + " as an input. Choose between 'h' or 'd'. Exiting.")));
                            return true;
                        }
                    }

                    plugin.getLogger().info("Amount After Multiplication: " + amount);
                    boolean isMute = command.getName().equals("mute");
                    if (isMute) adminSQLManager.addCooldownToMap(new Cooldown(playerToBan.getUniqueId(), amount, System.currentTimeMillis()));
                    else playerToBan.banPlayer(reason, new Date(amount + System.currentTimeMillis()), p.getName(), true);

                    if (isMute && playerToBan.getPlayer() != null)
                        playerToBan.getPlayer().sendMessage(Utils.getPrefix("moderation")
                                .append(Utils.chat("&aYou have been muted for " + reason + " by " + p.getName() + " for " + Utils.getFormattedTimeString(amount))));
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        if (online.hasPermission("eoncommands.staffchat"))
                            online.sendMessage(Utils.getPrefix("moderation")
                                    .append(Utils.chat("&a" + playerToBan.getName() + " has been given action: "
                                            + StringUtils.capitalize(command.getName()) + " for " + reason + " by "
                                            + p.getName() + " for " + Utils.getFormattedTimeString(amount))));
                    }
                } else p.sendMessage(Utils.getPrefix("moderation").append(Component.text("Player is invalid")));
            } else p.sendMessage(Utils.getPrefix("moderation").append(Component.text("Usage: /" + command.getName() + " <player> <time> <reason>")));
        } else sender.sendMessage(Component.text("You have to be an online player to do that..."));
        return true;
    }
}

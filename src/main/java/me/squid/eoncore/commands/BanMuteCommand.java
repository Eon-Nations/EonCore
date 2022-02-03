package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.managers.Cooldown;
import me.squid.eoncore.managers.MutedManager;
import me.squid.eoncore.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.stream.Stream;

public class BanMuteCommand implements CommandExecutor {

    EonCore plugin;
    MutedManager mutedManager;

    public BanMuteCommand(EonCore plugin, MutedManager mutedManager) {
        this.plugin = plugin;
        this.mutedManager = mutedManager;
        plugin.getCommand("ban").setExecutor(this);
        plugin.getCommand("mute").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 3) {
            OfflinePlayer playerToBan = Bukkit.getOfflinePlayer(args[0]);
            long punishLength = getBanLengthFromArgs(args);
            String reason = getReason(args);
            boolean isMute = command.getName().equals("mute");

            if (isMute) mutePlayer(playerToBan, reason, punishLength);
            else banPlayer(playerToBan, reason, punishLength);

            String actionMessage = Utils.getPrefix("moderation") + Utils.chat("&a" + playerToBan.getName() + " has been given action: "
                    + StringUtils.capitalize(command.getName()) + " for " + reason + " by "
                    + sender.getName() + " for " + Utils.getFormattedTimeString(punishLength));
            Stream<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers().stream();
            onlinePlayers.filter(online -> online.hasPermission("eoncommands.staffchat"))
                    .forEach(online -> online.sendMessage(actionMessage));
        } else sender.sendMessage(Utils.getPrefix("moderation") + "Usage: /" + command.getName() + " <player> <time> <reason>");
        return true;
    }

    private void banPlayer(OfflinePlayer player, String reason, long punishLength) {
        Date expiresDate = new Date(punishLength + System.currentTimeMillis());
        Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), reason, expiresDate, null);
        if (player.getPlayer() != null) {
            player.getPlayer().kickPlayer(Utils.getPrefix("moderation") +
                    Utils.chat("&aYou have been banned for " + reason + " for " +
                            Utils.getFormattedTimeString(punishLength)));
        }
    }

    private void mutePlayer(OfflinePlayer player, String reason, long punishLength) {
        mutedManager.addCooldown(new Cooldown(player.getUniqueId(), punishLength, System.currentTimeMillis()));
        if (player.getPlayer() != null) {
            player.getPlayer().sendMessage(Utils.getPrefix("moderation") +
                    Utils.chat("&aYou have been muted for " + reason + " for " + Utils.getFormattedTimeString(punishLength)));
        }
    }

    private String getReason(String[] args) {
        args[0] = ""; args[1] = "";
        return Utils.getMessageFromArgs(args);
    }

    private long getBanLengthFromArgs(String[] args) {
        String[] parseLength = args[1].split("\\D");
        String[] parseMultiplier = args[1].split("\\d");
        long amount = Long.parseLong(parseLength[0]);
        String type = parseMultiplier[parseMultiplier.length - 1];

        switch (type) {
            case "h" -> amount *= 60 * 1000 * 60;
            case "d" -> amount *= 60 * 1000 * 60 * 24;
            default -> { return 0; }
        }
        return amount;
    }
}

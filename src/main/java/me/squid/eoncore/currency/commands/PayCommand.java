package me.squid.eoncore.currency.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;

public class PayCommand {
    private PayCommand() { }

    public enum PayStatus {
        SUCCESS,
        INSUFFICIENT,
        INVALID
    }

    public static void registerPayCommand(EonCore plugin) {
        String usageMessage = Optional.ofNullable(plugin.getConfig().getString("usage-pay"))
                .orElse("<player> <amount>");
    }

    private static boolean isValidDouble(String arg) {
        try {
            Double.parseDouble(arg);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void handler(Player player, EonCore plugin) {
        Player target = player;
        double amount = 0.0;
        PayStatus status = payPlayer(plugin, player, target, amount);
        if (status.equals(PayStatus.INSUFFICIENT) || status.equals(PayStatus.INVALID)) {
            ConfigMessenger messenger = Messaging.setupConfigMessenger(plugin.getConfig(), EonPrefix.CURRENCY);
            messenger.sendMessage(player, "insufficient-funds");
        } else {
            Economy eco = plugin.getService(Economy.class);
            String formattedAmount = eco.format(amount);
            sendMessage(plugin, player, formattedAmount, "receiver-pay");
            sendMessage(plugin, target, formattedAmount, "sender-pay");
        }
    }

    private static void sendMessage(EonCore plugin, Player player, String formattedAmount, String path) {
        ConfigMessenger messenger = Messaging.setupConfigMessenger(plugin.getConfig(), EonPrefix.CURRENCY,
                Map.of("<player>", player.getName(), "<amount>", formattedAmount));
        messenger.sendMessage(player, path);
    }

    public static PayStatus payPlayer(EonCore plugin, Player sender, Player target, double amount) {
        Economy eco = plugin.getService(Economy.class);
        if (amount <= 0) return PayStatus.INVALID;
        EconomyResponse withdrawResponse = eco.withdrawPlayer(sender, amount);
        if (!withdrawResponse.transactionSuccess()) {
            return PayStatus.INSUFFICIENT;
        }
        eco.depositPlayer(target, amount);
        return PayStatus.SUCCESS;
    }
}

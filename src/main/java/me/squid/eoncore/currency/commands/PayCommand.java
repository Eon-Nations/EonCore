package me.squid.eoncore.currency.commands;

import me.lucko.helper.Commands;
import me.lucko.helper.command.Command;
import me.lucko.helper.command.CommandInterruptException;
import me.lucko.helper.command.argument.ArgumentParser;
import me.lucko.helper.command.context.CommandContext;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.utils.FunctionalBukkit;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
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

    public static Command registerPayCommand(EonCore plugin) {
        String usageMessage = Optional.ofNullable(plugin.getConfig().getString("usage-pay"))
                .orElse("<player> <amount>");
        return Commands.create().assertPlayer()
                .assertUsage(usageMessage)
                .assertArgument(0, arg -> Bukkit.getPlayer(arg) != null)
                .assertArgument(1, PayCommand::isValidDouble)
                .handler(context -> handler(context, plugin));
    }

    private static boolean isValidDouble(String arg) {
        try {
            Double.parseDouble(arg);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void handler(CommandContext<Player> context, EonCore plugin) throws CommandInterruptException {
        Player target = context.arg(0).parseOrFail(ArgumentParser.of(FunctionalBukkit::getPlayerFromName));
        double amount = context.arg(1).parseOrFail(ArgumentParser.of(arg -> Optional.of(Double.parseDouble(arg))));
        PayStatus status = payPlayer(plugin, context.sender(), target, amount);
        if (status.equals(PayStatus.INSUFFICIENT) || status.equals(PayStatus.INVALID)) {
            ConfigMessenger messenger = Messaging.setupConfigMessenger(plugin.getConfig(), EonPrefix.CURRENCY);
            messenger.sendMessage(context.sender(), "insufficient-funds");
        } else {
            Economy eco = plugin.getService(Economy.class);
            String formattedAmount = eco.format(amount);
            sendMessage(plugin, context.sender(), formattedAmount, "receiver-pay");
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

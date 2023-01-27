package me.squid.eoncore.currency.commands;

import me.squid.eoncore.EonCore;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

public class BalanceCommand {

    private BalanceCommand() { }

    public static void registerBalance(EonCore plugin) {

    }

    private static void handleBalance(Player player, EonCore plugin) {
        Economy eco = plugin.getService(Economy.class);
    }
}

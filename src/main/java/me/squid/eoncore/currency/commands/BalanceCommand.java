package me.squid.eoncore.currency.commands;

import me.lucko.helper.Commands;
import me.lucko.helper.command.context.CommandContext;
import me.squid.eoncore.currency.Eoncurrency;
import me.squid.eoncore.currency.menus.BalanceMenu;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

public class BalanceCommand {

    private BalanceCommand() { }

    public static void registerBalance(Eoncurrency plugin) {
        Commands.create().assertPlayer()
                .handler(context -> handleBalance(context, plugin))
                .registerAndBind(plugin, "balance");
    }

    private static void handleBalance(CommandContext<Player> context, Eoncurrency plugin) {
        Economy eco = plugin.getService(Economy.class);
        BalanceMenu menu = new BalanceMenu(context.sender(), eco);
        menu.open();
    }
}

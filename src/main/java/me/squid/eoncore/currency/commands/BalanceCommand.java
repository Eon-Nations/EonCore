package me.squid.eoncore.currency.commands;

import me.lucko.helper.Commands;
import me.lucko.helper.command.Command;
import me.lucko.helper.command.context.CommandContext;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.currency.menus.BalanceMenu;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

public class BalanceCommand {

    private BalanceCommand() { }

    public static Command registerBalance(EonCore plugin) {
        return Commands.create().assertPlayer()
                .handler(context -> handleBalance(context, plugin));
    }

    private static void handleBalance(CommandContext<Player> context, EonCore plugin) {
        Economy eco = plugin.getService(Economy.class);
        BalanceMenu menu = new BalanceMenu(context.sender(), eco);
        menu.open();
    }
}

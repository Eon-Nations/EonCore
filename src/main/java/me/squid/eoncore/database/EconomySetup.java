package me.squid.eoncore.database;

import me.lucko.helper.Commands;
import me.lucko.helper.command.Command;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.currency.commands.BalanceCommand;
import me.squid.eoncore.currency.commands.PayCommand;
import me.squid.eoncore.currency.managers.EconManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Optional;

public class EconomySetup {

    public static JedisPool setupPool(EonCore plugin) {
        String serverURL = Optional.ofNullable(plugin.getConfig().getString("redis-url")).orElse("redis://localhost:6379");
        return new JedisPool(serverURL);
    }

    public static void hookToVault(EonCore plugin, RedisClient client) {
        EconManager econ = new EconManager(plugin, client);
        plugin.provideService(Economy.class, econ);
        Bukkit.getConsoleSender().sendMessage("Vault has successfully hooked to Economy");
        List<Command> commands = registerCommands(plugin);
        commands.forEach(plugin::bind);
    }

    public static List<Command> registerCommands(EonCore plugin) {
        Command pay = PayCommand.registerPayCommand(plugin);
        Command balance = BalanceCommand.registerBalance(plugin);
        Command shop = Commands.create().assertPlayer()
                .handler(context -> context.sender().sendMessage("Shop is coming soon!"));
        return List.of(pay, balance, shop);
    }
}

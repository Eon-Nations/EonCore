package me.squid.eoncore.currency.managers;

import me.lucko.helper.Events;
import me.lucko.helper.event.filter.EventFilters;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.database.DatabasePlayer;
import me.squid.eoncore.database.RedisClient;
import me.squid.eoncore.database.SetFunction;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class EconManager implements Economy {

    RedisClient client;
    SetFunction setFunc;
    private static final int DEC_PLACES = 2;
    public static final String BALANCE = ":balance";
    private static final EconomyResponse NOT_SUPPORTED = new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not supported");

    public EconManager(EonCore plugin, RedisClient client) {
        this.client = client;
        this.setFunc = SetFunction.makeCommonSet(client, BALANCE);
        var event = Events.subscribe(PlayerJoinEvent.class)
                .filter(e -> !e.getPlayer().hasPlayedBefore())
                .handler(e -> createPlayerAccount(e.getPlayer()));
        event.bindWith(plugin);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "Eon Currency";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return DEC_PLACES;
    }

    @Override
    public String format(double amount) {
        BigDecimal bd = new BigDecimal(String.valueOf(amount), MathContext.DECIMAL32);
        bd = bd.setScale(DEC_PLACES, RoundingMode.HALF_EVEN);
        return bd.toString();
    }

    @Override
    public String currencyNamePlural() {
        return "dollars";
    }

    @Override
    public String currencyNameSingular() {
        return "dollar";
    }

    @Override
    public boolean hasAccount(String playerName) {
        OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(playerName);
        if (player != null) {
            return hasAccount(player);
        } else return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer p) {
        return p.hasPlayedBefore();
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(playerName);
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return hasAccount(player);
    }

    @Override
    public double getBalance(String name) {
        OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(name);
        return player != null ? getBalance(player) : 0.0;
    }

    @Override
    public double getBalance(OfflinePlayer p) {
        try {
            return client.getKey(p.getUniqueId(), BALANCE, Double::parseDouble);
        } catch (NullPointerException e) {
            return 0.0;
        }
    }

    @Override
    public double getBalance(String playerName, String world) {
        return getBalance(playerName);
    }

    @Override
    public double getBalance(OfflinePlayer player, String world) {
        return getBalance(player);
    }

    @Override
    public boolean has(String name, double amount) {
        OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(name);
        return player != null && has(player, amount);
    }

    @Override
    public boolean has(OfflinePlayer p, double amount) {
        double balance = getBalance(p);
        return balance >= amount;
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return has(playerName, amount);
    }

    @Override
    public boolean has(OfflinePlayer p, String worldName, double amount) {
        return has(p, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(String name, double amount) {
        Player p = Bukkit.getPlayer(name);
        if (p != null) {
            return withdrawPlayer(p, amount);
        } else {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayerIfCached(name);
            if (offlinePlayer != null) {
                return withdrawPlayer(offlinePlayer, amount);
            } else return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player does not exist.");
        }
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer p, double amount) {
        double balance = getBalance(p);
        double newBalance = balance - amount;
        if (newBalance < 0) {
            return new EconomyResponse(amount, balance, ResponseType.FAILURE, "Insufficient funds");
        } else {
            setFunc.setPlayer(p.getUniqueId(), newBalance, String::valueOf);
            return new EconomyResponse(amount, newBalance, ResponseType.SUCCESS, "");
        }
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer p, String worldName, double amount) {
        return withdrawPlayer(p, amount);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        Player p = Bukkit.getPlayer(playerName);
        if (p != null) {
            return depositPlayer(p, amount);
        } else {
            OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(playerName);
            if (player != null) {
                return depositPlayer(player, amount);
            } else return new EconomyResponse(0, 0.0,
                    EconomyResponse.ResponseType.FAILURE, "Player does not exist.");
        }
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer p, double amount) {
        double balance = getBalance(p);
        double newBalance = amount + balance;
        setFunc.setPlayer(p.getUniqueId(), newBalance, String::valueOf);
        return new EconomyResponse(amount, balance + amount, ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer p, String worldName, double amount) {
        return depositPlayer(p, amount);
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return NOT_SUPPORTED;
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return NOT_SUPPORTED;
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return NOT_SUPPORTED;
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return NOT_SUPPORTED;
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return NOT_SUPPORTED;
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return NOT_SUPPORTED;
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return NOT_SUPPORTED;
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return NOT_SUPPORTED;
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return NOT_SUPPORTED;
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return NOT_SUPPORTED;
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return NOT_SUPPORTED;
    }

    @Override
    public List<String> getBanks() {
        return List.of();
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(playerName);
        if (player != null) {
            return createPlayerAccount(player);
        } else return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer p) {
        double balance = getBalance(p);
        if (balance == 0) {
            setFunc.setPlayer(p.getUniqueId(), 0.0, String::valueOf);
            return true;
        }
        return false;
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return createPlayerAccount(playerName);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return createPlayerAccount(player);
    }
}

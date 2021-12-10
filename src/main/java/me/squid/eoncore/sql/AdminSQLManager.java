package me.squid.eoncore.sql;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.managers.Cooldown;
import me.squid.eoncore.managers.CooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminSQLManager implements Listener {

    EonCore plugin;
    MySQL sql;
    CooldownManager cooldownManager = new CooldownManager();

    public AdminSQLManager(EonCore plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.sql = plugin.sql;
        if (sql.isConnected())
            createTable();
    }

    public void createTable() {
        executeStatement("CREATE TABLE IF NOT EXISTS muted (" +
                "  UUID varchar(50)," +
                "  Name varchar(50)," +
                "  Length long," +
                "  Time long," +
                "  PRIMARY KEY(UUID))", false, null);
    }

    public void addPlayerCooldownToSQL(UUID uuid) {
        if (!playerExists(uuid)) {
            Cooldown cooldown = cooldownManager.getCooldown(uuid);
            List<Object> parameters = new ArrayList<>(3);
            parameters.add(uuid.toString());
            parameters.add(Bukkit.getOfflinePlayer(uuid).getName());
            parameters.add(cooldown.getLength());
            parameters.add(cooldown.getTime());
            executeStatement("INSERT IGNORE INTO muted (UUID, NAME, LENGTH, TIME) VALUES (?, ?, ?)",
                    false, parameters);
        }
    }

    public Cooldown getPlayerFromSQL(UUID uuid) {
        if (playerExists(uuid)) {
            List<Object> parameters = new ArrayList<>(2);
            parameters.add(uuid);
            ResultSet rs = executeStatement("SELECT * FROM muted WHERE UUID=?",
                    true, parameters);
            try {
                if (rs != null && rs.next()) {
                    long time = rs.getLong("Time");
                    long length = rs.getLong("Length");
                    return new Cooldown(uuid, length, time);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void addCooldownToMap(Cooldown cooldown) {
        cooldownManager.add(cooldown);
    }

    public void removePlayerFromMap(UUID uuid) {
        cooldownManager.remove(uuid);
        if (playerExists(uuid))
            deletePlayerFromSQL(uuid);
    }

    public Cooldown getCooldown(UUID uuid) {
        return cooldownManager.getCooldown(uuid);
    }

    public boolean hasCooldown(UUID uuid) {
        return cooldownManager.hasCooldown(uuid);
    }

    public void deletePlayerFromSQL(UUID uuid) {
        if (playerExists(uuid))
            executeStatement("DELETE FROM muted WHERE UUID=?", false, List.of(uuid.toString()));
    }

    public List<UUID> getAllUUIDs() {
        return cooldownManager.getUUIDsFromCooldownMap();
    }

    public boolean playerExists(UUID uuid) {
        List<String> params = new ArrayList<>();
        params.add(uuid.toString());
        ResultSet rs = executeStatement("SELECT * FROM muted WHERE UUID=?", true, params);
        try {
            return rs != null && rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private ResultSet executeStatement(String sqlStatement, boolean wantResults, @Nullable List<?> parameters) {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement(sqlStatement);
            if (parameters != null) {
                for (int i = 0; i < parameters.size(); i++) {
                    Object object = parameters.get(i);
                    if (object instanceof Long) ps.setLong(i + 1, (long) object);
                    if (object instanceof String) ps.setString(i + 1, (String) object);
                    else ps.setString(i + 1, object.toString());
                }
            }
            if (wantResults) return ps.executeQuery();
            else ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (playerExists(p.getUniqueId())) {
            Cooldown cooldown = getPlayerFromSQL(p.getUniqueId());
            cooldownManager.add(cooldown);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        Cooldown cooldown = getCooldown(uuid);
        if (cooldown != null && hasCooldown(uuid)) {
            addPlayerCooldownToSQL(uuid);
        } else if (cooldown != null && cooldown.isExpired()) {
            removePlayerFromMap(uuid);
        }
    }
}

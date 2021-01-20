package me.squid.eoncore.sql;

import me.squid.eoncore.EonCore;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLManager {

    EonCore plugin;

    public SQLManager(EonCore plugin) {
        this.plugin = plugin;
    }

    public void createTable() {
        PreparedStatement ps;
        try {
            ps = plugin.sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS votes " + "(NAME VARCHAR(100),UUID VARCHAR(100),VOTES INT(100),PRIMARY KEY (NAME))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createPlayer(Player p) {
        try {
            if (!playerExists(p.getUniqueId())) {
                PreparedStatement ps2 = plugin.sql.getConnection().prepareStatement("INSERT IGNORE INTO votes" + " (NAME, UUID) VALUES (?, ?)");
                ps2.setString(1, p.getName());
                ps2.setString(2, p.getUniqueId().toString());
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean playerExists(UUID uuid) {
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT * FROM votes WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();
            return results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addVotes(UUID uuid, int votes) {
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE votes SET VOTES=? WHERE UUID=?");
            ps.setInt(1, getVotes(uuid) + votes);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getVotes(UUID uuid) {
        try {
            if (playerExists(uuid)) {
                PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT VOTES FROM votes WHERE UUID=?");
                ps.setString(1, uuid.toString());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) return rs.getInt("VOTES");
            } else addVotes(uuid, 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

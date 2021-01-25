package me.squid.eoncore.sql;

import me.squid.eoncore.EonCore;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class VotesManager {

    EonCore plugin;

    public VotesManager(EonCore plugin) {
        this.plugin = plugin;
    }

    public void createTable() {
        PreparedStatement ps;
        try {
            ps = plugin.sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS votes " + "(NAME VARCHAR(100),UUID VARCHAR(100),TOTALVOTES INT(100),MONTHLYVOTES INT(100),PRIMARY KEY (UUID))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createPlayer(Player p) {
        try {
            if (!playerExists(p.getUniqueId())) {
                PreparedStatement ps = plugin.sql.getConnection().prepareStatement("INSERT IGNORE INTO votes" + " (NAME, UUID) VALUES (?, ?)");
                ps.setString(1, p.getName());
                ps.setString(2, p.getUniqueId().toString());
                ps.executeUpdate();
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

    public void addMonthlyVotes(UUID uuid, int votes) {
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE votes SET MONTHLYVOTES=? WHERE UUID=?");
            ps.setInt(1, getMonthlyVotes(uuid) + votes);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTotalVotes(UUID uuid, int votes) {
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE votes SET TOTALVOTES=? WHERE UUID=?");
            ps.setInt(1, getTotalVotes(uuid) + votes);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getTotalVotes(UUID uuid) {
        try {
            if (playerExists(uuid)) {
                PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT TOTALVOTES FROM votes WHERE UUID=?");
                ps.setString(1, uuid.toString());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) return rs.getInt("TOTALVOTES");
            } else addTotalVotes(uuid, 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getMonthlyVotes(UUID uuid) {
        try {
            if (playerExists(uuid)) {
                PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT MONTHLYVOTES FROM votes WHERE UUID=?");
                ps.setString(1, uuid.toString());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) return rs.getInt("MONTHLYVOTES");
            } else addMonthlyVotes(uuid, 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void resetMonthlyVotes() {
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT MONTHLYVOTES FROM votes");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("UUID");
                PreparedStatement query = plugin.sql.getConnection().prepareStatement("UPDATE votes SET MONTHLYVOTES=? WHERE UUID=?");
                query.setInt(1, 0);
                query.setString(2, uuid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dumpToFile() {
        // TODO Make this method dump the database to a file
    }
}

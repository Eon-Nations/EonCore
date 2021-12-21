package me.squid.eoncore.sql;

import me.squid.eoncore.EonCore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    EonCore plugin;
    String IP, database, user, password;
    Connection connection;

    public MySQL(EonCore plugin){
        this.plugin = plugin;
        IP = plugin.getConfig().getString("SQLIP");
        database = plugin.getConfig().getString("SQLDatabase");
        user = plugin.getConfig().getString("SQLUser");
        password = plugin.getConfig().getString("SQLPassword");
    }

    public boolean isConnected() {
        return (connection != null);
    }

    public void connectToDatabase() throws SQLException {
        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" + IP + "/" + database + "?useSSL=false", user, password);
            if (isConnected()) plugin.getLogger().info("Database has successfully connected");
        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }
}

package me.squid.eoncore;

import me.squid.eoncore.misc.listeners.JoinLeaveListener;
import me.squid.eoncore.misc.listeners.WildTpListener;
import me.squid.eoncore.misc.tasks.AutoAnnouncementTask;
import me.squid.eoncore.node.Node;
import me.squid.eoncore.utils.WorldLoader;

import org.bukkit.Bukkit;
import org.eonnations.eonpluginapi.EonPlugin;
import org.eonnations.eonpluginapi.api.database.Database;
import org.eonnations.eonpluginapi.database.sql.Credentials;
import org.eonnations.eonpluginapi.database.sql.SQLDatabase;

public class EonCore extends EonPlugin {

    public EonCore() {
        super();
    }

    @Override
    public void load() { }

    @Override
    public void setup() {
        saveDefaultConfig();
        WorldLoader.initializeWorlds();
        registerDatabase();
        EonCommand.registerCommandsInPackage(this);
        registerListeners();
        runTasks();
        Node.registerChestPlaceListener();
    }

    @Override
    public void cleanup() {
        Bukkit.getScheduler().cancelTasks(this);
    }

    public void registerDatabase() {
        Credentials credentials = Credentials.credentials(this);
        SQLDatabase database = new SQLDatabase(credentials);
        provideService(Database.class, database);
    }

    public void registerListeners() {
        new JoinLeaveListener(this);
        new WildTpListener(this);
    }

    public void runTasks() {
        new AutoAnnouncementTask(this).runTaskTimerAsynchronously(this, 0, getConfig().getLong("Announcement-Delay") * 20L);
    }
}

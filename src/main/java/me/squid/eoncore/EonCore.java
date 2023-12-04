package me.squid.eoncore;

import me.squid.eoncore.misc.listeners.JoinLeaveListener;
import me.squid.eoncore.misc.listeners.WildTpListener;
import me.squid.eoncore.misc.tasks.AutoAnnouncementTask;
import me.squid.eoncore.utils.WorldLoader;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.eonnations.eonpluginapi.EonPlugin;
import org.eonnations.eonpluginapi.api.database.Database;
import org.eonnations.eonpluginapi.database.sql.Credentials;
import org.eonnations.eonpluginapi.database.sql.SQLDatabase;
import org.jetbrains.annotations.NotNull;

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

    @NotNull
    public <T extends Listener> T registerListener(@NotNull T listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
        return listener;
    }

    @NotNull
    public <T> T getService(@NotNull Class<T> serviceClass) {
        RegisteredServiceProvider<T> service = Bukkit.getServicesManager().getRegistration(serviceClass);
        return service.getProvider();
    }

    public <T> void provideService(@NotNull Class<T> service, @NotNull T t, @NotNull ServicePriority servicePriority) {
        Bukkit.getServicesManager().register(service, t, this, servicePriority);
    }

    public <T> void provideService(@NotNull Class<T> service, @NotNull T t) {
        provideService(service, t, ServicePriority.Normal);
    }
}

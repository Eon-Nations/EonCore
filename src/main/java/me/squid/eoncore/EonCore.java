package me.squid.eoncore;

import me.squid.eoncore.commands.BanMuteCommand;
import me.squid.eoncore.database.EconomySetup;
import me.squid.eoncore.database.RedisClient;
import me.squid.eoncore.misc.listeners.ChatFormatListener;
import me.squid.eoncore.misc.listeners.JoinLeaveListener;
import me.squid.eoncore.misc.listeners.PortalListener;
import me.squid.eoncore.misc.listeners.WildTpListener;
import me.squid.eoncore.misc.managers.InventoryManager;
import me.squid.eoncore.misc.managers.MutedManager;
import me.squid.eoncore.misc.tasks.AutoAnnouncementTask;
import me.squid.eoncore.misc.tasks.RestartTask;
import me.squid.eoncore.utils.WorldLoader;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class EonCore extends JavaPlugin {
    private RedisClient client;

    public EonCore() {
        super();
    }

    protected EonCore(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        this.client = new RedisClient(this);
        EconomySetup.hookToVault(this, client);
        saveDefaultConfig();
        WorldLoader.initializeWorlds();
        EonCommand.registerAllCommands(this);
        registerListeners();
        runTasks();
        registerModeration();
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }

    public void registerListeners() {
        new JoinLeaveListener(this);
        new WildTpListener(this);
        new PortalListener(this);
        InventoryManager.registerInventories(this);
    }

    public void registerModeration() {
        MutedManager mutedManager = new MutedManager(this);
        new ChatFormatListener(this, mutedManager);
        new BanMuteCommand(this, mutedManager);
    }

    public void runTasks() {
        new AutoAnnouncementTask(this).runTaskTimerAsynchronously(this, 0, getConfig().getLong("Announcement-Delay") * 20L);
        RestartTask.runRestartTask(this);
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

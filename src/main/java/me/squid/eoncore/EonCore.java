package me.squid.eoncore;

import me.lucko.helper.Services;
import me.lucko.helper.config.ConfigFactory;
import me.lucko.helper.config.ConfigurationNode;
import me.lucko.helper.internal.LoaderUtils;
import me.lucko.helper.plugin.HelperPlugin;
import me.lucko.helper.terminable.composite.CompositeTerminable;
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
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class EonCore extends JavaPlugin implements HelperPlugin {
    private CompositeTerminable registry;
    private RedisClient client;

    public EonCore() {
        super();
    }

    protected EonCore(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
        LoaderUtils.getMainThread();
        LoaderUtils.forceSetPlugin(this);
    }

    @Override
    public void onEnable() {
        this.registry = CompositeTerminable.create();
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

    public static LuckPerms getPerms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        return provider != null ? provider.getProvider() : null;
    }

    @NotNull
    @Override
    public <T extends Listener> T registerListener(@NotNull T listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
        return listener;
    }

    @NotNull
    @Override
    public <T extends CommandExecutor> T registerCommand(@NotNull T executor, String permission, String permissionMessage, String description, @NotNull String... aliases) {
        CommandMap commandMap = getServer().getCommandMap();
        Command command = new Command(aliases[0]) {
            @Override
            public boolean execute(@NotNull CommandSender commandSender, @NotNull String label, @NotNull String[] args) {
                return executor.onCommand(commandSender, this, label, args);
            }
        };
        commandMap.register(aliases[0], command);
        return executor;
    }

    @NotNull
    @Override
    public <T> T getService(@NotNull Class<T> service) {
        return Services.load(service);
    }

    @NotNull
    @Override
    public <T> T provideService(@NotNull Class<T> service, @NotNull T t, @NotNull ServicePriority servicePriority) {
        return Services.provide(service, t, servicePriority);
    }

    @NotNull
    @Override
    public <T> T provideService(@NotNull Class<T> service, @NotNull T t) {
        return Services.provide(service, t);
    }

    @Override
    public boolean isPluginPresent(@NotNull String s) {
        return getServer().getPluginManager().getPlugin(s) != null;
    }

    @Nullable
    @Override
    public <T> T getPlugin(@NotNull String pluginName, @NotNull Class<T> plugin) {
        return (T) getServer().getPluginManager().getPlugin(pluginName);
    }

    private File getRelativeFile(String path) {
        this.getDataFolder().mkdirs();
        return new File(this.getDataFolder(), path);
    }

    @NotNull
    @Override
    public File getBundledFile(@NotNull String name) {
        File file = getRelativeFile(name);
        if (!file.exists()) {
            saveResource(name, false);
        }
        return file;
    }

    @NotNull
    @Override
    public YamlConfiguration loadConfig(@NotNull String file) {
        return YamlConfiguration.loadConfiguration(getBundledFile(file));
    }

    @NotNull
    @Override
    public ConfigurationNode loadConfigNode(@NotNull String file) {
        return ConfigFactory.yaml().load(getBundledFile(file));
    }

    @NotNull
    @Override
    public <T> T setupConfig(@NotNull String f, @NotNull T configObj) {
        File file = getRelativeFile(f);
        ConfigFactory.yaml().load(file, configObj);
        return configObj;
    }

    @NotNull
    @Override
    public ClassLoader getClassloader() {
        return super.getClassLoader();
    }

    @NotNull
    @Override
    public <T extends AutoCloseable> T bind(@NotNull T terminable) {
        return this.registry.bind(terminable);
    }
}

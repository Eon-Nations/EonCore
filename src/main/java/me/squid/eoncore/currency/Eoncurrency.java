package me.squid.eoncore.currency;

import com.google.common.reflect.Reflection;
import me.lucko.helper.Commands;
import me.lucko.helper.Services;
import me.lucko.helper.config.ConfigFactory;
import me.lucko.helper.config.ConfigurationNode;
import me.lucko.helper.internal.LoaderUtils;
import me.lucko.helper.maven.LibraryLoader;
import me.lucko.helper.plugin.HelperPlugin;
import me.lucko.helper.plugin.ap.Plugin;
import me.lucko.helper.plugin.ap.PluginDependency;
import me.lucko.helper.terminable.composite.CompositeTerminable;
import me.lucko.helper.utils.CommandMapUtil;
import me.squid.eoncore.currency.commands.BalanceCommand;
import me.squid.eoncore.currency.commands.PayCommand;
import me.squid.eoncore.currency.managers.EconManager;
import me.squid.eoncore.currency.menus.EcoMenu;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.util.Optional;

@Plugin(name = "Eoncurrency",
        load = PluginLoadOrder.STARTUP,
        depends = {@PluginDependency("Vault")},
        authors = {"Squid"},
        loadBefore = {"MobArena", "AuctionHouse"}
)
public class Eoncurrency extends JavaPlugin implements HelperPlugin {
    EconManager econManager;
    JedisPool pool;
    CompositeTerminable registry;

    public Eoncurrency() {
        super();
    }

    public Eoncurrency(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
        LoaderUtils.getMainThread();
        LoaderUtils.forceSetPlugin(this);
    }

    @Override
    public void onEnable() {
        this.registry = CompositeTerminable.create();
        this.pool = setupPool();
        this.econManager = hookToVault();
        LibraryLoader.loadAll(this);
        saveDefaultConfig();
        EcoMenu ecoMenu = new EcoMenu(econManager);
        registerCommands(ecoMenu);
    }

    @Override
    public void onDisable() {
        unHookVault();
        pool.close();
    }

    public void registerCommands(EcoMenu ecoMenu) {
        PayCommand.registerPayCommand(this);
        BalanceCommand.registerBalance(this);
        Commands.create().assertPlayer()
                .handler(context -> context.sender().openInventory(ecoMenu.ShopCategory()))
                .registerAndBind(this, "shop");
    }

    public JedisPool setupPool() {
        String serverURL = Optional.ofNullable(System.getProperty("REDIS_URL")).orElse("redis://localhost:6379");
        return new JedisPool(serverURL);
    }

    public EconManager hookToVault() {
        EconManager econ = new EconManager(pool);
        Bukkit.getServicesManager().register(Economy.class, econ, this, ServicePriority.Normal);
        Bukkit.getConsoleSender().sendMessage("Vault has successfully hooked to Economy");
        return econ;
    }

    public void unHookVault() {
        Bukkit.getServicesManager().unregister(Economy.class, econManager);
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

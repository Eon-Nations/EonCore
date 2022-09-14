package me.squid.eoncore;

import me.squid.eoncore.commands.BanMuteCommand;
import me.squid.eoncore.listeners.*;
import me.squid.eoncore.managers.InventoryManager;
import me.squid.eoncore.managers.MutedManager;
import me.squid.eoncore.tasks.AutoAnnouncementTask;
import me.squid.eoncore.tasks.RestartTask;
import me.squid.eoncore.utils.WorldLoader;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class EonCore extends JavaPlugin {
    public EonCore() {
        super();
    }

    protected EonCore(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
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
        new GenericMenusListener(this);
        new WildTpListener(this);
        new PhantomSpawnListener(this);
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
}

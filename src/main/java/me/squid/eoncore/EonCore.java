package me.squid.eoncore;

import me.squid.eoncore.commands.*;
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
        registerCommands();
        registerListeners();
        runTasks();
        registerModeration();
    }

    @Override
    public void onDisable() {
        saveConfig();
        Bukkit.getScheduler().cancelTasks(this);
    }

    public void registerCommands(){
        new SurvivalCommand(this);
        new SpectatorCommand(this);
        new HealCommand(this);
        new NightVisionCommand(this);
        new GamemodeCheckCommand(this);
        new InvseeCommand(this);
        new FlyCommand(this);
        new TopCommand(this);
        new RulesCommand(this);
        new TphereCommand(this);
        new TeleportCommand(this);
        new SpawnCommand(this);
        new WildTpCommand(this);
        new TpaCommand(this);
        new TpaAcceptCommand(this);
        new TpDenyCommand(this);
        new WarpsCommand(this);
        new WorkbenchCommand(this);
        new TpposCommand(this);
        new ReviveCommand(this);
        new StaffChatCommand(this);
        new KickCommand(this);
        new RenameCommand(this);
        new VoteRanksCommand(this);
        new HatCommand(this);
        new HelpCommand(this);
        new SudoCommand(this);
        new GrindstoneCommand(this);
        new WorldCommand(this);
        new UptimeCommand(this);
        new InventoryManager();
    }

    public void registerListeners() {
        new JoinLeaveListener(this);
        new GenericMenusListener(this);
        new WildTpListener(this);
        new WarpsListener(this);
        new PhantomSpawnListener(this);
        new PortalListener(this);
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

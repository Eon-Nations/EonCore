package me.squid.eoncore;

import me.squid.eoncore.commands.*;
import me.squid.eoncore.listeners.*;
import me.squid.eoncore.managers.InventoryManager;
import me.squid.eoncore.managers.MutedManager;
import me.squid.eoncore.tasks.AutoAnnouncementTask;
import me.squid.eoncore.utils.Utils;
import me.squid.eoncore.utils.VoidChunkGenerator;
import me.squid.eoncore.voting.VotifierListener;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.*;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import scala.collection.immutable.Stream;

import java.io.File;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class EonCore extends JavaPlugin {
    public EonCore() {
        super();
    }

    protected EonCore(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        loadWorlds();
        saveDefaultConfig();
        registerCommands();
        registerListeners();
        disableRecipes();
        runTasks();
        setupGameRules();
        registerModeration();
    }

    @Override
    public void onDisable() {
        saveConfig();
        Bukkit.getScheduler().cancelTasks(this);
    }

    public void registerCommands(){
        new SurvivalCommand(this);
        new CreativeCommand(this);
        new SpectatorCommand(this);
        new HealCommand(this);
        new FeedCommand(this);
        new EnderChestCommand(this);
        new ClearInventoryCommand(this);
        new DiscordCommand(this);
        new DisposalCommand(this);
        new NightVisionCommand(this);
        new GamemodeCheckCommand(this);
        new PickupCommand(this);
        new InvseeCommand(this);
        new FlyCommand(this);
        new CommandSpyCommand(this);
        new TopCommand(this);
        new RulesCommand(this);
        new TphereCommand(this);
        new TeleportCommand(this);
        new SpawnCommand(this);
        new WildTpCommand(this);
        new TpaCommand(this);
        new TpaAcceptCommand(this);
        new TpDenyCommand(this);
        new BackCommand(this);
        new WarpsCommand(this);
        new WorkbenchCommand(this);
        new TpposCommand(this);
        new ReviveCommand(this);
        new StaffChatCommand(this);
        new KickCommand(this);
        new ClearChatCommand(this);
        new RenameCommand(this);
        new VoteRanksCommand(this);
        new HatCommand(this);
        new PWeatherCommand(this);
        new PTimeCommand(this);
        new HelpCommand(this);
        new GetPositionCommand(this);
        new FixCommand(this);
        new SudoCommand(this);
        new GrindstoneCommand(this);
        new DirectMessageCommand(this);
        new WorldCommand(this);
        new ClockCommand(this);
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
        runRestartTask();
    }

    public void runRestartTask() {
        Runnable restartTask = () -> {
            Bukkit.broadcastMessage(getRestartingMessage());
            Bukkit.getScheduler().runTaskLater(this, Bukkit::shutdown, 40L);
        };
        Runnable messageTask = () -> Bukkit.broadcastMessage(getWarningMessage());

        runScheduledTask(messageTask, true);
        runScheduledTask(restartTask, false);
    }

    private void runScheduledTask(Runnable runnable, boolean messageTask) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
        ZonedDateTime restartTime = now.withHour(5).withMinute(0).withSecond(0);

        if (now.compareTo(restartTime) > 0)
            restartTime = restartTime.plusDays(1);

        if (messageTask)
            restartTime = restartTime.minusMinutes(5);

        Duration duration = Duration.between(now, restartTime);
        long delay = duration.getSeconds();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(runnable, delay, TimeUnit.SECONDS);
    }

    public void disableRecipes() {
        List<Material> bannedMats = List.of(Material.HOPPER);
        bannedMats.forEach(Utils::removeRecipe);
    }

    private void loadWorlds() {
        try {
            new WorldCreator("spawn_void").generator(new VoidChunkGenerator()).createWorld();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupGameRules() {
        List<GameRule<Boolean>> gameRulesToSet = List.of(
                GameRule.ANNOUNCE_ADVANCEMENTS,
                GameRule.SHOW_DEATH_MESSAGES,
                GameRule.COMMAND_BLOCK_OUTPUT,
                GameRule.SPECTATORS_GENERATE_CHUNKS);
        List<World> worlds = Bukkit.getWorlds();
        BiConsumer<List<GameRule<Boolean>>, World> setGameRule =
                (gameRules, world) -> gameRules.forEach(gameRule -> world.setGameRule(gameRule, false));
        worlds.forEach(world -> setGameRule.accept(gameRulesToSet, world));
        setSpawnGameRules(setGameRule);
    }

    private void setSpawnGameRules(BiConsumer<List<GameRule<Boolean>>, World> setGameRule) {
        List<GameRule<Boolean>> spawnRules = List.of
                (GameRule.DO_FIRE_TICK,
                GameRule.DO_WEATHER_CYCLE,
                GameRule.DO_DAYLIGHT_CYCLE,
                GameRule.DO_MOB_SPAWNING);
        World spawn = Bukkit.getWorld("spawn_void");
        setGameRule.accept(spawnRules, spawn);
    }

    public static LuckPerms getPerms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        return provider != null ? provider.getProvider() : null;
    }

    private String getWarningMessage() {
        return Utils.getPrefix("nations") + Utils.chat("Restarting Server in 5 minutes!");
    }

    private String getRestartingMessage() {
        return Utils.getPrefix("nations") + Utils.chat("Restarting server!");
    }
}

package me.squid.eoncore;

import me.squid.eoncore.commands.*;
import me.squid.eoncore.listeners.*;
import me.squid.eoncore.menus.AdminGUI;
import me.squid.eoncore.managers.MutedManager;
import me.squid.eoncore.tasks.AutoAnnouncementTask;
import me.squid.eoncore.utils.Utils;
import me.squid.eoncore.utils.VoidChunkGenerator;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EonCore extends JavaPlugin {

    public static final String prefix = "&7[&5&lEon Nations&7] &r";

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
        new ExtinguishCommand(this);
        new DiscordCommand(this);
        new VanishCommand(this);
        new BurnCommand(this);
        new DisposalCommand(this);
        new NightVisionCommand(this);
        new GamemodeCheckCommand(this);
        new PickupCommand(this);
        new InvseeCommand(this);
        new FlyCommand(this);
        new CommandSpyCommand(this);
        new FreezeCommand(this);
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
    }

    public void registerListeners() {
        new JoinLeaveListener(this);
        new GenericMenusListener(this);
        new DeathBackListener(this);
        new WildTpListener(this);
        new WarpsListener(this);
        new PhantomSpawnListener(this);
        new PortalListener(this);
    }

    public void registerModeration() {
        MutedManager mutedManager = new MutedManager(this);
        AdminGUI adminGUI = new AdminGUI(mutedManager);
        new AdminMenuManager(this, mutedManager, adminGUI);
        new ChatFormatListener(this, mutedManager);
        new MutedCommand(this, adminGUI);
        new AdminGUICommand(this, adminGUI);
        new BanMuteCommand(this, mutedManager);
    }

    public void runTasks() {
        new AutoAnnouncementTask(this).runTaskTimerAsynchronously(this, 0, getConfig().getLong("Announcement-Delay") * 20L);
        /*
        new PortalTeleportTask(this).runTaskTimerAsynchronously(this, 0, 20L);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new UtilityDoorTask(this), 0, 20L);
        new BasicMineTask(this, new Location(Bukkit.getWorld("spawn"), -443, 94, -288),
               new Location(Bukkit.getWorld("spawn"), -455, 73, -300),
               new Location(Bukkit.getWorld("spawn"), -430, 94, -294)).runTaskTimerAsynchronously(this, 3000L, 24000L);
         */
        runRestartTask();
    }

    public void runRestartTask() {
        Runnable restartTask = () -> {
            Bukkit.getOnlinePlayers().forEach(
                    player -> player.sendMessage(Utils.getPrefix("nations").append(Utils.chat("Restarting server!"))));
            Bukkit.getScheduler().runTaskLater(this, Bukkit::shutdown, 40L);
        };
        Runnable messageTask = () -> Bukkit.getServer().sendMessage(Utils.getPrefix("nations").append(Utils.chat("Restarting Server in 5 minutes!")));

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
        new WorldCreator("spawn_void").generator(new VoidChunkGenerator()).createWorld();
    }

    @SuppressWarnings("ConstantConditions")
    private void setupGameRules() {
        for (World world : Bukkit.getWorlds()) {
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
            world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, false);
            world.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false);
        }
        Bukkit.getWorld("spawn_void").setGameRule(GameRule.DO_FIRE_TICK, false);
        Bukkit.getWorld("spawn_void").setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        Bukkit.getWorld("spawn_void").setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        Bukkit.getWorld("spawn_void").setGameRule(GameRule.DO_MOB_SPAWNING, false);
    }

    public static LuckPerms getPerms() {
        return LuckPermsProvider.get();
    }
}

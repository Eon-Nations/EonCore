package me.squid.eoncore;

import me.squid.eoncore.commands.*;
import me.squid.eoncore.listeners.*;
import me.squid.eoncore.menus.AdminGUI;
import me.squid.eoncore.sql.AdminSQLManager;
import me.squid.eoncore.sql.MySQL;
import me.squid.eoncore.sql.VotesManager;
import me.squid.eoncore.tasks.AutoAnnouncementTask;
import me.squid.eoncore.utils.Utils;
import me.squid.eoncore.utils.VoidChunkGenerator;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Calendar;

public class EonCore extends JavaPlugin {

    public static final String prefix = "&7[&5&lEon Nations&7] &r";
    public MySQL sql;
    private static LuckPerms api;

    @Override
    public void onEnable() {
        loadWorlds();
        saveDefaultConfig();
        registerCommands();
        registerListeners();
        disableRecipes();
        runTasks();
        setupGameRules();
        connectToSQL();
        registerModeration();
    }

    @Override
    public void onDisable() {
        saveConfig();
        Bukkit.getScheduler().cancelTasks(this);
        sql.disconnect();
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
        new RanksCommand(this);
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
    }

    public void registerListeners() {
        new JoinLeaveListener(this);
        new CommandSendListener(this);
        new GenericMenusListener(this);
        new DeathBackListener(this);
        new WildTpListener(this);
        new WarpsListener(this);
        new PhantomSpawnListener(this);
        new PortalListener(this);
    }

    public void registerModeration() {
        AdminSQLManager adminSQLManager = new AdminSQLManager(this);
        AdminGUI adminGUI = new AdminGUI(adminSQLManager);
        new AdminMenuManager(this, adminSQLManager, adminGUI);
        new ChatFormatListener(this, adminSQLManager);
        new MutedCommand(this, adminGUI);
        new AdminGUICommand(this, adminGUI);
        new BanMuteCommand(this, adminSQLManager);
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
    }

    public void disableRecipes() {
        Utils.removeRecipe(Material.HOPPER);
        Utils.removeRecipe(Material.ITEM_FRAME);
        Utils.removeRecipe(Material.TNT);
    }

    private void loadWorlds() {
        new WorldCreator("spawn_void").generator(new VoidChunkGenerator()).createWorld();
    }

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

    private void connectToSQL() {
        sql = new MySQL(this);
        try {
            sql.connectToDatabase();
        } catch (SQLException throwables) {
            getLogger().warning("SQL Database connection has failed.");
        }
        if (sql.isConnected()) {
            VotesManager votesManager = new VotesManager(this);
            votesManager.createTable();
            if (Calendar.DAY_OF_MONTH == 1) {
                votesManager.resetMonthlyVotes();
                votesManager.dumpToFile();
            }
        }
    }
}

package me.squid.eoncore;

import me.squid.eoncore.commands.*;
import me.squid.eoncore.files.BanMessages;
import me.squid.eoncore.files.MutedPlayers;
import me.squid.eoncore.listeners.*;
import me.squid.eoncore.tasks.AutoAnnouncementTask;
import me.squid.eoncore.tasks.PortalTeleportTask;
import me.squid.eoncore.tasks.UtilityDoorTask;
import me.squid.eoncore.utils.Utils;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class EonCore extends JavaPlugin {

    public static final String prefix = "&7[&5&lEon Survival&7] &r";
    private static LuckPerms api;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        registerCommands();
        registerListeners();
        setupFiles();
        disableRecipes();
        runTasks();
        setupLuckPerms();
        setupGameRules();
    }

    @Override
    public void onDisable() {
        saveConfig();
        BanMessages banMessages = new BanMessages();
        MutedPlayers mutedPlayers = new MutedPlayers();
        try {
            banMessages.saveFile();
            mutedPlayers.saveFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerCommands(){
        new AdminGUICommand(this);
        new SurvivalCommand(this);
        new CreativeCommand(this);
        new SpectatorCommand(this);
        new HealCommand(this);
        new FeedCommand(this);
        new AnvilCommand(this);
        new EnderChestCommand(this);
        new ClearInventoryCommand(this);
        new ExtinguishCommand(this);
        new DiscordCommand(this);
        new VanishCommand(this);
        new BurnCommand(this);
        new DisposalCommand(this);
        new NightVisionCommand(this);
        new FCommand(this);
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
    }

    public void registerListeners() {
        new JoinLeaveListener(this);
        new AdminMenuManager(this);
        new CommandSendListener(this);
        new GenericMenusListener(this);
        new DeathBackListener(this);
        new WildTpListener(this);
        new KitListener(this);
        new WarpsListener(this);
        new PhantomSpawnListener(this);
    }

    public void setupFiles(){
        BanMessages banMessages = new BanMessages();
        banMessages.setupFile();
        banMessages.getConfig().options().copyDefaults(true);
        banMessages.loadDefaults();

        MutedPlayers mutedPlayers = new MutedPlayers();
        mutedPlayers.setupFile();
        mutedPlayers.getConfig().options().copyDefaults(true);
        mutedPlayers.loadDefaults();
        mutedPlayers.saveFile();
    }

    public void runTasks() {
        new AutoAnnouncementTask(this).runTaskTimerAsynchronously(this, 0, getConfig().getLong("Announcement-Delay") * 20L);
        new PortalTeleportTask(this).runTaskTimerAsynchronously(this, 0, 20L);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new UtilityDoorTask(this), 0, 20L);
    }

    public void disableRecipes() {
        Utils.removeRecipe(Material.HOPPER);
        Utils.removeRecipe(Material.ITEM_FRAME);
        Utils.removeRecipe(Material.TNT);
    }

    private void setupLuckPerms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            api = provider.getProvider();
        } else System.out.println("LuckPerms is returning null");
    }

    private void setupGameRules() {
        for (World world : Bukkit.getWorlds()) {
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
            world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, false);
            world.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false);
        }
        Bukkit.getWorld("spawn").setGameRule(GameRule.DO_MOB_SPAWNING, false);
    }

    public static LuckPerms getPerms() {
        return api;
    }
}

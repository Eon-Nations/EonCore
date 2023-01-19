package me.squid.eoncore;

import me.squid.eoncore.misc.commands.RegisterCommand;
import me.squid.eoncore.misc.utils.Teleport;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.Set;

public abstract class EonCommand implements CommandExecutor {
    protected JavaPlugin core;
    protected Teleport teleport;

    protected EonCommand(String name, JavaPlugin core) {
        this.core = core;
        this.teleport = new Teleport((EonCore) core);
        registerCommand(name);
    }

    private void registerCommand(String name) {
        PluginCommand pluginCommand = core.getCommand(name);
        pluginCommand.setExecutor(this);
    }

    protected abstract void execute(Player player, String[] args);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            execute(p, args);
        } else {
            sender.sendMessage("Commands cannot be executed by the console");
            return false;
        }
        return true;
    }

    public static void registerAllCommands(EonCore plugin) {
        final String PACKAGE = "me.squid.eoncore.commands";
        Reflections reflections = new Reflections(PACKAGE);
        Set<Class<?>> commands = reflections.getTypesAnnotatedWith(RegisterCommand.class);
        commands.forEach(command -> registerCommand(command, plugin));
    }

    private static void registerCommand(Class<?> command, EonCore plugin) {
        try {
            command.getDeclaredConstructor(EonCore.class).newInstance(plugin);
        } catch (ReflectiveOperationException e) {
            plugin.getLogger().severe("Command " + command.getSimpleName() + " has failed to initialize");
        }
    }
}

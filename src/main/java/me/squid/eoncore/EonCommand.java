package me.squid.eoncore;

import me.squid.eoncore.commands.RegisterCommand;
import me.squid.eoncore.utils.Teleport;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public abstract class EonCommand implements CommandExecutor {
    protected JavaPlugin core;
    protected Teleport teleport;

    protected EonCommand(String name, JavaPlugin core) {
        this.core = core;
        this.teleport = new Teleport((EonCore) core);
        registerCommand(name);
    }

    protected EonCommand(EonCore core, String name, String description, String usage, String permission, String... aliases) {
        this.core = core;
        this.teleport = new Teleport(core);
        registerCommand(name, description, usage, permission, aliases);
    }

    private void registerCommand(String name) {
        PluginCommand command = core.getCommand(name);
        command.setExecutor(this);
    }

    private void registerCommand(String name, String description, String usage, String permission, String... aliases) {
        Command command = new Command(name) {
            @Override
            public boolean execute(@NotNull CommandSender commandSender, @NotNull String label, @NotNull String[] args) {
                return onCommand(commandSender, this, label, args);
            }
        };
        command.setAliases(Arrays.asList(aliases));
        command.setDescription(description);
        Optional.ofNullable(permission).ifPresent(command::setPermission);
        command.setUsage(usage);
        CommandMap map = core.getServer().getCommandMap();
        map.register(name, command);
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

    private static void registerCommand(Class<?> eonCommand, EonCore plugin) {
        try {
            eonCommand.getDeclaredConstructor(EonCore.class).newInstance(plugin);
        } catch (ReflectiveOperationException e) {
            plugin.getLogger().severe("Command " + eonCommand.getSimpleName() + " has failed to initialize");
        }
    }
}

package me.squid.eoncore;

import io.vavr.collection.List;
import io.vavr.control.Option;
import me.squid.eoncore.utils.Teleport;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.eonnations.eonpluginapi.api.Alias;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.Set;

public abstract class EonCommand implements CommandExecutor {
    protected JavaPlugin core;
    protected Teleport teleport;

    protected EonCommand(JavaPlugin core) {
        this.core = core;
        this.teleport = new Teleport((EonCore) core);
    }

    private void registerCommand(String name, String description, String usage, String permission, Alias[] rawAliases) {
        List<String> aliases = List.ofAll(Arrays.asList(rawAliases))
                .map(Alias::name);
        Command command = new Command(name, description, usage, aliases.asJava()) {
            @Override
            public boolean execute(@NotNull CommandSender commandSender, @NotNull String label, @NotNull String[] args) {
                return onCommand(commandSender, this, label, args);
            }
        };
        if (!permission.isEmpty()) command.setPermission(permission);
        CommandMap map = core.getServer().getCommandMap();
        map.register(name, "eoncore", command);
    }

    protected abstract void execute(Player player, String[] args);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        boolean hasPermission = Option.of(command.getPermission())
                .map(sender::hasPermission)
                .getOrElse(true);
        if (sender instanceof Player p && hasPermission) {
            execute(p, args);
            return true;
        } else if (sender instanceof ConsoleCommandSender console) {
            console.sendMessage("Commands cannot be executed by the console");
        }
        return false;
    }

    public static void registerCommandsInPackage(EonCore plugin) {
        Reflections reflections = new Reflections("me.squid.eoncore");
        Set<Class<?>> commands = reflections.getTypesAnnotatedWith(org.eonnations.eonpluginapi.api.Command.class);
        commands.forEach(command -> registerSingleCommand(command, plugin));
    }

    private static void registerSingleCommand(Class<?> eonCommand, EonCore plugin) {
        try {
            var commandInfo = eonCommand.getAnnotation(org.eonnations.eonpluginapi.api.Command.class);
            EonCommand command = (EonCommand) eonCommand.getDeclaredConstructor(EonCore.class).newInstance(plugin);
            command.registerCommand(commandInfo.name(), commandInfo.description(), commandInfo.usage(), commandInfo.permission(), commandInfo.aliases());
        } catch (ReflectiveOperationException e) {
            plugin.getLogger().severe("Command " + eonCommand.getSimpleName() + " has failed to initialize");
        }
    }
}

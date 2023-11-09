package me.squid.eoncore;

import io.vavr.collection.List;
import io.vavr.control.Option;
import me.squid.eoncore.utils.Teleport;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
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

    private void registerCommand(String name, String description, String usage, String permission, Alias[] aliases) {
        List<String> mappedAliases = List.ofAll(Arrays.asList(aliases))
                .map(Alias::name);
        Command command = new Command(name, description, usage, mappedAliases.asJava()) {
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
        Option<String> permissionOpt = Option.of(command.getPermission());
        if (sender instanceof Player p) {
            boolean hasPermission = permissionOpt
                    .map(p::hasPermission)
                    .getOrElse(true);
            if (hasPermission) {
                execute(p, args);
                return true;
            }
        } else {
            sender.sendMessage("Commands cannot be executed by the console");
        }
        return false;
    }

    public static void registerCommandsInPackage(EonCore plugin) {
        Reflections reflections = new Reflections("me.squid.eoncore.commands");
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

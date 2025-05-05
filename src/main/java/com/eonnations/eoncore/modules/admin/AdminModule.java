package com.eonnations.eoncore.modules.admin;

import com.eonnations.eoncore.EonCore;
import com.eonnations.eoncore.common.EonModule;
import com.eonnations.eoncore.messaging.*;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import io.vavr.control.Option;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class AdminModule extends EonModule {

    private static final String ADMIN_PERMISSION_STRING = "eoncore.admin";

    public AdminModule(EonCore plugin) {
        super(plugin, LoadOrder.MOST);
    }

    @Override
    public void load() {

    }

    @Override
    public void setup() {
        new CommandAPICommand("gmc")
                .withPermission(ADMIN_PERMISSION_STRING)
                .withOptionalArguments(new PlayerArgument("target"))
                .executesPlayer((player, args) -> {
                    Option<Player> targetOpt = Option.of(args.getByClass("target", Player.class));
                    Player playerToSwitch = targetOpt.isDefined() ? targetOpt.get() : player;
                    playerToSwitch.setGameMode(GameMode.CREATIVE);
                     Messenger messenger = Messaging.messenger(EonPrefix.MODERATION);
                     messenger.send(player, Component.text("Switched to creative mode"));
                }).register(plugin);
        new CommandAPICommand("gms")
                .withPermission(ADMIN_PERMISSION_STRING)
                .withOptionalArguments(new PlayerArgument("target"))
                .executesPlayer((player, args) -> {
                    Option<Player> targetOpt = Option.of(args.getByClass("target", Player.class));
                    Player playerToSwitch = targetOpt.isDefined() ? targetOpt.get() : player;
                    playerToSwitch.setGameMode(GameMode.SURVIVAL);
                    Messenger messenger = Messaging.messenger(EonPrefix.MODERATION);
                    messenger.send(player, Component.text("Switched to survival mode"));
                }).register(plugin);
        new CommandAPICommand("gmsp")
                .withPermission(ADMIN_PERMISSION_STRING)
                .withOptionalArguments(new PlayerArgument("target"))
                .executesPlayer((player, args) -> {
                    Option<Player> targetOpt = Option.of(args.getByClass("target", Player.class));
                    Player playerToSwitch = targetOpt.isDefined() ? targetOpt.get() : player;
                    playerToSwitch.setGameMode(GameMode.SPECTATOR);
                    Messenger messenger = Messaging.messenger(EonPrefix.MODERATION);
                    messenger.send(player, Component.text("Switched to spectator mode"));
                }).register(plugin);
        new CommandAPICommand("world")
                .withPermission(ADMIN_PERMISSION_STRING)
                .withArguments(new StringArgument("world")
                        .replaceSuggestions(ArgumentSuggestions.strings(Bukkit.getWorlds().stream().map(World::getName).toList())))
                .executesPlayer((player, args) -> {
                    String worldChoice = args.getUnchecked("world");
                    World world = Bukkit.getWorld(worldChoice);
                    if (world == null) {
                        Messaging.sendNullMessage(player);
                        return;
                    }
                    player.teleportAsync(world.getSpawnLocation()).thenAccept(teleported -> {
                        if (teleported) {
                            Messenger messenger = Messaging.messenger(EonPrefix.MODERATION);
                            messenger.send(player, Component.text("Teleported to world: " + worldChoice));
                        }
                    });
                }).register(plugin);
        new CommandAPICommand("invsee")
                .withPermission(ADMIN_PERMISSION_STRING)
                .withArguments(new PlayerArgument("target"))
                .executesPlayer((player, args) -> {
                    Player target = args.getByClass("target", Player.class);
                    if (target.hasPermission(ADMIN_PERMISSION_STRING)) {
                        Messaging.sendNullMessage(player);
                        return;
                    }
                    player.openInventory(target.getInventory());
                }).register(plugin);
        new CommandAPICommand("trash")
                .executesPlayer((player, args) -> {
                    Inventory trashInventory = Bukkit.createInventory(null, 27, Component.text("Trash"));
                    player.openInventory(trashInventory);
                }).register(plugin);
        new CommandAPICommand("config")
                .withPermission(ADMIN_PERMISSION_STRING)
                .withSubcommand(new CommandAPICommand("reload")
                        .executesPlayer((sender, args) -> {
                            plugin.reloadConfig();
                            SimpleConfigMessenger messenger = Messaging.setupSimpleConfigMessenger(plugin.getConfig(), EonPrefix.MODERATION);
                            messenger.sendMessage(sender, "Config_Reload");
                        }))
                .register(plugin);
    }

    @Override
    public void cleanup() {

    }

    @Override
    public String name() {
        return "Admin";
    }
}

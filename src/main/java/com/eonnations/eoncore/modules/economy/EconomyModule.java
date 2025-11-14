package com.eonnations.eoncore.modules.economy;

import com.eonnations.eoncore.EonCore;
import com.eonnations.eoncore.common.EonModule;
import com.eonnations.eoncore.common.api.database.Database;
import com.eonnations.eoncore.common.api.records.Vault;
import com.eonnations.eoncore.common.events.EventSubscriber;
import com.eonnations.eoncore.messaging.ConfigMessenger;
import com.eonnations.eoncore.messaging.EonPrefix;
import com.eonnations.eoncore.messaging.Messaging;
import com.eonnations.eoncore.messaging.Messenger;
import com.eonnations.eoncore.utils.menus.ButtonFunction;
import com.eonnations.eoncore.utils.parsers.MenuParser;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.*;
import dev.jorel.commandapi.executors.CommandArguments;
import io.vavr.Function1;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.xenondevs.invui.window.Window;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

public class EconomyModule extends EonModule {
    private static final List<String> currencies = List.of("copper", "iron", "gold", "diamond", "emerald");

    public EconomyModule(EonCore plugin) {
        super(plugin, LoadOrder.MOST);
    }

    @Override
    public void load() {

    }

    private boolean handleNewPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            plugin.getDatabase().createPlayer(event.getPlayer().getUniqueId(), event.getPlayer().getName());
        }
        return false;
    }

    private Option<SQLException> giveResourceToPlayer(Player sender, OfflinePlayer target, String resource,
            int amount) {
        Database database = plugin.getDatabase();
        int vaultId = database.playerVaultId(target.getUniqueId())
                .getOrElseGet(e -> -1);
        if (vaultId == -1) {
            return Option.some(new SQLException("Could not find vault for " + target.getUniqueId()));
        }
        Option<SQLException> res = database.addResourceToPlayer(target.getUniqueId(), resource, amount);
        res.peek(e -> {
            ConfigMessenger messenger = Messaging.setupConfigMessenger(plugin.getConfig(), EonPrefix.ISLANDS);
            messenger.sendMessage(sender, "Error occured giving a player a resource", HashMap.empty());
        });
        return res;
    }

    private void economyGiveTakeCommand(Player sender, CommandArguments args, boolean isTake) {
        OfflinePlayer target = args.getByClass("target", OfflinePlayer.class);
        String type = args.getByClass("type", String.class);
        int amount = args.getUnchecked("amount");
        amount = isTake ? -amount : amount;
        ConfigMessenger messenger = Messaging.setupConfigMessenger(plugin.getConfig(), EonPrefix.MODERATION);
        Option<SQLException> errorOpt = giveResourceToPlayer(sender, target, type, amount);
        if (errorOpt.isDefined()) {
            Messenger normalMessenger = Messaging.messenger(EonPrefix.MODERATION);
            normalMessenger.send(sender, Component.text("Error occurred: " + errorOpt.get().getMessage()));
            return;
        }
        Map<String, String> replacementVals = List.of(Tuple.of("<type>", type),
                Tuple.of("<amount>", String.valueOf(Math.abs(amount))))
                .toMap(Tuple2::_1, Tuple2::_2);
        String configPath = isTake ? "ECO_Successful_Take" : "ECO_Successful_Give";
        messenger.sendMessage(sender, configPath, replacementVals);
    }

    private void economyGiveCommand(Player sender, CommandArguments args) {
        economyGiveTakeCommand(sender, args, false);
    }

    private void economyInfoCommand(Player sender, CommandArguments args) {
        OfflinePlayer target = args.getByClass("target", OfflinePlayer.class);
        Database database = plugin.getDatabase();
        Vault vault = database.playerVault(target.getUniqueId())
                .fold(e -> Vault.getDefault(), Function1.identity());
        ConfigMessenger messenger = Messaging.setupConfigMessenger(plugin.getConfig(), EonPrefix.MODERATION);
        Map<String, String> replacementVals = List.ofAll(Arrays.stream(Vault.ResourceType.values()))
                .map(Vault.ResourceType::toString)
                .map(String::toLowerCase)
                .toMap(Function1.identity(), resource -> String.valueOf(vault.getAmount(resource)))
                .put("id", String.valueOf(vault.id()))
                .mapKeys(k -> "<" + k + ">");
        messenger.sendMessage(sender, "ECO_Info", replacementVals);
    }

    private void economyTakeCommand(Player sender, CommandArguments args) {
        economyGiveTakeCommand(sender, args, true);
    }

    private Map<String, String> vaultPlaceholderMap(UUID uuid) {
        Vault vault = plugin.getDatabase().playerVault(uuid)
                .peekLeft(SQLException::printStackTrace)
                .fold(e -> Vault.getDefault(), Function1.identity());
        return List.ofAll(Arrays.stream(Vault.ResourceType.values()))
                .map(Vault.ResourceType::toString)
                .map(String::toLowerCase)
                .map(s -> {
                    if (s.equals("gold")) {
                        return "currency_gold";
                    }
                    return s;
                })
                .map(type -> "<" + type + ">")
                .toMap(Function1.identity(), resource -> String.valueOf(vault.getAmountFromPlaceholder(resource)));
    }

    private void openVaultMenu(Player sender, CommandArguments args) {
        Option<OfflinePlayer> targetOpt = Option.ofOptional(args.getOptionalByClass("target", OfflinePlayer.class));
        UUID targetId = targetOpt.isDefined() ? targetOpt.get().getUniqueId() : sender.getUniqueId();
        ButtonFunction closeFunc = (p, e) -> {
            p.closeInventory();
            return null;
        };
        Map<Character, ButtonFunction> buttons = List.of(Tuple.of('b', closeFunc))
                .toMap(Tuple2::_1, Tuple2::_2);
        YamlConfiguration menuConfig = plugin.getMenuRegistry().getMenu("vault").get();
        Window window = MenuParser.parse(menuConfig, sender, vaultPlaceholderMap(targetId), buttons);
        window.open();
    }

    @Override
    public void setup() {
        EventSubscriber.subscribe(PlayerJoinEvent.class, EventPriority.NORMAL)
                .handler(this::handleNewPlayerJoin);
        List<Argument<?>> giveTakeArguments = List.of(new EntitySelectorArgument.OnePlayer("target"),
                new StringArgument("type").replaceSuggestions(ArgumentSuggestions.strings(currencies.toJavaList())),
                new IntegerArgument("amount"));
        new CommandAPICommand("economy")
                .withPermission("eoncore.admin.economy")
                .withSubcommand(new CommandAPICommand("give")
                        .withArguments(giveTakeArguments.toJavaList())
                        .executesPlayer(this::economyGiveCommand))
                .withSubcommand(new CommandAPICommand("info")
                        .withArguments(new EntitySelectorArgument.OnePlayer("target"))
                        .executesPlayer(this::economyInfoCommand))
                .withSubcommand(new CommandAPICommand("take")
                        .withArguments(giveTakeArguments.toJavaList())
                        .executesPlayer(this::economyTakeCommand))
                .register(plugin);
        new CommandAPICommand("vault")
                .withOptionalArguments(new EntitySelectorArgument.OnePlayer("target"))
                .executesPlayer(this::openVaultMenu)
                .register(plugin);
    }

    @Override
    public void cleanup() {

    }

    @Override
    public String name() {
        return "Database";
    }
}

package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Alias;
import org.eonnations.eonpluginapi.api.Command;

import io.vavr.control.Option;

@Command(name = "clearinventory",
        usage = "/ci <player>",
        aliases = {@Alias(name = "ci")},
        permission = "eoncommands.clearinventory")
public class ClearInventoryCommand extends EonCommand {
    private static final String OTHERS_NODE = "eoncommands.clearinventory.others";

    public ClearInventoryCommand(EonCore plugin) {
        super(plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        FileConfiguration config = core.getConfig();
        ConfigMessenger messenger = Messaging.setupConfigMessenger(config, EonPrefix.MODERATION);
        if (args.length == 0) {
            clearInventory(player, messenger);
        } else if (args.length == 1 && player.hasPermission(OTHERS_NODE)) {
            Option<Player> target = Option.of(Bukkit.getPlayer(args[0]))
                .map(p -> clearInventory(p, messenger))
                .onEmpty(() -> Messaging.sendNullMessage(player));
            if (target.isDefined()) {
                messenger.sendMessage(player, "Clear-Other-Inventory");
            }
        }
    }

    private Player clearInventory(Player player, ConfigMessenger messenger) {
        player.getInventory().clear();
        messenger.sendMessage(player, "Clear-Self-Inventory");
        return player;
    }
}

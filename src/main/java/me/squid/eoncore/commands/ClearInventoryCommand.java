package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Alias;
import org.eonnations.eonpluginapi.api.Command;

import java.util.function.Consumer;

import static me.squid.eoncore.utils.FunctionalBukkit.getPlayerOrSendMessage;

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
        Consumer<Player> inventoryClear = clearInventory(messenger);
        if (args.length == 0) {
            inventoryClear.accept(player);
        } else if (args.length == 1 && player.hasPermission(OTHERS_NODE)) {
            getPlayerOrSendMessage(player, inventoryClear, args[0]);
            messenger.sendMessage(player, "Clear-Other-Inventory");
        }
    }

    private Consumer<Player> clearInventory(ConfigMessenger messenger) {
        Consumer<Player> clearInventory = player -> player.getInventory().clear();
        return clearInventory.andThen(player -> messenger.sendMessage(player, "Clear-Self-Inventory"));
    }
}

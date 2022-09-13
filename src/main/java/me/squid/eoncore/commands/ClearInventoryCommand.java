package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

import static me.squid.eoncore.utils.FunctionalBukkit.getPlayerOrSendMessage;

@RegisterCommand
public class ClearInventoryCommand extends EonCommand {
    private static final String OTHERS_NODE = "eoncommands.clearinventory.others";

    public ClearInventoryCommand(EonCore plugin) {
        super("clearinventory", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        FileConfiguration config = core.getConfig();
        Consumer<Player> inventoryClear = clearInventory(player, config);
        if (args.length == 0) {
            inventoryClear.accept(player);
        } else if (args.length == 1 && player.hasPermission(OTHERS_NODE)) {
            getPlayerOrSendMessage(player, inventoryClear, args[0]);
        }
    }

    private Consumer<Player> clearInventory(Player sender, FileConfiguration config) {
        ConfigMessenger messenger = Messaging.setupConfigMessenger(config, EonPrefix.MODERATION);
        messenger.sendMessage(sender, "Clear-Other-Inventory");
        Consumer<Player> clearInventory = player -> player.getInventory().clear();
        return clearInventory.andThen(player -> messenger.sendMessage(player, "Clear-Self-Inventory"));
    }
}

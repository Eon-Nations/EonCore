package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Messaging;
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
        Consumer<Player> inventoryClear = clearInventory(config);
        if (args.length == 0) {
            inventoryClear.accept(player);
        } else if (args.length == 1 && player.hasPermission(OTHERS_NODE)) {
            getPlayerOrSendMessage(player, inventoryClear, args[0]);
        }
    }

    private Consumer<Player> clearInventory(FileConfiguration config) {
        String clearMessage = config.getString("Clear-Self-Inventory");
        return player -> {
            player.getInventory().clear();
            Messaging.sendNationsMessage(player, clearMessage);
        };
    }
}

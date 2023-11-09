package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.eonnations.eonpluginapi.api.Command;

@Command(name = "rename", usage = "/rename <name>", permission = "eoncommands.rename")
public class RenameCommand extends EonCommand {

    public RenameCommand(EonCore plugin) {
        super(plugin);
    }


    @Override
    protected void execute(Player player, String[] args) {
        Component itemName = itemName(args);
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        meta.displayName(itemName);
        item.setItemMeta(meta);
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.NATIONS);
        messenger.sendMessage(player, "Rename-Message");
    }

    private Component itemName(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append(" ");
        }
        String allArgs = sb.toString().trim();
        return Messaging.fromFormatString(allArgs);
    }
}

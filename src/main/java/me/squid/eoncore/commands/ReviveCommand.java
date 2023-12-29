package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.eonnations.eonpluginapi.api.Command;
import org.eonnations.eonpluginapi.events.EventSubscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static me.squid.eoncore.utils.FunctionalBukkit.getPlayerOrSendMessage;

@Command(name = "revive", usage = "/revive <player>", permission = "eoncommands.revive")
public class ReviveCommand extends EonCommand {
    private final HashMap<Player, List<ItemStack>> items = new HashMap<>();

    public ReviveCommand(EonCore plugin) {
        super(plugin);
        EventSubscriber.subscribe(PlayerDeathEvent.class, EventPriority.NORMAL)
                      .handler(this::onDeath); 
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 1) {
            getPlayerOrSendMessage(player, target -> revivePlayer(player, target), args[0]);
        } else {
            ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.MODERATION);
            messenger.sendMessage(player, "Revive-Usage");
        }
    }

    private void revivePlayer(Player reviver, Player target) {
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.MODERATION);
        Optional<List<ItemStack>> targetItems = Optional.ofNullable(items.remove(target));
        List<ItemStack> allItems = targetItems.orElse(List.of());
        allItems.forEach(target.getInventory()::addItem);
        String path = allItems.isEmpty() ? "Revive-Failure" : "Success-Revive-Message";
        if (target.equals(reviver)) {
            messenger.sendMessage(reviver, path);
        } else {
            messenger.sendMessage(target, path);
            messenger.sendMessage(reviver, path);
        }
    }

    public boolean onDeath(PlayerDeathEvent e) {
        List<ItemStack> currentPendingItems = items.getOrDefault(e.getEntity(), new ArrayList<>());
        currentPendingItems.addAll(e.getDrops());        
        items.put(e.getEntity(), currentPendingItems);
        return false;
    }
}

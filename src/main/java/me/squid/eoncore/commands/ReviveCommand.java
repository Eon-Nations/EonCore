package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.eonnations.eonpluginapi.api.Command;
import org.eonnations.eonpluginapi.events.EventSubscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.vavr.control.Option;

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
            Option.of(Bukkit.getPlayer(args[0]))
                .map(target -> revivePlayer(player, target))
                .onEmpty(() -> Messaging.sendNullMessage(player));
        } else {
            ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.MODERATION);
            messenger.sendMessage(player, "Revive-Usage");
        }
    }

    private Player revivePlayer(Player reviver, Player target) {
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.MODERATION);
        Option<List<ItemStack>> targetItems = Option.of(items.remove(target));
        List<ItemStack> allItems = targetItems.getOrElse(List.of());
        allItems.forEach(target.getInventory()::addItem);
        String path = allItems.isEmpty() ? "Revive-Failure" : "Success-Revive-Message";
        if (target.equals(reviver)) {
            messenger.sendMessage(reviver, path);
        } else {
            messenger.sendMessage(target, path);
            messenger.sendMessage(reviver, path);
        }
        return target;
    }

    public boolean onDeath(PlayerDeathEvent e) {
        List<ItemStack> currentPendingItems = items.getOrDefault(e.getEntity(), new ArrayList<>());
        currentPendingItems.addAll(e.getDrops());        
        items.put(e.getEntity(), currentPendingItems);
        return false;
    }
}

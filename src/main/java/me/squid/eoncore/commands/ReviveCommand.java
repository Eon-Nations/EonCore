package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

import static me.squid.eoncore.utils.FunctionalBukkit.getPlayerOrSendMessage;

@RegisterCommand
public class ReviveCommand extends EonCommand implements Listener {
    private final HashMap<Player, List<ItemStack>> items = new HashMap<>();

    public ReviveCommand(EonCore plugin) {
        super("revive", plugin);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        getPlayerOrSendMessage(player, target -> revivePlayer(player, target), args[0]);
    }

    private void revivePlayer(Player reviver, Player target) {
        List<ItemStack> targetItems = items.remove(target);
        targetItems.forEach(target.getInventory()::addItem);
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.MODERATION);
        messenger.sendMessage(target, "Target-Success-Revive-Message");
        messenger.sendMessage(reviver, "Success-Revive-Message");
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        items.put(e.getEntity(), e.getDrops());
        Location deathLocation = e.getEntity().getLocation();
        // Log the death location in case a bug happens and further investigation should be added
        core.getLogger().info("Player died at: x=" + deathLocation.getX() + " y=" + deathLocation.getY() +
                " z=" + deathLocation.getZ() + " world=" + deathLocation.getWorld().getName());
    }
}

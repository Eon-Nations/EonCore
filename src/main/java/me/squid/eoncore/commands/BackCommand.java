package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashMap;
import java.util.Optional;

@RegisterCommand
public class BackCommand extends EonCommand implements Listener {
    private final HashMap<Player, Location> backLocations = new HashMap<>();

    public BackCommand(EonCore plugin) {
        super("back", plugin);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void execute(Player player, String[] args) {
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.NATIONS);
        Optional<Location> toTeleport = Optional.ofNullable(backLocations.remove(player));
        toTeleport.ifPresentOrElse(location -> teleport.delayedTeleport(player, location),
                () -> messenger.sendMessage(player, "No-Back"));
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (!e.getEntity().getWorld().equals(Bukkit.getWorld("spawn_void"))) {
            backLocations.put(e.getEntity(), e.getEntity().getLocation());
        }
        if (e.getEntity().isOp()) {
            e.setKeepInventory(true);
            e.getDrops().clear();
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.NATIONS);
        messenger.sendMessage(e.getPlayer(), "Death-Back-Message");
        e.getPlayer().teleportAsync(Utils.getSpawnLocation());
    }
}

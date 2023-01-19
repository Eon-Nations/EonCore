package me.squid.eoncore.utils;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.messaging.Messenger;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.function.Consumer;

public class Teleport {
    EonCore core;
    ConfigMessenger messenger;

    public Teleport(EonCore core) {
        this.core = core;
        this.messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.NATIONS);
    }

    private static final String IMMUNE_COOLDOWN = "eoncommands.teleport.immune";

    public void delayedTeleport(Player player, Location toTeleport, String configPath) {
        boolean noCooldown = !player.hasPermission(IMMUNE_COOLDOWN);
        if (noCooldown) {
            Messenger otherMessenger = Messaging.messenger(EonPrefix.NATIONS);
            long delaySeconds = core.getConfig().getLong("Delay-Seconds");
            String formatMessage = formatMessage(core, delaySeconds);
            Component message = Messaging.fromFormatString(formatMessage);
            otherMessenger.send(player, message);
            scheduleTeleport(player, toTeleport, delaySeconds, configPath);
        } else {
            teleport(player, toTeleport, configPath);
        }
    }

    private static String formatMessage(EonCore core, long delaySeconds) {
        return Optional.ofNullable(core.getConfig().getString("Cooldown-Teleport-Message"))
                .orElse("Teleporting in <seconds>")
                .replace("<seconds>", Long.toString(delaySeconds));
    }

    private void scheduleTeleport(Player player, Location toTeleport, long delay, String configPath) {
        Bukkit.getScheduler().runTaskLater(core, () -> teleport(player, toTeleport, configPath), delay * 20L);
    }

    public void teleport(Player player, Location toTeleport, String configPath) {
        Consumer<Boolean> sender = sendTeleportMessage(player, configPath);
        player.teleportAsync(toTeleport).thenAccept(sender);
    }

    private Consumer<Boolean> sendTeleportMessage(Player player, String configPath) {
        return successful -> {
            if (Boolean.TRUE.equals(successful)) messenger.sendMessage(player, configPath);
            else messenger.sendMessage(player, "Teleport-Failed");
        };
    }
}

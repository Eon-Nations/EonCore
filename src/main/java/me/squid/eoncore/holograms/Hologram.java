package me.squid.eoncore.holograms;

import me.squid.eoncore.EonCore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitTask;

import static me.squid.eoncore.messaging.Messaging.fromFormatString;

public class Hologram implements AutoCloseable {
    private final ArmorStand stand;
    private final BukkitTask task;
    private int secondsLeft = 5;

    public Hologram(String line, Location location) {
        this.stand = location.getWorld().spawn(location, ArmorStand.class);
        stand.setInvisible(true);
        stand.setInvulnerable(true);
        stand.setCustomNameVisible(true);
        stand.customName(getName(line));
        EonCore plugin = (EonCore) Bukkit.getPluginManager().getPlugin("EonCore");
        task = Bukkit.getScheduler().runTaskTimer(plugin, this::countdown, 0L, 20L);
    }

    private Component getName(String line) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        return miniMessage.deserialize(line + " <green><bold><seconds> seconds</bold></green>", Placeholder.unparsed("seconds", String.valueOf(secondsLeft)));
    }

    private void countdown() {
        secondsLeft--;
        Component text = stand.customName().replaceText(TextReplacementConfig.builder().match("\\d+").replacement(String.valueOf(secondsLeft)).build());
        stand.customName(text);
        if (secondsLeft == 0) {
            task.cancel();
            close();
        }
    }

    @Override
    public void close() {
        stand.remove();
    }
}

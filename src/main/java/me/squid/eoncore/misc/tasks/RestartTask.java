package me.squid.eoncore.misc.tasks;

import me.squid.eoncore.misc.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RestartTask {
    private RestartTask() { }

    public static void runRestartTask(JavaPlugin core) {
        Server server = Bukkit.getServer();
        Runnable messageTask = () -> server.sendMessage(warningMessage());
        Runnable restartTask = () -> {
            server.sendMessage(restartingMessage());
            Bukkit.getScheduler().runTaskLater(core, Bukkit::shutdown, 40L);
        };

        runScheduledTask(messageTask, true);
        runScheduledTask(restartTask, false);
    }

    private static void runScheduledTask(Runnable runnable, boolean messageTask) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
        ZonedDateTime restartTime = now.withHour(4).withMinute(0).withSecond(0);

        if (now.compareTo(restartTime) > 0)
            restartTime = restartTime.plusDays(1);

        if (messageTask)
            restartTime = restartTime.minusMinutes(5);

        Duration duration = Duration.between(now, restartTime);
        long delay = duration.getSeconds();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(runnable, delay, TimeUnit.SECONDS);
    }

    private static Component warningMessage() {
        return Component.text(Utils.getPrefix("nations") + Utils.chat("Restarting Server in 5 minutes!"));
    }

    private static Component restartingMessage() {
        return Component.text(Utils.getPrefix("nations") + Utils.chat("Restarting server!"));
    }
}

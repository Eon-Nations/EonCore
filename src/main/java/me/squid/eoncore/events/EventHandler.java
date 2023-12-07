package me.squid.eoncore.events;

import io.vavr.Function1;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.bukkit.event.*;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.PluginManager;
import org.eonnations.eonpluginapi.EonPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public class EventHandler<T extends Event & Cancellable> implements EventExecutor, Listener {
    private final Class<T> eventClass;
    private final EventPriority priority;
    private final Function1<Throwable, Boolean> exceptionHandler;
    private final List<Function1<T, Boolean>> filters;
    private final Function1<T, Boolean> handler;

    public EventHandler(EonPlugin plugin, Class<T> eventClass, EventPriority priority, List<Function1<T, Boolean>> filters, Function1<Throwable, Boolean> exceptionHandler, Function1<T, Boolean> handler) {
        this.eventClass = eventClass;
        this.priority = priority;
        this.filters = filters;
        this.exceptionHandler = exceptionHandler;
        this.handler = handler;
        registerEventWithBukkit(plugin);
    }

    private void registerEventWithBukkit(EonPlugin plugin) {
        PluginManager manager = plugin.getServer().getPluginManager();
        manager.registerEvent(eventClass, this, priority, this, plugin, false);
    }

    @Override
    public void execute(@NotNull Listener listener, @NotNull Event event) throws EventException {
        if (!event.getClass().equals(eventClass)) {
            return;
        }

        T eventInstance = eventClass.cast(event);
        boolean passAllChecks = Try.of(() -> filters.map(f -> f.apply(eventInstance))
                .forAll(b -> b))
                .getOrElse(false);
        if (!passAllChecks) return;

        boolean isCancelled = Try.of(() -> handler.apply(eventInstance))
                .recover(exceptionHandler)
                .get();
        if (isCancelled) {
            cancelEvent(eventInstance).peekLeft(Exception::printStackTrace);
        }
    }

    private Either<Exception, Boolean> cancelEvent(T event) {
        try {
            Method method = event.getClass().getMethod("setCancelled", boolean.class);
            method.invoke(event, true);
            return Either.right(true);
        } catch (Exception e) {
            return Either.left(e);
        }
    }
}

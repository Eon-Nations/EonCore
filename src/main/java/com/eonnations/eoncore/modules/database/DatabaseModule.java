package com.eonnations.eoncore.modules.database;

import com.eonnations.eoncore.EonCore;
import com.eonnations.eoncore.common.EonModule;
import com.eonnations.eoncore.common.events.EventHandler;
import com.eonnations.eoncore.common.events.EventSubscriber;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

public class DatabaseModule extends EonModule {

    private EventHandler<PlayerJoinEvent> joinHandler;

    public DatabaseModule(EonCore plugin) {
        super(plugin, LoadOrder.MOST);
    }

    @Override
    public void load() {

    }

    @Override
    public void setup() {
        joinHandler = EventSubscriber.subscribe(PlayerJoinEvent.class, EventPriority.NORMAL)
                .handler(event -> {
                    plugin.getDatabase().createPlayer(event.getPlayer().getUniqueId(), event.getPlayer().getName());
                    return false;
                });
    }

    @Override
    public void cleanup() {
        joinHandler.close();
    }

    @Override
    public String name() {
        return "Database";
    }
}

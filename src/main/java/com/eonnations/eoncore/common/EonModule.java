package com.eonnations.eoncore.common;

import com.eonnations.eoncore.EonCore;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public abstract class EonModule implements Comparable<EonModule> {

    public enum LoadOrder {
        BEFORE_ALL,
        MOST,
        AFTER_ALL
    }

    protected EonCore plugin;
    @Getter
    protected LoadOrder loadOrder;
    // TODO: Add object that can register menu listeners
    
    protected EonModule(EonCore plugin, LoadOrder loadOrder) {
        this.plugin = plugin;
        this.loadOrder = loadOrder;
    }

    public abstract void load();
    public abstract void setup();
    public abstract void cleanup();

    public abstract String name();

    @Override
    public int compareTo(@NotNull EonModule other) {
        return loadOrder.compareTo(other.getLoadOrder());
    }
}

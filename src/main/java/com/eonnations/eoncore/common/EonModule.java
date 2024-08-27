package com.eonnations.eoncore.common;

import com.eonnations.eoncore.EonCore;

public abstract class EonModule {

    protected EonCore plugin;
    // TODO: Add object that can register menu listeners
    
    protected EonModule(EonCore plugin) {
        this.plugin = plugin;
    }

    public abstract void load();
    public abstract void setup();
    public abstract void cleanup();

    public abstract String name();
}

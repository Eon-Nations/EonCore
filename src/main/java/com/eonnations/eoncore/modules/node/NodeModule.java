package com.eonnations.eoncore.modules.node;

import com.eonnations.eoncore.EonCore;
import com.eonnations.eoncore.common.EonModule;

public class NodeModule extends EonModule {
    
    public NodeModule(EonCore plugin) {
        super(plugin, LoadOrder.MOST);
    }

    @Override
    public void load() { }

    @Override
    public void setup() {
        Node.registerChestPlaceListener();         
    }

    @Override
    public void cleanup() {

    }

    @Override
    public String name() {
        return "Node";
    }
}

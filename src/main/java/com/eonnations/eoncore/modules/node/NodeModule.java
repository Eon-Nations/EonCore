package com.eonnations.eoncore.modules.node;

import com.eonnations.eoncore.EonCore;
import com.eonnations.eoncore.common.EonModule;
import com.eonnations.eoncore.modules.node.holograms.Hologram;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import io.vavr.collection.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.Arrays;

public class NodeModule extends EonModule {
    
    public NodeModule(EonCore plugin) {
        super(plugin, LoadOrder.MOST);
    }

    @Override
    public void load() { }

    @Override
    public void setup() {
        Node.registerChestPlaceListener();
        new CommandAPICommand("hologram")
                .withPermission("eoncore.admin")
                .withSubcommand(new CommandAPICommand("new")
                        .withArguments(new GreedyStringArgument("lines"))
                        .executesPlayer((sender, args) -> {
                            String rawText = args.getByArgument(new GreedyStringArgument("lines"));
                            List<Component> lines = List.ofAll(Arrays.stream(rawText.split("\\|")))
                                    .map(MiniMessage.miniMessage()::deserialize);
                            Hologram hologram = new Hologram(lines, sender.getLocation());
                        }))
                .register(plugin);
    }

    @Override
    public void cleanup() {

    }

    @Override
    public String name() {
        return "Node";
    }
}

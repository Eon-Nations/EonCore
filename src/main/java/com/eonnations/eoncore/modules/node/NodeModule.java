package com.eonnations.eoncore.modules.node;

import com.eonnations.eoncore.EonCore;
import com.eonnations.eoncore.common.EonModule;
import com.eonnations.eoncore.modules.node.holograms.Hologram;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import io.vavr.collection.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;

import java.util.Arrays;

public class NodeModule extends EonModule {
    
    public NodeModule(EonCore plugin) {
        super(plugin, LoadOrder.MOST);
    }

    @Override
    public void load() {
        // Nothing to do here
    }

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
                            new Hologram(lines, sender.getLocation());
                        }))
                .register(plugin);
        List<String> resources = List.ofAll(Arrays.stream(Resource.values()))
                .map(Resource::toString)
                .map(String::toLowerCase);
        new CommandAPICommand("node")
                .withPermission("eoncore.admin")
                .withSubcommand(new CommandAPICommand("add")
                        .withArguments(new StringArgument("resource")
                                .replaceSuggestions(ArgumentSuggestions.strings(resources.toJavaList())))
                        .executesPlayer((sender, args) -> {
                            String resource = args.getByClass("resource", String.class);
                            new Node(sender.getLocation(), Resource.valueOf(resource.toUpperCase()));
                        }))
                .withSubcommand(new CommandAPICommand("remove")
                        .executesPlayer((sender, args) -> {
                            for (ArmorStand stand : sender.getWorld().getNearbyEntitiesByType(ArmorStand.class, sender.getLocation(), 10.0)) {
                                stand.remove();
                            }
                            for (Item item : sender.getWorld().getNearbyEntitiesByType(Item.class, sender.getLocation(), 10.0)) {
                                if (item.hasNoPhysics()) {
                                    item.remove();
                                }
                            }
                        }))
                .register(plugin);
    }

    @Override
    public void cleanup() {
        // Nothing to clean up
    }

    @Override
    public String name() {
        return "Node";
    }
}

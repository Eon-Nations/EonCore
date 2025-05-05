package com.eonnations.eoncore.modules.worldgen;

import com.eonnations.eoncore.EonCore;
import com.eonnations.eoncore.common.EonModule;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.WorldCreator;

public class WorldGenModule extends EonModule {

    public WorldGenModule(EonCore plugin) {
        super(plugin, LoadOrder.MOST);
    }

    @Override
    public void load() {

    }

    @Override
    public void setup() {
        new CommandAPICommand("worldgen")
                .withSubcommand(new CommandAPICommand("generate")
                        .withArguments(new StringArgument("name"))
                        .executesPlayer((sender, args) -> {
                            new WorldCreator(args.getByClass("name", String.class))
                                    .generator(new IslandGenerator())
                                    .createWorld();
                        }))
                .register(plugin);
    }

    @Override
    public void cleanup() {

    }

    @Override
    public String name() {
        return "WorldGen";
    }
}

package me.squid.eoncore.commands;

import io.vavr.collection.List;
import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.holograms.Hologram;
import me.squid.eoncore.messaging.Messaging;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Command;

import java.util.Arrays;

@Command(name = "hologram", usage = "/hologram <name>")
public class HologramCommand extends EonCommand {

    public HologramCommand(EonCore plugin) {
        super(plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        String joinedArgs = String.join(" ", args);
        String[] rawLines = joinedArgs.split("\\|");
        List<Component> lines = List.ofAll(Arrays.stream(rawLines))
                .map(Messaging::fromFormatString);
        Hologram hologram = new Hologram(lines, player.getLocation());
    }
}

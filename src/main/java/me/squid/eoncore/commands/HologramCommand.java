package me.squid.eoncore.commands;

import io.vavr.collection.List;
import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.holograms.Hologram;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Command;

@Command(name = "hologram", usage = "/hologram <name>")
public class HologramCommand extends EonCommand {
    List<Hologram> list = List.of();

    public HologramCommand(EonCore plugin) {
        super(plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        Hologram hologram = new Hologram(args[0], player.getLocation());
        list = list.append(hologram);
    }
}

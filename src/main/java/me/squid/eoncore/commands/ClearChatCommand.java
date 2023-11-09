package me.squid.eoncore.commands;

import io.vavr.collection.List;
import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Alias;
import org.eonnations.eonpluginapi.api.Command;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Command(name = "clearchat",
        usage = "/cc",
        aliases = {@Alias(name = "cc")},
        permission = "eoncommands.clearchat",
        description = "Clears the chat")
public class ClearChatCommand extends EonCommand {

    public ClearChatCommand(EonCore plugin) {
        super(plugin);
    }

    public void execute(Player player, String[] args) {
        List<Player> normalPlayers = List.ofAll(player.getServer().getOnlinePlayers().stream()
                        .filter(online -> !online.hasPermission("eoncommands.clearchat"))
                        .collect(Collectors.toList()));
        Audience audience = Audience.audience(normalPlayers);
        IntStream.range(0, 25).forEach(i -> audience.sendMessage(Component.text("")));
    }

}

package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.messaging.Messenger;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Alias;
import org.eonnations.eonpluginapi.api.Command;

import io.vavr.control.Option;

@Command(name = "gamemodecheck",
        usage = "/gamemodecheck <player>",
        aliases = @Alias(name = "gmcheck"),
        permission = "eoncommands.gamemodecheck")
public class GamemodeCheckCommand extends EonCommand {

    public GamemodeCheckCommand(EonCore plugin) {
        super(plugin);
    }

    private Player sendGamemodeMessage(Player sender, Player target) {
        String gamemodeMessage = core.getConfig().getString("GamemodeCheck-Message")
                .replace("<player>", target.getName())
                .replace("<gamemode>", StringUtils.capitalize(target.getGameMode().toString().toLowerCase()));
        Component message = Messaging.fromFormatString(gamemodeMessage);
        Messenger messenger = Messaging.messenger(EonPrefix.MODERATION);
        messenger.send(sender, message);
        return sender;
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 1) {
            Option.of(Bukkit.getPlayer(args[0]))
                .map(target -> sendGamemodeMessage(player, target))
                .onEmpty(() -> Messaging.sendNullMessage(player));
        } else {
            Messenger messenger = Messaging.messenger(EonPrefix.MODERATION);
            messenger.send(player, Component.text("Usage: /gmcheck <player>"));
        }
    }
}

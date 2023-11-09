package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.utils.FunctionalBukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Command;

@Command(name = "gmc",
        usage = "/gmc",
        permission = "eoncommands.gmc")
public class CreativeCommand extends EonCommand {
    static final String OTHERS_NODE = "eoncommands.gmc.others";

    public CreativeCommand(EonCore plugin) {
        super(plugin);
    }

    private void setPlayerToCreative(Player p) {
        p.setGameMode(GameMode.CREATIVE);
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.MODERATION);
        messenger.sendMessage(p, "Creative-Message");
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 0) {
            setPlayerToCreative(player);
        } else if (args.length == 1 && player.hasPermission(OTHERS_NODE)) {
            FunctionalBukkit.getPlayerOrSendMessage(player, this::setPlayerToCreative, args[0]);
        }
    }
}

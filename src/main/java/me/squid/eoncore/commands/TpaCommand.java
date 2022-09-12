package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.messaging.Messenger;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static me.squid.eoncore.messaging.Messaging.fromFormatString;
import static me.squid.eoncore.utils.FunctionalBukkit.getPlayerOrSendMessage;

@RegisterCommand
public class TpaCommand extends EonCommand {

    private static final Map<UUID, UUID> requests = new HashMap<>();

    public TpaCommand(EonCore plugin) {
        super("tpa", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 1) {
            getPlayerOrSendMessage(player, target -> makeRequest(player, target), args[0]);
        } else {
            Messenger messenger = Messaging.messenger(EonPrefix.NATIONS);
            String rawUsage = "<gray>Usage: /tpa <player></gray>";
            messenger.send(player, fromFormatString(rawUsage));
        }
    }

    private void makeRequest(Player player, Player target) {
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.NATIONS);
        UUID initialTarget = requests.getOrDefault(player.getUniqueId(), target.getUniqueId());
        if (initialTarget.equals(player.getUniqueId())) {
            messenger.sendMessage(player, "Pending-Request");
        } else {
            requests.put(player.getUniqueId(), target.getUniqueId());
            messenger.sendMessage(player, "Tpa-Player-Message");
            String rawTargetMessage = core.getConfig().getString("Tpa-Target-Message")
                    .replace("<player>", player.getName());
            Messenger otherMessenger = Messaging.messenger(EonPrefix.NATIONS);
            otherMessenger.send(target, fromFormatString(rawTargetMessage));
        }
    }

    public static Map<UUID, UUID> requests() {
        return requests;
    }
}

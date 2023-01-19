package me.squid.eoncore.misc.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

@RegisterCommand
public class TpDenyCommand extends EonCommand {

    public TpDenyCommand(EonCore plugin) {
        super("tpdeny", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        Map<UUID, UUID> requests = TpaCommand.requests();
        var players = requests.entrySet().stream()
                .filter(entry -> entry.getValue().equals(player.getUniqueId()))
                .findFirst();
        players.ifPresentOrElse(entry -> cancelTp(player, entry.getValue()),
                () -> sendNoPendingRequests(player));
    }

    private void cancelTp(Player player, UUID denied) {
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.NATIONS);
        Map<UUID, UUID> requests = TpaCommand.requests();
        requests.remove(denied);
        messenger.sendMessage(player, "Teleport-Deny-Message");
        Player theDenied = Bukkit.getPlayer(denied);
        messenger.sendMessage(theDenied, "Teleport-Deny-Message");
    }

    private void sendNoPendingRequests(Player player) {
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.NATIONS);
        messenger.sendMessage(player, "No-Pending-Requests");
    }
}

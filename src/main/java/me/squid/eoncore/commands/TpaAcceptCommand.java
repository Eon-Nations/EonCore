package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RegisterCommand
public class TpaAcceptCommand extends EonCommand {

    public TpaAcceptCommand(EonCore plugin) {
        super("tpaccept", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        Map<UUID, UUID> requests = TpaCommand.requests();
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.NATIONS);
        if (requests.containsKey(player.getUniqueId())) {
            UUID targetUUID = requests.remove(player.getUniqueId());
            Optional<Player> maybeTarget = Optional.ofNullable(Bukkit.getPlayer(targetUUID));
            maybeTarget.ifPresentOrElse(target -> teleportPlayer(player, target),
                    () -> Messaging.sendNullMessage(player));
        } else {
            messenger.sendMessage(player, "No-Pending-Requests");
        }
    }

    private void teleportPlayer(Player player, Player target) {
        teleport.delayedTeleport(player, target.getLocation(), "Teleport-Successful");
    }
}

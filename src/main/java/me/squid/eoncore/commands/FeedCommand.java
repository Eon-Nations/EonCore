package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.managers.Cooldown;
import me.squid.eoncore.managers.CooldownManager;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.utils.FunctionalBukkit;
import org.bukkit.entity.Player;

import java.util.Optional;

@RegisterCommand
public class FeedCommand extends EonCommand {
    CooldownManager cooldownManager;
    static final String OTHERS_IMMUNE_NODE = "eoncommands.feed.others";
    static final String IMMUNE_COOLDOWN_NODE = "eoncommands.feed.cooldown.immune";

    public FeedCommand(EonCore plugin) {
        super("feed", plugin);
        cooldownManager = new CooldownManager();
    }

    private void feedPlayer(Player player, ConfigMessenger messenger) {
        final int MAX_FOOD = 20;
        final int MAX_SATURATION = 14;
        player.setFoodLevel(MAX_FOOD);
        player.setSaturation(MAX_SATURATION);
        messenger.sendMessage(player, "Feed-Message");
    }

    private void applyCooldown(Player player) {
        if (!player.hasPermission(IMMUNE_COOLDOWN_NODE)) {
            int minutes = player.hasPermission("eoncommands.feed.5") ? 5 : 10;
            final long SECONDS_TO_MILLISECONDS = 60L * 1000L;
            long totalLength = minutes * SECONDS_TO_MILLISECONDS;
            Cooldown cooldown = new Cooldown(player.getUniqueId(), totalLength, System.currentTimeMillis());
            cooldownManager.add(cooldown);
        }
    }

    @Override
    protected void execute(Player player, String[] args) {
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.NATIONS);
        if (args.length == 0) {
            if (cooldownManager.hasCooldown(player.getUniqueId())) {
                messenger.sendMessage(player, "Feed-Cooldown-Message");
            } else {
                feedPlayer(player, messenger);
                applyCooldown(player);
            }
        } else if (args.length == 1 && player.hasPermission(OTHERS_IMMUNE_NODE)) {
            Optional<Player> maybeTarget = FunctionalBukkit.getPlayerFromName(args[0]);
            maybeTarget.ifPresentOrElse(target -> feedPlayer(target, messenger),
                    () -> Messaging.sendNullMessage(player));
        }
    }
}

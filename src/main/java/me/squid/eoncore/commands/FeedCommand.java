package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.managers.Cooldown;
import me.squid.eoncore.managers.CooldownManager;
import me.squid.eoncore.utils.FunctionalBukkit;
import me.squid.eoncore.messaging.Messaging;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class FeedCommand implements CommandExecutor {

    EonCore plugin;
    CooldownManager cooldownManager;
    static final String OTHERS_IMMUNE_NODE = "eoncommands.feed.others";
    static final String IMMUNE_COOLDOWN_NODE = "eoncommands.feed.cooldown.immune";

    public FeedCommand(EonCore plugin) {
        this.plugin = plugin;
        cooldownManager = new CooldownManager();
        plugin.getCommand("feed").setExecutor(this);
    }

    private void feedPlayer(Player player) {
        final int MAX_FOOD = 20;
        final int MAX_SATURATION = 14;
        player.setFoodLevel(MAX_FOOD);
        player.setSaturation(MAX_SATURATION);
        Messaging.sendNationsMessage(player, plugin.getConfig().getString("Feed-Message"));
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
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            if (cooldownManager.hasCooldown(p.getUniqueId())) {
                Messaging.sendNationsMessage(p, plugin.getConfig().getString("Feed-Cooldown-message"));
                return false;
            }
            feedPlayer(p);
            applyCooldown(p);
        } else if (args.length == 1 && p.hasPermission(OTHERS_IMMUNE_NODE)) {
            Optional<Player> maybeTarget = FunctionalBukkit.getPlayerFromName(args[0]);
            maybeTarget.ifPresentOrElse(this::feedPlayer, () -> Messaging.sendNullMessage(p));
            return true;
        }
        return false;
    }
}

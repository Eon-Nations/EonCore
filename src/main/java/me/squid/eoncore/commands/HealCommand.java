package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.managers.Cooldown;
import me.squid.eoncore.managers.CooldownManager;
import me.squid.eoncore.utils.FunctionalBukkit;
import me.squid.eoncore.utils.Messaging;
import me.squid.eoncore.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class HealCommand implements CommandExecutor {

    EonCore plugin;
    final CooldownManager cooldownManager = new CooldownManager();
    static final String OTHERS_PERMISSION = "eoncommands.heal.others";
    static final long HEAL_COOLDOWN_MINUTES = 60L;

    public HealCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("heal").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 0) {
                if (addCooldownToPlayer(p)) {
                    healPlayer(p);
                }
            } else if (p.hasPermission(OTHERS_PERMISSION) && args.length == 1) {
                FunctionalBukkit.getPlayerOrSendMessage(p, this::healPlayer, args[0]);
                Messaging.sendNationsMessage(p, args[0] + " has been successfully healed!");
            }
        }
        return true;
    }

    private boolean addCooldownToPlayer(Player player) {
        if (player.isOp()) return true;
        if (cooldownManager.hasCooldown(player.getUniqueId())) {
            Cooldown cooldown = cooldownManager.getCooldown(player.getUniqueId());
            Messaging.sendNationsMessage(player, "You can do /heal in " + Utils.getFormattedTimeString(cooldown.getTimeRemaining()));
            return false;
        } else {
            Cooldown cooldown = new Cooldown(player.getUniqueId(), HEAL_COOLDOWN_MINUTES * 60L * 1000L, System.currentTimeMillis());
            cooldownManager.add(cooldown);
            return true;
        }
    }

    private void healPlayer(Player player) {
        Optional<String> message = Optional.ofNullable(plugin.getConfig().getString("Heal-Message"));
        String healMessage = message.orElse("Healed!");
        player.setHealth(20);
        player.setFoodLevel(20);
        Messaging.sendNationsMessage(player, healMessage);
    }

}

package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.managers.Cooldown;
import me.squid.eoncore.managers.CooldownManager;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.messaging.Messenger;
import me.squid.eoncore.utils.FunctionalBukkit;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

@RegisterCommand
public class HealCommand extends EonCommand {
    final CooldownManager cooldownManager = new CooldownManager();
    static final String OTHERS_PERMISSION = "eoncommands.heal.others";
    static final long HEAL_COOLDOWN_MINUTES = 60L;

    public HealCommand(EonCore plugin) {
        super("hat", plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 0) {
            if (addCooldownToPlayer(player)) {
                healPlayer(player);
            }
        } else if (player.hasPermission(OTHERS_PERMISSION) && args.length == 1) {
            FunctionalBukkit.getPlayerOrSendMessage(player, this::healPlayer, args[0]);
        }
    }

    private boolean addCooldownToPlayer(Player player) {
        if (player.isOp()) return true;
        if (cooldownManager.hasCooldown(player.getUniqueId())) {
            Cooldown cooldown = cooldownManager.getCooldown(player.getUniqueId());
            String rawHealMessage = core.getConfig().getString("Heal-Cooldown")
                    .replace("<time>", Utils.getFormattedTimeString(cooldown.getTimeRemaining()));
            Component healMessage = Messaging.fromFormatString(rawHealMessage);
            Messenger messenger = Messaging.messenger(EonPrefix.NATIONS);
            messenger.send(player, healMessage);
            return false;
        } else {
            final long FULL_HEAL_COOLDOWN = HEAL_COOLDOWN_MINUTES * 60L * 1000L;
            Cooldown cooldown = new Cooldown(player.getUniqueId(), FULL_HEAL_COOLDOWN, System.currentTimeMillis());
            cooldownManager.add(cooldown);
            return true;
        }
    }

    private void healPlayer(Player player) {
        player.setHealth(20);
        player.setFoodLevel(20);
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.NATIONS);
        messenger.sendMessage(player, "Heal-Message");
    }

}

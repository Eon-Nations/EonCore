package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.eonnations.eonpluginapi.api.Alias;
import org.eonnations.eonpluginapi.api.Command;
import io.vavr.control.Option;

@Command(name = "nightvision", usage = "/nightvision <player>", aliases = {@Alias(name = "nv")}, permission = "eoncommands.nightvision")
public class NightVisionCommand extends EonCommand {
    static final String OTHERS_IMMUNE_NODE = "eoncommands.nightvision.others";

    public NightVisionCommand(EonCore plugin) {
        super(plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 0) {
            toggleNightVision(player);
        } else if (args.length == 1 && player.hasPermission(OTHERS_IMMUNE_NODE)) {
            Option<Player> targetOpt = Option.of(Bukkit.getPlayer(args[0]))
                .peek(this::toggleNightVision);
            if (targetOpt.isEmpty()) {
                Messaging.sendNullMessage(player);
                return;
            }
            ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.MODERATION);
            messenger.sendMessage(player, "Target-NV-Message");
        }
    }

    private void toggleNightVision(Player player) {
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.MODERATION);
        if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            messenger.sendMessage(player, "NV-Off");
        } else {
            PotionEffect nightVision = new PotionEffect(PotionEffectType.NIGHT_VISION, Short.MAX_VALUE, 1, false, false);
            player.addPotionEffect(nightVision);
            messenger.sendMessage(player, "NV-Message");
        }
    }
}

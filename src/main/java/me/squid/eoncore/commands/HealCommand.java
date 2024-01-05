package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.misc.managers.CooldownManager;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Command;

import io.vavr.control.Option;

@Command(name = "heal", usage = "/heal <player>", permission = "eoncommands.heal")
public class HealCommand extends EonCommand {
    final CooldownManager cooldownManager = new CooldownManager();
    static final String OTHERS_PERMISSION = "eoncommands.heal.others";
    static final long HEAL_COOLDOWN_MINUTES = 60L;

    public HealCommand(EonCore plugin) {
        super(plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 0) {
            healPlayer(player);
        } else if (player.hasPermission(OTHERS_PERMISSION) && args.length == 1) {
            Option.of(Bukkit.getPlayer(args[0]))
                .map(this::healPlayer)
                .onEmpty(() -> Messaging.sendNullMessage(player));
        }
    }

    private Player healPlayer(Player player) {
        player.setHealth(20);
        player.setFoodLevel(20);
        ConfigMessenger messenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.NATIONS);
        messenger.sendMessage(player, "Heal-Message");
        return player;
    }

}

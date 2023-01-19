package me.squid.eoncore.misc.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.misc.managers.Cooldown;
import me.squid.eoncore.misc.managers.CooldownManager;
import me.squid.eoncore.messaging.ConfigMessenger;
import me.squid.eoncore.messaging.EonPrefix;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.messaging.Messenger;
import me.squid.eoncore.misc.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RegisterCommand
public class FixCommand extends EonCommand {
    // 10 Minutes converted to second converted to milliseconds
    static final long COOLDOWN_LENGTH = 10L * 60L * 1000L;
    final CooldownManager cooldownManager = new CooldownManager();

    public FixCommand(EonCore plugin) {
        super("fix", plugin);
        core.getCommand("fix").setTabCompleter(getTabComplete());
    }


    @Override
    protected void execute(Player player, String[] args) {
        Messenger messenger = Messaging.messenger(EonPrefix.NATIONS);
        ConfigMessenger configMessenger = Messaging.setupConfigMessenger(core.getConfig(), EonPrefix.NATIONS);
        if (cooldownManager.hasCooldown(player.getUniqueId())) {
            Cooldown cooldown = cooldownManager.getCooldown(player.getUniqueId());
            String timeRemaining = Utils.getFormattedTimeString(cooldown.getTimeRemaining());
            String formatString = core.getConfig().getString("Fix-Cooldown")
                            .replace("<time>", timeRemaining);
            Component cooldownMessage = Messaging.fromFormatString(formatString);
            messenger.send(player, cooldownMessage);
            return;
        }
        if (args.length == 1) {
            String hand = args[0];
            if (hand.equals("hand")) {
                fixItemInHand(player, configMessenger);
            } else if (hand.equals("all")) {
                fixAllItems(player, configMessenger);
            }
        } else player.sendMessage(Utils.chat(Utils.getPrefix("nations") + "&7Usage: /fix hand/all"));
    }

    private void fixAllItems(Player player, ConfigMessenger configMessenger) {
        if (!player.hasPermission("eoncommands.fix.all")) {
            configMessenger.sendMessage(player, "No-Perm-All-Fix");
            return;
        }
        ItemStack[] items = player.getInventory().getContents();
        long amountRepaired = Arrays.stream(items)
                .filter(Objects::nonNull)
                .filter(item -> !item.getType().equals(Material.AIR))
                .map(this::applyFix)
                .count();
        String formatString = core.getConfig().getString("Success-All-Fix")
                        .replace("<amount>", Long.toString(amountRepaired));
        Component successMessage = Messaging.fromFormatString(formatString);
        Messenger messenger = Messaging.messenger(EonPrefix.NATIONS);
        messenger.send(player, successMessage);
        addCooldownToPlayer(player);
    }

    private void fixItemInHand(Player player, ConfigMessenger messenger) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!applyFix(item)) {
            messenger.sendMessage(player, "No-Item-Fix");
        } else {
            messenger.sendMessage(player, "Success-Fix");
            addCooldownToPlayer(player);
        }
    }

    public void addCooldownToPlayer(Player p) {
        if (!p.hasPermission("eoncommands.fix.cooldown.immune")) {
            Cooldown cooldown = new Cooldown(p.getUniqueId(), COOLDOWN_LENGTH, System.currentTimeMillis());
            cooldownManager.add(cooldown);
        }
    }

    public boolean applyFix(ItemStack item) {
        Damageable damageable = (Damageable) item.getItemMeta();
        try {
            damageable.setDamage(0);
            item.setItemMeta(damageable);
            return true;
        }
        catch (NullPointerException e) {
            return false;
        }
    }

    public TabCompleter getTabComplete() {
        return ((sender, command, alias, args) -> List.of("hand", "all"));
    }
}

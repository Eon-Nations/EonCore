package me.squid.eoncore.kit;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.kit.kits.StarterKit;
import me.squid.eoncore.menus.KitGUI;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KitManager implements CommandExecutor {

    EonCore plugin;
    public static ArrayList<Kit> kits = new ArrayList<>();
    final String prefix = "&7[&b&lEonKits&r&7] &r";
    KitGUI kitGUI = new KitGUI();

    public KitManager(EonCore plugin) {
        this.plugin = plugin;
        kits.add(new StarterKit(plugin));
        Objects.requireNonNull(plugin.getCommand("kits")).setTabCompleter(getTabCompleter());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (args.length > 0) {
                for (Kit kit : kits) {
                    if (args[0].equalsIgnoreCase(kit.getName())) {
                        kit.giveKit(p);
                        p.sendMessage(Utils.chat(prefix + Objects.requireNonNull(plugin.getConfig().getString("Kit-Received-Message"))
                                .replace("<name>", kit.getName())));
                        break;
                    }
                }
            } else {
                p.openInventory(kitGUI.Select());
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
            }
        }
        return true;
    }

    public TabCompleter getTabCompleter() {
        return (sender, command, alias, args) -> {
            List<String> toReturn = new ArrayList<>();
            for (Kit kit : kits) {
                toReturn.add(kit.getName());
            }
            return toReturn;
        };
    }
}

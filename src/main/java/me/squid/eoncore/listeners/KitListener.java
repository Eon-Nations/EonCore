package me.squid.eoncore.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.kit.Kit;
import me.squid.eoncore.kit.KitManager;
import me.squid.eoncore.menus.KitGUI;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

@SuppressWarnings("ALL")
public class KitListener implements Listener {

    EonCore plugin;
    KitGUI kitGUI = new KitGUI();

    public KitListener(EonCore plugin) {
        this.plugin = plugin;
        //Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onKitClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equals(Utils.chat("&5&lEon Kits"))) {
            if (Objects.requireNonNull(e.getCurrentItem()).getType() == Material.ENCHANTED_BOOK) {
                String name = e.getCurrentItem().getItemMeta().getDisplayName();
                p.openInventory(kitGUI.ConfirmMenu(name));
            }
            e.setCancelled(true);
        }

        if (e.getView().getTitle().equals(Utils.chat("&a&lConfirm Kit"))) {
            String kitName = Objects.requireNonNull(Objects.requireNonNull(e.getClickedInventory()).getItem(13)).getItemMeta().getDisplayName();
            switch (e.getCurrentItem().getType()) {
                case EMERALD_BLOCK:
                    if (getKit(kitName) != null) {
                        getKit(kitName).giveKit(p);
                        p.sendMessage(Utils.chat(plugin.getConfig().getString("Kit-Received-Message")
                        .replace("<name>", kitName)));
                    } else {
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                        p.sendMessage(Utils.chat("Shit aint working"));
                    }
                    break;
                case BARRIER:
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
                    p.closeInventory();
                    break;
            }
            e.setCancelled(true);
        }
    }

    public Kit getKit(String name) {
        for (Kit kit : KitManager.kits) {
            if (name.equalsIgnoreCase(kit.getName())) {
                return kit;
            }
        }
        return null;
    }
}

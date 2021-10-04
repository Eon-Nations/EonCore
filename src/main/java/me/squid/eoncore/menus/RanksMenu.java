package me.squid.eoncore.menus;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class RanksMenu implements Listener {

    Inventory inv;

    public RanksMenu(EonCore plugin) {
        inv = getInventory();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onRankMenuClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory().equals(inv)) {
            e.setCancelled(true);
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
        }
    }

    // Only one for inventory listening purposes
    public Inventory getInv() { return inv; }

    private Inventory getInventory() {
        TextColor white = TextColor.color(255, 255, 255);
        Inventory inv = Bukkit.createInventory(null, 9, Component.text("Eon Ranks").color(TextColor.color(153, 0, 153)));

        Utils.createItem(inv, Material.LIGHT_BLUE_BANNER, 1, 2, Component.text("Traveler").color(TextColor.color(0, 255, 255)),
                Component.text("$2,500").color(white),
                Component.text("- +5 Chunks to Claim").color(white),
                Component.text("- Access to /hat").color(white),
                Component.text("- +1 Home").color(white));

        Utils.createItem(inv, Material.BLUE_BANNER, 1, 3, Component.text("Explorer").color(TextColor.color(0, 102, 204)),
                Component.text("$5,000").color(white),
                Component.text("- 1 Galactic Key").color(white),
                Component.text("- +5 Chunks to Claim").color(white),
                Component.text("- +1 Home").color(white));

        Utils.createItem(inv, Material.PURPLE_BANNER, 1, 4, Component.text("Ranger").color(TextColor.color(102, 0, 204)),
                Component.text("$10,000").color(white),
                Component.text("- 2 Galactic Keys").color(white),
                Component.text("- +10 Chunks to Claim").color(white),
                Component.text("- /feed (10 Minute Cooldown)").color(white),
                Component.text("- +1 Home").color(white));

        Utils.createItem(inv, Material.YELLOW_BANNER, 1, 5, Component.text("Hero").color(TextColor.color(255, 255, 0)),
                Component.text("$25,000").color(white),
                Component.text("- 3 Galactic Keys").color(white),
                Component.text("- +20 Chunks to Claim").color(white),
                Component.text("- /feed (5 minute cooldown)").color(white),
                Component.text("- /ptime (Player Time)").color(white),
                Component.text("- +2 Homes").color(white));

        Utils.createItem(inv, Material.ORANGE_BANNER, 1, 6, Component.text("Legend").color(TextColor.color(255, 128, 0)),
                Component.text("$50,000").color(white),
                Component.text("- 6 Galactic Keys").color(white),
                Component.text("- +50 Chunks to Claim").color(white),
                Component.text("- /pweather (Player Weather)").color(white),
                Component.text("- /fix (60 Minute Cooldown)").color(white),
                Component.text("- +2 Homes").color(white));

        Utils.createItem(inv, Material.RED_BANNER, 1, 7, Component.text("Mythic").color(TextColor.color(255, 0, 0)),
                Component.text("$100,000").color(white),
                Component.text("- 12 Galactic Keys").color(white),
                Component.text("- +100 Chunks to Claim").color(white),
                Component.text("- /heal (60 minute cooldown)").color(white),
                Component.text("- Basic Mine").color(white));

        Utils.createItem(inv, Material.DIAMOND_SWORD, 1, 8, Component.text(Utils.chat("&1&lGa&2&lm&3&ler")),
                Component.text("$500,000").color(white),
                Component.text("- Coming soon!").color(white));

        Utils.makeDummySlots(inv);
        return inv;
    }

}

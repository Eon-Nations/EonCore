package me.squid.eoncore.menus;

import me.squid.eoncore.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class AdminGUI {

    public Inventory GUI () {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.chat("&5&lEon Admin GUI"));

        Utils.createItem(inv, Material.PLAYER_HEAD, 1, 12, "&b");
        Utils.createItem(inv, Material.PAPER, 1, 14, "&bReload Config");
        Utils.createItem(inv, Material.CLOCK, 1, 16, Utils.chat("&bChange Time/Weather"));
        Utils.makeDummySlots(inv);

        return inv;
    }

    public Inventory WeatherTimeGUI(Player p){
        Inventory inv = Bukkit.createInventory(null, 27, Utils.chat("&b&lWeather/Time Menu"));
        String weather;
        if (p.getWorld().isThundering()){
            weather = "Stormy";
        } else {
            weather = "Sunny";
        }

        Utils.createItem(inv, Material.LIGHT_BLUE_CONCRETE, 1, 2, "&aChange to Day");
        Utils.createItem(inv, Material.GREEN_CONCRETE, 1, 8, "&aChange to Sunny");
        Utils.createItem(inv, Material.CLOCK, 1, 14, "&bCurrent Time and Setting", "&aTime: " + p.getWorld().getTime()
        , "&5Weather: " + weather);
        Utils.createItem(inv, Material.GRAY_CONCRETE, 1, 20, "&7Change to Night");
        Utils.createItem(inv, Material.LIGHT_GRAY_CONCRETE, 1, 26, "&7Change to Stormy");

        return inv;
    }

    public Inventory PeopleGUI() {
        Inventory inv = Bukkit.createInventory(null, 54, Utils.chat("&5&lEon Management"));

        for (Player p : Bukkit.getOnlinePlayers()) {
            ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta skull = (SkullMeta) item.getItemMeta();
            skull.setOwningPlayer(p);
            skull.setDisplayName(p.getName());

            List<String> lore = new ArrayList<>();
            lore.add(Utils.chat("&bHealth: " + p.getHealth()));
            lore.add(Utils.chat("&bFood Level: " + p.getFoodLevel()));
            lore.add(Utils.chat("&bWorld: " + StringUtils.capitalize(p.getWorld().getName())));
            lore.add(Utils.chat("&bUUID: " + p.getUniqueId().toString()));
            skull.setLore(lore);

            item.setItemMeta(skull);
            inv.addItem(item);
        }

        return inv;
    }

    public Inventory PeopleOptionsGUI(ItemStack head) {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.chat(""));

        String uuid = head.getItemMeta().getLore().get(3);
        List<String> list = new ArrayList<>();
        list.add(uuid);
        head.getItemMeta().setLore(list);
        inv.setItem(6, head);

        return inv;
    }

}

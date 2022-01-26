package me.squid.eoncore.menus;

import me.squid.eoncore.managers.MutedManager;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminGUI {

    MutedManager mutedManager;

    public AdminGUI(MutedManager mutedManager) {
        this.mutedManager = mutedManager;
    }

    public Inventory GUI () {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.chat("&5&lEon Admin GUI"));

        Utils.createItem(inv, Material.PLAYER_HEAD, 1, 12, "&bPlayer Menu");
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
        Utils.makeDummySlots(inv);

        return inv;
    }

    public Inventory PeopleGUI() {
        Inventory inv = Bukkit.createInventory(null, 54, Utils.chat("&5&lEon Management"));

        for (Player p : Bukkit.getOnlinePlayers()) {
            ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta skull = (SkullMeta) item.getItemMeta();
            skull.setOwningPlayer(p);
            skull.displayName(Component.text(p.getName()));

            List<Component> lore = new ArrayList<>();
            lore.add(Utils.chat("&bHealth: " + p.getHealth()));
            lore.add(Utils.chat("&bFood Level: " + p.getFoodLevel()));
            lore.add(Utils.chat("&bWorld: " + StringUtils.capitalize(p.getWorld().getName())));
            lore.add(Utils.chat("&bUUID: " + p.getUniqueId()));
            skull.lore(lore);

            item.setItemMeta(skull);
            inv.addItem(item);
        }

        return inv;
    }

    public Inventory PeopleOptionsGUI(ItemStack head) {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.chat("&a&lPlayer Options"));

        inv.setItem(4, head);
        Utils.createItem(inv, Material.WOODEN_AXE, 1,  11, "&c&lBan");
        Utils.createItem(inv, Material.GOLDEN_CARROT, 1, 13, "&a&lFeed");
        Utils.createItem(inv, Material.ENDER_PEARL, 1, 15, "&b&lTeleport");
        Utils.createItem(inv, Material.WOODEN_SHOVEL, 1, 17, "&a&lMute");
        Utils.makeDummySlots(inv);

        return inv;
    }

    public Inventory getBanReasons(UUID uuid) {
        Inventory inv = Bukkit.createInventory(null, 27,  Utils.chat("&bBan Reasons"));

        Utils.createItem(inv, Material.WOODEN_AXE, 1, 5, "&bSelect a reason to ban", "&fUUID: " + uuid.toString());
        Utils.createItem(inv, Material.MAGENTA_WOOL, 1, 10, "&6Advertising other servers/products are not allowed", "&f(30d ban -> IP Ban)");
        Utils.createItem(inv, Material.GREEN_WOOL, 1, 11, Utils.chat("&6DoX and DDoS threats are now allowed"), Utils.chat("&f(Perm Ban)"));
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 12, "&6Exploiting bugs including duping is not allowed", "&f(14d ban -> IP Ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 13, "&6Ban Evasion is not allowed", "&f(Perm IP Ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 14, "&6Mute Evasion is not allowed", "&f(7d ban -> Perm Ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 15, "&6Forging a ban is not allowed", "&f(7d ban -> 30d ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 16, "&6Inappropriate names are not allowed", "&f(Kick -> 5d ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 17, "&6Scamming others is not allowed", "&f(7d ban -> Perm Ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 18, "&6Player killing is not allowed", "&f(14d ban -> 60d ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 19, "&6Use of a hacked client is not allowed", "&f(30d ban -> IP Ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 20, "&6No building near someone without permission", "&f(250 blocks)", "&f(1-30d ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 21, "&6No harassing other players", "&f(1-30d ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 22, "&6No inappropriate public builds", "&f(7-30d ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 23, "&6X-Ray Texture Packs/Mods are not allowed", "&f(14d -> Perm Ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 24, "&6No griefing other players. Even if it is unclaimed", "&f(7d ban -> Perm Ban)");
        Utils.makeDummySlots(inv);

        return inv;
    }

    public Inventory getMuteReasons(UUID uuid) {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.chat("&bMute Reasons"));

        Utils.createItem(inv, Material.WOODEN_SHOVEL, 1, 5, "&bSelect a reason to mute", "&fUUID: " + uuid.toString());
        Utils.createItem(inv, Material.BLUE_WOOL, 1, 10, Utils.chat("&6No personal information in public chat"), Utils.chat("&f(24h mute -> Perm Mute"));
        Utils.createItem(inv, Material.PURPLE_WOOL, 1, 11, Utils.chat("&6Offensive language is not allowed"), Utils.chat("&f(2 Verbal Warnings -> 24h mute)"));
        Utils.createItem(inv, Material.LIGHT_BLUE_WOOL, 1, 12, Utils.chat("&6Bigotry/hate speech is not allowed"), Utils.chat("&f(2 Verbal Warnings -> 24h mute)"));
        Utils.createItem(inv, Material.MAGENTA_WOOL, 1, 13, Utils.chat("&6Misleading players is not allowed"), Utils.chat("&f(Verbal Warning -> 1h mute)"));
        Utils.createItem(inv, Material.ORANGE_WOOL, 1, 14, Utils.chat("&6Harassing players/staff members is not allowed"), Utils.chat("&f(2 Verbal Warnings -> 1h mute)"));
        Utils.createItem(inv, Material.GREEN_WOOL, 1, 15, Utils.chat("&6Excessive swearing is not allowed"), Utils.chat("&f(Verbal Warning -> 5h mute)"));
        Utils.createItem(inv, Material.GRAY_WOOL, 1, 16, Utils.chat("&6No political discussions/statements allowed"), Utils.chat("&f(2 Verbal Warnings -> 3h mute)"));
        Utils.createItem(inv, Material.RED_WOOL, 1, 17, Utils.chat("&6No sexual statements/discussions in chat"), Utils.chat("&f(2 Verbal Warnings -> 3h mute)"));
        Utils.createItem(inv, Material.BLUE_WOOL, 1, 1, "&6Indirect advertising is not allowed", "&f(2 Verbal Warnings -> 3h mute)");
        Utils.makeDummySlots(inv);

        return inv;
    }

    public Inventory getLengthGUI(UUID uuid, String action, String reason) {
        if (action.equals("mute")) {
            return getLength(uuid, action, reason, "Hour");
        } else {
            return getLength(uuid, action, reason, "Day");
        }
    }

    private Inventory getLength(UUID uuid, String action, String reason, String time) {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.chat("&a&lLength"));

        Utils.createItem(inv, Material.WOODEN_SWORD, 1, 5, "&bUUID: " + uuid.toString(), "&fReason: " + reason, "&fAction: " + action);
        Utils.createItem(inv, Material.RED_WOOL, 1, 10, "&c&l1 " + time);
        Utils.createItem(inv, Material.RED_WOOL, 1, 12, "&c&l3 " + time);
        Utils.createItem(inv, Material.RED_WOOL, 1, 14, "&c&l7 " + time);
        Utils.createItem(inv, Material.RED_WOOL, 1, 16, "&c&l14 " + time);
        Utils.createItem(inv, Material.RED_WOOL, 1, 18, "&c&l30 " + time);
        Utils.createItem(inv, Material.PURPLE_WOOL, 1, 22, "&5&lPerm Ban");
        Utils.createItem(inv, Material.PURPLE_WOOL, 1, 24, "&5&lPerm Ban");

        return inv;
    }

    public Inventory getMutedInventory() {
        List<UUID> uuids = mutedManager.getAllUUIDs();
        Inventory inv = Bukkit.createInventory(null, 54, Utils.chat("&b&lMuted Chat"));

        for (int i = 0; i < uuids.size(); i++) {
            ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
            OfflinePlayer p = Bukkit.getOfflinePlayer(uuids.get(i));
            skullMeta.setOwningPlayer(p);
            if (p.getPlayer() != null) {
                skullMeta.setPlayerProfile(p.getPlayer().getPlayerProfile());
            }

            List<Component> lore = new ArrayList<>();
            skullMeta.displayName(Utils.chat("&b" + p.getName()));
            lore.add(Utils.chat("&b" + mutedManager.getCooldown(p.getUniqueId()).getTimeRemaining() / 60000));
            lore.add(Utils.chat("&bUUID: " + p.getUniqueId()));
            skullMeta.lore(lore);
            head.setItemMeta(skullMeta);
            inv.setItem(i, head);
        }
        return inv;
    }

    public Inventory mutedOptions(ItemStack head) {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.chat("&b&lMuted Options"));

        inv.setItem(4, head);
        Utils.createItem(inv, Material.EMERALD_BLOCK, 1, 16, Utils.chat("&a&lConfirm"));
        Utils.createItem(inv, Material.RED_CONCRETE, 1, 12, Utils.chat("&c&lGo Back"));

        return inv;
    }

}

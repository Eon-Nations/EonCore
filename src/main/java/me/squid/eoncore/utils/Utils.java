package me.squid.eoncore.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.*;

import static org.bukkit.Bukkit.getServer;

public class Utils {

    public static String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void createItem(Inventory inv, Material material, int amount, int invSlot, String displayName, String... loreString) {
        ItemStack item;
        List<String> lore = new ArrayList<>();
        item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        Objects.requireNonNull(meta).setDisplayName(Utils.chat(displayName));
        for (String s : loreString) {
            lore.add(Utils.chat(s));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(invSlot - 1, item);
    }

    public static void createItem(Inventory inv, Material material, int amount, int invSlot, String displayName, List<String> loreString) {
        ItemStack item;
        item = new ItemStack(material, amount);
        List<String> lore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();

        for (String s : loreString) {
            lore.add(Utils.chat(s));
        }

        Objects.requireNonNull(meta).setDisplayName(Utils.chat(displayName));
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(invSlot - 1, item);
    }

    public static void removeRecipe(Material m) {

        Iterator<Recipe> it = getServer().recipeIterator();
        Recipe recipe;
        while (it.hasNext()) {
            recipe = it.next();
            if (recipe != null && recipe.getResult().getType() == m) {
                it.remove();
            }
        }
    }

    public static Location generateLocation() {
        Random random = new Random();

        int x = random.nextInt(17500) * (random.nextBoolean() ? -1 : 1);
        int y = 150;
        int z = random.nextInt(17500) * (random.nextBoolean() ? -1 : 1);
        Location randomLoc = new Location(Bukkit.getWorld("world"), x, y, z);

        y = randomLoc.getWorld().getHighestBlockYAt(randomLoc.add(0, 1, 0));
        randomLoc.setY(y);

        if (isLocationSafe(randomLoc)) {
            return randomLoc;
        }

        while (!isLocationSafe(randomLoc)) {
            randomLoc = generateLocation();
        }

        return randomLoc;
    }

    public static boolean isLocationSafe(Location location) {
        ArrayList<Material> blackList = new ArrayList<>();
        blackList.add(Material.LAVA);
        blackList.add(Material.CACTUS);
        blackList.add(Material.FIRE);
        blackList.add(Material.WATER);

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        Block block = location.getWorld().getBlockAt(x, y, z);
        Block below = location.getWorld().getBlockAt(x, y - 1, z);
        Block above = location.getWorld().getBlockAt(x, y + 1, z);

        return !(blackList.contains(below.getType())) || block.getType().isSolid() || above.getType().isSolid();
    }

    public static ItemStack createKitItem(Material material, int amount, String displayName, @Nullable List<String> lore, @Nullable HashMap<Enchantment, Integer> enchantments) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Utils.chat(displayName));
        meta.setLore(lore);
        item.setItemMeta(meta);

        if (enchantments != null) {
            for (Enchantment e : enchantments.keySet()) {
                item.addUnsafeEnchantment(e, enchantments.get(e));
            }
        }
        return item;
    }

    public static void makeDummySlots(Inventory inv) {
        ItemStack item;
        item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("");
        item.setItemMeta(meta);

        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, item);
            }
        }
    }
}

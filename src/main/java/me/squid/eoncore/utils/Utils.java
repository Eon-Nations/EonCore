package me.squid.eoncore.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

import static org.bukkit.Bukkit.getServer;

public class Utils {

    public static @NotNull String chat(String s) {
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

    public static void createItem(Inventory inv, Material material, int amount, int invSlot, Component displayName, Component... loreString) {
        ItemStack item;
        List<Component> lore = new ArrayList<>();
        item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(displayName);
        Collections.addAll(lore, loreString);
        meta.lore(lore);
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

    public static Location generateLocation(World world) {
        Random random = new Random();

        int x = random.nextInt(17500) * (random.nextBoolean() ? -1 : 1);
        int y = 150;
        int z = random.nextInt(17500) * (random.nextBoolean() ? -1 : 1);
        Location randomLoc = new Location(world, x, y, z);

        y = randomLoc.getWorld().getHighestBlockYAt(randomLoc.add(0, 1, 0));
        randomLoc.setY(y);

        if (isLocationSafe(randomLoc)) {
            return randomLoc;
        }

        while (!isLocationSafe(randomLoc)) {
            randomLoc = generateLocation(world);
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
        meta.displayName(Component.text(""));
        item.setItemMeta(meta);

        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, item);
            }
        }
    }

    public static String getFormattedTimeString(long timeInMilliseconds) {
        String formattedDate = DurationFormatUtils.formatDurationHMS(timeInMilliseconds);
        String[] splitString = formattedDate.split(":");

        int totalHours = Integer.getInteger(splitString[0]);
        if (totalHours > 24) {
            int days = totalHours % 24;
            return days + " days " + (totalHours - (24 * days)) + " minutes " + splitString[1] + " seconds";
        } else {
            return totalHours + " hours " + splitString[1] + " minutes " + splitString[2] + " seconds";
        }
    }

    public static Component getPrefix(String name) {
        return switch (name) {
            case "admin" -> Component.text("[").color(TextColor.color(128, 128, 128))
                    .append(Component.text("Eon Admin").color(TextColor.color(102, 178, 255))
                            .append(Component.text("] ").append(Component.text(" "))));
            case "nations" -> Component.text("[").color(TextColor.color(128, 128, 128))
                    .append(Component.text("Eon Nations").color(TextColor.color(102, 178, 255))
                            .append(Component.text("] ").append(Component.text(" "))));
            case "moderation" -> Component.text("[").color(TextColor.color(128, 128, 128))
                    .append(Component.text("Eon Moderation").color(TextColor.color(102, 178, 255))
                            .append(Component.text("] ").color(TextColor.color(128, 128, 128))
                                    .append(Component.text(" "))));
            default -> Component.text("Invalid prefix");
        };
    }
}

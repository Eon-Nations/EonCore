package me.squid.eoncore.utils;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.bukkit.Bukkit.getServer;

public class Utils {

    public static String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String translateHex(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] hexCharacters = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char hexChar : hexCharacters) {
                builder.append("&").append(hexChar);
            }

            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void createItem(Inventory inv, Material material, int amount, int invSlot, String displayName, String... loreString) {
        ItemStack item = new ItemStack(material, amount);
        List<String> lore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(displayName);
        for (String s : loreString) {
            lore.add(Utils.chat(s));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(invSlot - 1, item);
    }

    public static void createItem(Inventory inv, Material material, int amount, int invSlot, String displayName, List<String> loreString) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        // Create lore array
        Stream<String> loreStream = loreString.stream();
        List<String> lore = loreStream.map(Utils::translateHex).toList();

        meta.setDisplayName(Utils.chat(displayName));
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

    public static Location generateLocation(World world, List<Material> blackList) {
        Random random = new Random();

        int x = random.nextInt(7500) * (random.nextBoolean() ? -1 : 1);
        int y = 150;
        int z = random.nextInt(7500) * (random.nextBoolean() ? -1 : 1);
        Location randomLoc = new Location(world, x, y, z);

        y = randomLoc.getWorld().getHighestBlockYAt(randomLoc.add(0, 1, 0));
        randomLoc.setY(y);

        if (isLocationSafe(randomLoc, blackList)) {
            return randomLoc;
        } else {
            return generateLocation(world, blackList);
        }
    }

    public static boolean isLocationSafe(Location location, List<Material> blackList) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        Block block = location.getWorld().getBlockAt(x, y, z);
        Block below = location.getWorld().getBlockAt(x, y - 1, z);
        Block above = location.getWorld().getBlockAt(x, y + 1, z);

        return !(blackList.contains(below.getType()))  || block.getType().isSolid() || above.getType().isSolid();
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
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("");
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

        int totalHours = Integer.parseInt(splitString[0]);
        int totalMinutes = Integer.parseInt(splitString[1]);
        int totalSeconds = Integer.parseInt(splitString[2].split("\\.")[0]);
        if (totalHours > 24) {
            int days = totalHours / 24;
            return days + " days " + (totalHours - (24 * days)) + " hours " + totalMinutes + " minutes " + totalSeconds + " seconds";
        } else {
            return totalHours + " hours " + totalMinutes + " minutes " + totalSeconds + " seconds";
        }
    }

    public static String getPrefix(String name) {
        return switch (name) {
            case "admin" -> translateHex("#7f7f7f[#66b2ffEon Admin#7f7f7f] ");
            case "nations" -> translateHex("#7f7f7f[#66b2ffEon Nations#7f7f7f] ");
            case "moderation" -> translateHex("#7f7f7f[#66b2ffEon Moderation#7f7f7f] ");
            default -> "Invalid Prefix";
        };
    }

    public static Location getSpawnLocation() {
        return new Location(Bukkit.getWorld("spawn_void"), -12, 87, -16);
    }

    public static String getMessageFromArgs(String[] args) {
        StringBuilder sb = new StringBuilder();
        Stream<String> argStream = Arrays.stream(args);
        argStream.forEach(arg -> sb.append(arg).append(" "));
        return ChatColor.translateAlternateColorCodes('&', sb.toString().trim());
    }
}

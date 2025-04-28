package com.eonnations.eoncore.utils.parsers;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class YamlItemParser {
    private YamlItemParser() { }

    public static void setItemName(ItemStack item, String name) {
        setItemName(item, MiniMessage.miniMessage().deserialize(name));
    }

    public static void setItemName(ItemStack item, Component name) {
        ItemMeta meta = item.getItemMeta();
        meta.displayName(name);
        item.setItemMeta(meta);
    }

    public static void setItemLore(ItemStack item, List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        List<Component> newLore = lore.map(MiniMessage.miniMessage()::deserialize);
        meta.lore(newLore.toJavaList());
    }

    private static Enchantment enchantmentFromString(String name) {
        RegistryAccess registryAccess = RegistryAccess.registryAccess();
        Registry<@NotNull Enchantment> registry = registryAccess.getRegistry(RegistryKey.ENCHANTMENT);
        return registry.get(NamespacedKey.minecraft(name));
    }

    public static void addEnchantments(ItemStack item, ConfigurationSection section) {
        ItemMeta meta = item.getItemMeta();
        Map<Enchantment, Integer> enchantments = List.ofAll(section.getStringList("enchantments"))
                .toMap(YamlItemParser::enchantmentFromString, full -> Integer.parseInt(full.split(":")[1]));
        for (Tuple2<Enchantment, Integer> enchantment : enchantments) {
            meta.addEnchant(enchantment._1, enchantment._2, true);
        }
        item.setItemMeta(meta);
    }

    public static ItemStack parse(ConfigurationSection section) {
        Material type = Material.valueOf(section.getString("type"));
        int amount = section.getInt("amount", 1);
        ItemStack item = new ItemStack(type, amount);
        String name = section.getString("name");
        setItemName(item, name);
        List<String> lore = List.ofAll(section.getStringList("lore"));
        setItemLore(item, lore);
        addEnchantments(item, section);
        return item;
    }
}

package com.eonnations.eoncore.utils.parsers;

import com.eonnations.eoncore.utils.menus.Button;
import com.eonnations.eoncore.utils.menus.ButtonFunction;
import com.eonnations.eoncore.utils.menus.Menu;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.xenondevs.invui.item.impl.AbstractItem;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.stream.Collectors;

public class MenuParser {
    private MenuParser() { }

    public static Window parse(YamlConfiguration config, Player viewer, Map<String, String> placeholders) {
        return parse(config, viewer, placeholders, HashMap.empty(), List.of(), List.of());
    }

    public static Window parse(YamlConfiguration config, Player viewer, Map<String, String> placeholders, Map<Character, ButtonFunction> buttons) {
        return parse(config, viewer, placeholders, buttons, List.of(), List.of());
    }

    public static Window parse(YamlConfiguration config, Player viewer, Map<String, String> placeholders, Map<Character, ButtonFunction> buttons, List<Runnable> closeHandlers) {
        return parse(config, viewer, placeholders, buttons, List.of(), closeHandlers);
    }

    public static Window parse(YamlConfiguration config, Player viewer, Map<String, String> placeholders, Map<Character, ButtonFunction> buttons, List<Runnable> openHandlers, List<Runnable> closeHandlers) {
        String title = config.getString("title");
        List<String> structure = List.ofAll(config.getStringList("structure"));
        ConfigurationSection itemSection = config.getConfigurationSection("items");
        Map<Character, ItemStack> items = List.ofAll(itemSection.getKeys(false))
                .toMap(s -> s.charAt(0), s -> YamlItemParser.parse(itemSection.getConfigurationSection(s)));
        var builder = Menu.Builder.builder()
                .title(title)
                .viewer(viewer);
        for (Runnable openHandler : openHandlers) {
            builder.addOpenHandler(openHandler);
        }
        for (Runnable closeHandler : closeHandlers) {
            builder.addCloseHandler(closeHandler);
        }
        for (Tuple2<Character, ItemStack> invItem : items) {
            Option<ButtonFunction> buttonOpt = buttons.get(invItem._1);
            ItemStack item = invItem._2.clone();
            for (Tuple2<String, String> placeholder : placeholders) {
                TextReplacementConfig replacementConfig = TextReplacementConfig.builder()
                        .match(placeholder._1)
                        .replacement(placeholder._2)
                        .build();
                ItemMeta meta = item.getItemMeta().clone();
                meta.customName(Option.of(meta.customName())
                                .map(name -> name.replaceText(replacementConfig))
                                .getOrNull());
                meta.lore(Option.of(meta.lore())
                        .map(lore -> lore.stream().map(c -> c.replaceText(replacementConfig)).collect(Collectors.toList()))
                        .getOrNull());
                item.setItemMeta(meta);
            }
            if (buttonOpt.isDefined()) {
                Button button = new Button(item, buttonOpt.get());
                builder.addItem(invItem._1, button);
            } else {
                builder.addItem(invItem._1, new SimpleItem(item));
            }
        }
        builder.addStructure(structure);
        return builder.build();
    }
}

package com.eonnations.eoncore.utils.menus;

import io.vavr.Function2;
import io.vavr.Tuple2;
import io.vavr.collection.Map;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class Button extends AbstractItem {

    private final ItemStack item;
    private final Function2<Player, InventoryClickEvent, Void> handler;

    public Button(ItemStack item, Function2<Player, InventoryClickEvent, Void> handler) {
        this.item = item.clone();
        this.handler = handler;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(item.clone());
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        handler.apply(player, inventoryClickEvent);
    }
}

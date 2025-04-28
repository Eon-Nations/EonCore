package com.eonnations.eoncore.utils.menus;

import io.vavr.Function2;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface ButtonFunction extends Function2<Player, InventoryClickEvent, Void> {
}

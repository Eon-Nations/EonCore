package me.squid.eoncore.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.eonnations.eonpluginapi.menu.Item;
import org.eonnations.eonpluginapi.menu.Menu;
import org.eonnations.eonpluginapi.menu.MenuInteraction;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import me.squid.eoncore.node.Resource;
import me.squid.eoncore.node.Node;
import net.kyori.adventure.text.Component;

public class SpawnNodeCommand extends EonCommand {
    
    public SpawnNodeCommand(EonCore plugin) {
        super(plugin);
    }

    private MenuInteraction createHandler(Material material) {
        Resource resource = switch (material) {
            case COPPER_INGOT -> Resource.COPPER;
            case IRON_INGOT -> Resource.IRON;
            case GOLD_INGOT -> Resource.GOLD;
            case DIAMOND -> Resource.DIAMOND;
            case EMERALD -> Resource.EMERALD;
            default -> Resource.COPPER;
        };
        return e -> {
            Node node = new Node(e.getWhoClicked().getLocation(), resource);
            return true;
        }; 
    }

    public void execute(Player player, String[] args) {
        Menu menu = Menu.create(27, Component.text("Node Creator"))
            .withItem(4, Item.builder(Material.COPPER_INGOT).handler(ClickType.LEFT, true, createHandler(Material.COPPER_INGOT)).finish())
            .withItem(11, Item.builder(Material.IRON_INGOT).handler(ClickType.LEFT, true, createHandler(Material.IRON_INGOT)).finish())
            .complete();
        menu.openToPlayer(player); 
    }
}

package me.squid.eoncore.node;

import me.squid.eoncore.holograms.FloatingItem;
import me.squid.eoncore.holograms.Hologram;
import me.squid.eoncore.misc.menus.MenuBuilder;

import java.util.Random;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import io.vavr.collection.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import org.eonnations.eonpluginapi.events.EventSubscriber;
import org.eonnations.eonpluginapi.events.EventHandler;
import org.eonnations.eonpluginapi.menu.Item;
import org.eonnations.eonpluginapi.menu.Menu;

import com.mysql.cj.util.StringUtils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;

import static me.squid.eoncore.messaging.Messaging.fromFormatString;

public class Node {
    
    private static final int MIN_OUTPUT_RATE = 10;
    private static final int MAX_OUTPUT_RATE = 100;
    private static EventHandler<BlockBreakEvent> deepslateCancel = cancelBreakingDeepslate();    
    private static EventHandler<PlayerInteractEvent> menuInteraction = nodeMenuListener();

    private Hologram nodeInfo;
    private FloatingItem resourceDisplay;
    private int outputRate;
    private boolean isClaimed;
    private Location location;
    private Resource resource;

    private static Map<Location, Node> cache = new HashMap<>();

    private static boolean claimNode(BlockPlaceEvent event) {
        Collection<ArmorStand> holograms = event.getPlayer().getLocation().getNearbyEntitiesByType(ArmorStand.class, 5.0);
        for (ArmorStand stand : holograms) {
            Component name = stand.customName().replaceText(TextReplacementConfig.builder().match("UNCLAIMED").replacement("CLAIMED").build());
            stand.customName(name);
        }
        // Start dropping items into the chest until it is no longer claimed
        return false;
    }

    // Make a listener that listens for breaking the REINFORCED_DEEPSLATE and cancel it
    private static EventHandler<BlockBreakEvent> cancelBreakingDeepslate() {
        if (deepslateCancel != null) return deepslateCancel;
        return EventSubscriber.subscribe(BlockBreakEvent.class, EventPriority.NORMAL)
            .filter(e -> e.getBlock().getType().equals(Material.REINFORCED_DEEPSLATE))
            .handler(e -> true);
    }

    private static EventHandler<PlayerInteractEvent> nodeMenuListener() {
        if (menuInteraction != null) return menuInteraction;
        return EventSubscriber.subscribe(PlayerInteractEvent.class, EventPriority.NORMAL)
            .filter(e -> e.getClickedBlock().getType().equals(Material.REINFORCED_DEEPSLATE))
            .filter(e -> e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            .handler(e -> {
                Node currentNode = cache.get(e.getClickedBlock().getLocation());
                // Add names for the chest and ender chest respectively
                Menu menu = Menu.create(27, Component.text(currentNode.getResource().name() + " Node - " + currentNode.getOutputRate() + "/min"))
                    .withItem(11, Item.builder(Material.CHEST).finish())
                    .withItem(15, Item.builder(Material.ENDER_CHEST).finish())
                    .makeDummySlots()
                    .complete();
                menu.openToPlayer(e.getPlayer());
                return false;
            });
    }

    private static void claimToUnclaim(Location loc) {
        Collection<ArmorStand> holograms = loc.getNearbyEntitiesByType(ArmorStand.class, 5.0);
        for (ArmorStand stand : holograms) {
            Component name = stand.customName().replaceText(TextReplacementConfig.builder().match("CLAIMED").replacement("UNCLAIMED").build());
            stand.customName(name);
        }
    }

    public Node(Location location, Resource resource) {
        Random random = new Random();
        this.outputRate = random.nextInt((MAX_OUTPUT_RATE - MIN_OUTPUT_RATE) + 1) + MIN_OUTPUT_RATE;
        String resourceName = switch (resource) {
            case COPPER -> "COPPER_INGOT";
            case IRON -> "IRON_INGOT";
            case GOLD -> "GOLD_INGOT";
            case DIAMOND -> "DIAMOND";
            case EMERALD -> "EMERALD";
        };
        Location resourceLoc = location.clone().add(new Vector(0, 2, 0));
        this.resourceDisplay = new FloatingItem(resourceLoc, Material.valueOf(resourceName));
        String claimString = isClaimed ? "CLAIMED" : "UNCLAIMED";
        List<Component> lines = List.of(fromFormatString("<#F0E442><bold>" + outputRate + "</bold></#F0E442>"), 
            fromFormatString("<#D55E00><bold>" + claimString + "</bold></#D55E00>"));
        Location nodeLoc = location.clone().add(new Vector(0, 1, 0));
        this.nodeInfo = new Hologram(lines, nodeLoc);
        cache.put(location, this);
    }

    public Resource getResource() {
        return resource;
    }

    public Location getLocation() {
        return location;
    }

    public int getOutputRate() {
        return outputRate;
    }

    public void remove() {
        nodeInfo.close();
        resourceDisplay.close();
    }
}

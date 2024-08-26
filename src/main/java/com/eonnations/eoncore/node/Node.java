package com.eonnations.eoncore.node;

import static com.eonnations.eoncore.messaging.Messaging.fromFormatString;

import java.util.Collection;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.util.Vector;

import com.eonnations.eoncore.common.events.EventHandler;
import com.eonnations.eoncore.common.events.EventSubscriber;
import com.eonnations.eoncore.holograms.FloatingItem;
import com.eonnations.eoncore.holograms.Hologram;

import io.vavr.collection.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;

public class Node {
    
    private static final int MIN_OUTPUT_RATE = 10;
    private static final int MAX_OUTPUT_RATE = 100;
    private static EventHandler<BlockPlaceEvent> chestPlace = registerChestPlaceListener();
    private static EventHandler<BlockBreakEvent> deepslateCancel = cancelBreakingDeepslate();    
    private static EventHandler<BlockBreakEvent> replaceHolo = replaceUnclaimed();

    private Hologram nodeInfo;
    private FloatingItem resourceDisplay;
    private int outputRate;
    private boolean isClaimed;

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

    private static boolean claimToUnclaim(BlockBreakEvent e) {
        Collection<ArmorStand> holograms = e.getPlayer().getLocation().getNearbyEntitiesByType(ArmorStand.class, 5.0);
        for (ArmorStand stand : holograms) {
            Component name = stand.customName().replaceText(TextReplacementConfig.builder().match("CLAIMED").replacement("UNCLAIMED").build());
            stand.customName(name);
        }
        return false;
    }

    private static EventHandler<BlockBreakEvent> replaceUnclaimed() {
        if (replaceHolo != null) return replaceHolo;
        return EventSubscriber.subscribe(BlockBreakEvent.class, EventPriority.NORMAL)
            .filter(e -> e.getBlock().getType().equals(Material.CHEST) && 
                e.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.REINFORCED_DEEPSLATE))
            .handler(Node::claimToUnclaim);
    }

    public static EventHandler<BlockPlaceEvent> registerChestPlaceListener() {
        if (chestPlace != null) return chestPlace;
        return EventSubscriber.subscribe(BlockPlaceEvent.class, EventPriority.NORMAL)  
            .filter(e -> e.getBlock().getType().equals(Material.CHEST))
            .filter(e -> e.getBlockAgainst().getType().equals(Material.REINFORCED_DEEPSLATE))
            .handler(Node::claimNode);
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
    }


    public void remove() {
        nodeInfo.close();
        resourceDisplay.close();
    }
}

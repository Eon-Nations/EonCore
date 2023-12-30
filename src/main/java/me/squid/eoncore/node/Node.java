package me.squid.eoncore.node;

import me.squid.eoncore.holograms.FloatingItem;
import me.squid.eoncore.holograms.Hologram;

import java.util.Random;

import io.vavr.collection.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;
import net.kyori.adventure.text.Component;

import static me.squid.eoncore.messaging.Messaging.fromFormatString;

public class Node {
    
    private static final int MIN_OUTPUT_RATE = 10;
    private static final int MAX_OUTPUT_RATE = 100;

    private Hologram nodeInfo;
    private FloatingItem resourceDisplay;
    private int outputRate;
    private boolean isClaimed;
    
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
        this.resourceDisplay = new FloatingItem(location.add(new Vector(0, 2, 0)), Material.valueOf(resourceName));
        String claimString = isClaimed ? "CLAIMED" : "UNCLAIMED";
        List<Component> lines = List.of(fromFormatString("<#F0E442><bold>" + outputRate + "</bold></#F0E442>"), 
            fromFormatString("<D55E00><bold>" + claimString + "</bold></D55E00>"));
        this.nodeInfo = new Hologram(lines, location.add(new Vector(0, 1, 0)));
    }


    public void remove() {
        nodeInfo.close();
        resourceDisplay.close();
    }
}

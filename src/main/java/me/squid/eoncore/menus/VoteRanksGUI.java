package me.squid.eoncore.menus;

import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class VoteRanksGUI {

    public Inventory MainGUI() {
        Inventory inv = Bukkit.createInventory(null, 9, Utils.chat("&a&lVote Ranks"));

        Utils.createItem(inv, Material.GOLD_BLOCK, 1, 7, "&a&lVoter of the Month");
        Utils.createItem(inv, Material.DIAMOND_PICKAXE, 1, 3, "&a&lVoter I");
        Utils.createItem(inv, Material.DIAMOND_ORE, 1, 4, "&a&lVoter II");
        Utils.createItem(inv, Material.EMERALD_BLOCK, 1, 5, "&a&lVoter III");
        Utils.createItem(inv, Material.EMERALD, 1, 6, "&a&lVoter IV");
        Utils.makeDummySlots(inv);

        return inv;
    }

    public Inventory VoterOfTheMonthGUI() {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.chat("&a&lVoter Of The Month"));

        Utils.createItem(inv, Material.EMERALD, 1, 13, "&a&l$20,000");
        Utils.createItem(inv, Material.TRIPWIRE_HOOK, 1, 14, "&5&l5 Galactic Keys");
        Utils.createItem(inv, Material.BARRIER, 1, 23, "&4&lExit");
        Utils.createItem(inv, Material.COMMAND_BLOCK, 1, 15, "&a&lCommand: /fly for 1 month");
        Utils.makeDummySlots(inv);

        return inv;
    }

    public Inventory VoterIGUI() {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.chat("&a&lVoter I"));

        Utils.createItem(inv, Material.DIAMOND_PICKAXE, 1, 13, "&a&lVoter Set of Tools/Armor");
        Utils.createItem(inv, Material.BARRIER, 1, 23, "&4&lExit");
        Utils.createItem(inv, Material.TRIPWIRE_HOOK, 10, 14, "&a&lVote Crate Key");
        Utils.createItem(inv, Material.TRIPWIRE_HOOK, 3, 15, "&5&lGalactic Key");
        Utils.makeDummySlots(inv);

        return inv;
    }

    public Inventory VoterIIGUI() {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.chat("&a&lVoter II"));

        Utils.createItem(inv, Material.DIAMOND_CHESTPLATE, 1, 13, "&a&lVote Kit");
        Utils.createItem(inv, Material.BARRIER, 1, 23, "&4&lExit");
        Utils.createItem(inv, Material.DIAMOND_ORE, 1, 14 ,"&a&lVoters Cave");
        Utils.createItem(inv, Material.TRIPWIRE_HOOK, 5, 15, "&5&lGalactic Key");
        Utils.makeDummySlots(inv);

        return inv;
    }

    public Inventory VoterIIIGUI() {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.chat("&a&lVoter III"));

        Utils.createItem(inv, Material.TRIPWIRE_HOOK, 10, 13, "&5&lGalactic Key");
        Utils.createItem(inv, Material.BARRIER, 23, 23, "&4&lExit");
        Utils.createItem(inv, Material.COMMAND_BLOCK, 1, 14, "&fCommand: /feed for two weeks");
        Utils.createItem(inv, Material.EMERALD, 1, 15, "$50000");
        Utils.makeDummySlots(inv);

        return inv;
    }

    public Inventory VoterIVGUI() {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.chat("&a&lVoter IV"));

        Utils.createItem(inv, Material.DIAMOND_CHESTPLATE, 1, 13, "&5&lSet of Galactic Tools");
        Utils.createItem(inv, Material.BARRIER, 1, 23, "&4&lExit");
        Utils.createItem(inv, Material.DIAMOND_ORE, 1, 14, "&a&lAccess to Donator Cave");
        Utils.createItem(inv, Material.COMMAND_BLOCK, 1, 15, "&a&lPermanent Access to /feed or /fly (Choice)");
        Utils.makeDummySlots(inv);

        return inv;
    }

    public Inventory HelpVoteMenu() {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.chat("&a&lVote Help"));

        Utils.createItem(inv, Material.TRIPWIRE_HOOK, 1, 11, "&a&lVote Now!");
        Utils.createItem(inv, Material.DIAMOND_PICKAXE, 1, 14, "&a&lVote Ranks Info");
        Utils.createItem(inv, Material.BARRIER, 1, 17, "&4&lExit");
        Utils.makeDummySlots(inv);

        return inv;
    }

}

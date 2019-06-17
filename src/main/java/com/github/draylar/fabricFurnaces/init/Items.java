package com.github.draylar.fabricFurnaces.init;

import com.github.draylar.fabricFurnaces.furnaces.FurnaceItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Items
{
    private static final Item.Settings DEFAULT = new Item.Settings().itemGroup(ItemGroup.DECORATIONS);

    // default
    private static final Item FABRIC_FURNACE = new FurnaceItem(Blocks.FABRIC_FURNACE, DEFAULT);
    private static final Item IRON_FURNACE = new FurnaceItem(Blocks.IRON_FURNACE, DEFAULT);
    private static final Item GOLD_FURNACE = new FurnaceItem(Blocks.GOLD_FURNACE, DEFAULT);
    private static final Item DIAMOND_FURNACE = new FurnaceItem(Blocks.DIAMOND_FURNACE, DEFAULT);
    private static final Item OBSIDIAN_FURNACE = new FurnaceItem(Blocks.OBSIDIAN_FURNACE, DEFAULT);
    private static final Item NETHER_FURNACE = new FurnaceItem(Blocks.NETHER_FURNACE, DEFAULT);
    private static final Item EMERALD_FURNACE = new FurnaceItem(Blocks.EMERALD_FURNACE, DEFAULT);
    private static final Item END_FURNACE = new FurnaceItem(Blocks.END_FURNACE, DEFAULT);
    private static final Item ETHEREAL_FURNACE = new FurnaceItem(Blocks.ETHEREAL_FURNACE, DEFAULT);

    // crystal
    private static final Item CRYSTAL_FABRIC_FURNACE = new FurnaceItem(Blocks.CRYSTAL_FABRIC_FURNACE, DEFAULT);
    private static final Item CRYSTAL_IRON_FURNACE = new FurnaceItem(Blocks.CRYSTAL_IRON_FURNACE, DEFAULT);
    private static final Item CRYSTAL_GOLD_FURNACE = new FurnaceItem(Blocks.CRYSTAL_GOLD_FURNACE, DEFAULT);
    private static final Item CRYSTAL_DIAMOND_FURNACE = new FurnaceItem(Blocks.CRYSTAL_DIAMOND_FURNACE, DEFAULT);
    private static final Item CRYSTAL_OBSIDIAN_FURNACE = new FurnaceItem(Blocks.CRYSTAL_OBSIDIAN_FURNACE, DEFAULT);
    private static final Item CRYSTAL_NETHER_FURNACE = new FurnaceItem(Blocks.CRYSTAL_NETHER_FURNACE, DEFAULT);
    private static final Item CRYSTAL_EMERALD_FURNACE = new FurnaceItem(Blocks.CRYSTAL_EMERALD_FURNACE, DEFAULT);
    private static final Item CRYSTAL_END_FURNACE = new FurnaceItem(Blocks.CRYSTAL_END_FURNACE, DEFAULT);
    private static final Item CRYSTAL_ETHEREAL_FURNACE = new FurnaceItem(Blocks.CRYSTAL_ETHEREAL_FURNACE, DEFAULT);

    public static void register()
    {
        register("fabric_furnace", FABRIC_FURNACE);
        register("iron_furnace", IRON_FURNACE);
        register("gold_furnace", GOLD_FURNACE);
        register("diamond_furnace", DIAMOND_FURNACE);
        register("obsidian_furnace", OBSIDIAN_FURNACE);
        register("nether_furnace", NETHER_FURNACE);
        register("emerald_furnace", EMERALD_FURNACE);
        register("end_furnace", END_FURNACE);
        register("ethereal_furnace", ETHEREAL_FURNACE);

        register("crystal_fabric_furnace", CRYSTAL_FABRIC_FURNACE);
        register("crystal_iron_furnace", CRYSTAL_IRON_FURNACE);
        register("crystal_gold_furnace", CRYSTAL_GOLD_FURNACE);
        register("crystal_diamond_furnace", CRYSTAL_DIAMOND_FURNACE);
        register("crystal_obsidian_furnace", CRYSTAL_OBSIDIAN_FURNACE);
        register("crystal_nether_furnace", CRYSTAL_NETHER_FURNACE);
        register("crystal_emerald_furnace", CRYSTAL_EMERALD_FURNACE);
        register("crystal_end_furnace", CRYSTAL_END_FURNACE);
        register("crystal_ethereal_furnace", CRYSTAL_ETHEREAL_FURNACE);
    }

    private static void register(String name, Item item)
    {
        Registry.register(Registry.ITEM, new Identifier("fabric-furnaces", name), item);
    }
}

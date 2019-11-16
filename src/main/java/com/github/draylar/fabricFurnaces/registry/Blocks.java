package com.github.draylar.fabricFurnaces.registry;

import com.github.draylar.fabricFurnaces.block.BaseFurnaceBlock;
import com.github.draylar.fabricFurnaces.block.CrystalFurnaceBlock;
import com.github.draylar.fabricFurnaces.item.FurnaceItem;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class Blocks {

    public static final List<Block> allFurnaces = new ArrayList<>();
    public static final List<Block> crystalFurnaces = new ArrayList<>();

    public static void init() {
        registerFurnace("fabric_furnace", 1.5f, 1.5f, 0);
        registerFurnace("iron_furnace", 2, 2, 0);
        registerFurnace("gold_furnace", 3f, 2, 0);
        registerFurnace("diamond_furnace", 3.5f, 3f, 0);
        registerFurnace("obsidian_furnace", 4f, 2f, 0);
        registerFurnace("nether_furnace", 4.5f, 3.5f, 0);
        registerFurnace("emerald_furnace", 8f, 8f, 33);
        registerFurnace("end_furnace", 12f, 10f, 66);
        registerFurnace("ethereal_furnace", 32f, 16f, 100);
    }

    private static void registerFurnace(String name, float speed, float fuel, float dupe) {
        BaseFurnaceBlock baseFurnace = register(
                name,
                new BaseFurnaceBlock(
                        FabricBlockSettings.of(Material.STONE).hardness(3.5f).build(),
                        speed,
                        fuel,
                        dupe
                )
        );

        BaseFurnaceBlock crystalFurnace = register(
                "crystal_" + name,
                new CrystalFurnaceBlock(
                        FabricBlockSettings.of(Material.STONE).hardness(3.5f).build().nonOpaque(),
                        speed,
                        fuel,
                        dupe
                )
        );

        Registry.register(Registry.ITEM, new Identifier("fabric-furnaces", name), new FurnaceItem(baseFurnace, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Registry.register(Registry.ITEM, new Identifier("fabric-furnaces", "crystal_" + name), new FurnaceItem(crystalFurnace, new Item.Settings().group(ItemGroup.DECORATIONS)));

        crystalFurnaces.add(crystalFurnace);
        allFurnaces.add(crystalFurnace);
        allFurnaces.add(baseFurnace);
    }

    private static BaseFurnaceBlock register(String name, BaseFurnaceBlock block) {
        return Registry.register(Registry.BLOCK, new Identifier("fabric-furnaces", name), block);
    }

    public static List<Block> getFurnaces() {
        return allFurnaces;
    }

    public static List<Block> getCrystalFurnaces() {
        return crystalFurnaces;
    }
}




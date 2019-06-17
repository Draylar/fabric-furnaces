package com.github.draylar.fabricFurnaces.init;

import com.github.draylar.fabricFurnaces.furnaces.base.BaseFurnaceBlock;
import com.github.draylar.fabricFurnaces.furnaces.crystal.CrystalFurnaceBlock;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Blocks
{
    // default
    static final BaseFurnaceBlock FABRIC_FURNACE = new BaseFurnaceBlock(FabricBlockSettings.of(Material.STONE).hardness(3.5f).build(), 1.5f, 1.5f, 0);
    static final BaseFurnaceBlock IRON_FURNACE = new BaseFurnaceBlock(FabricBlockSettings.of(Material.STONE).hardness(3.5f).build(), 2, 2, 0);
    static final BaseFurnaceBlock GOLD_FURNACE = new BaseFurnaceBlock(FabricBlockSettings.of(Material.STONE).hardness(3.5f).build(), 3f, 2, 0);
    static final BaseFurnaceBlock DIAMOND_FURNACE = new BaseFurnaceBlock(FabricBlockSettings.of(Material.STONE).hardness(3.5f).build(), 3.5f, 3f, 0);
    static final BaseFurnaceBlock OBSIDIAN_FURNACE = new BaseFurnaceBlock(FabricBlockSettings.of(Material.STONE).hardness(3.5f).build(), 4f, 2f, 0);
    static final BaseFurnaceBlock NETHER_FURNACE = new BaseFurnaceBlock(FabricBlockSettings.of(Material.STONE).hardness(3.5f).build(), 4.5f, 3.5f, 0);
    static final BaseFurnaceBlock EMERALD_FURNACE = new BaseFurnaceBlock(FabricBlockSettings.of(Material.STONE).hardness(3.5f).build(), 8f, 8f, 33);
    static final BaseFurnaceBlock END_FURNACE = new BaseFurnaceBlock(FabricBlockSettings.of(Material.STONE).hardness(3.5f).build(), 12f, 10f, 66);
    static final BaseFurnaceBlock ETHEREAL_FURNACE = new BaseFurnaceBlock(FabricBlockSettings.of(Material.STONE).hardness(3.5f).build(), 32f, 16f, 100);

    // crystal
    static final CrystalFurnaceBlock CRYSTAL_FABRIC_FURNACE = new CrystalFurnaceBlock(FabricBlockSettings.of(Material.STONE).hardness(3.5f).sounds(BlockSoundGroup.GLASS).build(), 1.5f, 1.5f, 0);
    static final CrystalFurnaceBlock CRYSTAL_IRON_FURNACE = new CrystalFurnaceBlock(FabricBlockSettings.of(Material.STONE).hardness(3.5f).sounds(BlockSoundGroup.GLASS).build(), 2, 2, 0);
    static final CrystalFurnaceBlock CRYSTAL_GOLD_FURNACE = new CrystalFurnaceBlock(FabricBlockSettings.of(Material.STONE).hardness(3.5f).sounds(BlockSoundGroup.GLASS).build(), 2.25f, 2, 0);
    static final CrystalFurnaceBlock CRYSTAL_DIAMOND_FURNACE = new CrystalFurnaceBlock(FabricBlockSettings.of(Material.STONE).hardness(3.5f).sounds(BlockSoundGroup.GLASS).build(), 3.5f, 3f, 0);
    static final CrystalFurnaceBlock CRYSTAL_OBSIDIAN_FURNACE = new CrystalFurnaceBlock(FabricBlockSettings.of(Material.STONE).hardness(3.5f).sounds(BlockSoundGroup.GLASS).build(), 3.5f, 2f, 0);
    static final CrystalFurnaceBlock CRYSTAL_NETHER_FURNACE = new CrystalFurnaceBlock(FabricBlockSettings.of(Material.STONE).hardness(3.5f).sounds(BlockSoundGroup.GLASS).build(), 4.5f, 3.5f, 0);
    static final CrystalFurnaceBlock CRYSTAL_EMERALD_FURNACE = new CrystalFurnaceBlock(FabricBlockSettings.of(Material.STONE).hardness(3.5f).sounds(BlockSoundGroup.GLASS).build(), 8f, 5f, 33);
    static final CrystalFurnaceBlock CRYSTAL_END_FURNACE = new CrystalFurnaceBlock(FabricBlockSettings.of(Material.STONE).hardness(3.5f).sounds(BlockSoundGroup.GLASS).build(), 12f, 6f, 66);
    static final CrystalFurnaceBlock CRYSTAL_ETHEREAL_FURNACE = new CrystalFurnaceBlock(FabricBlockSettings.of(Material.STONE).hardness(3.5f).sounds(BlockSoundGroup.GLASS).build(), 32f, 10f, 100);
    
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

    private static void register(String name, Block block)
    {
        Registry.register(Registry.BLOCK, new Identifier("fabric-furnaces", name), block);
    }

    public static Block[] getRegularFurnaces() {
        return new Block[]{FABRIC_FURNACE,IRON_FURNACE,GOLD_FURNACE,DIAMOND_FURNACE,OBSIDIAN_FURNACE,NETHER_FURNACE,EMERALD_FURNACE,END_FURNACE,ETHEREAL_FURNACE};
    }

    public static Block[] getCrystalFurnaces() {
        return new Block[]{CRYSTAL_FABRIC_FURNACE,CRYSTAL_IRON_FURNACE,CRYSTAL_GOLD_FURNACE,CRYSTAL_DIAMOND_FURNACE,CRYSTAL_OBSIDIAN_FURNACE,CRYSTAL_NETHER_FURNACE,CRYSTAL_EMERALD_FURNACE,CRYSTAL_END_FURNACE,CRYSTAL_ETHEREAL_FURNACE};
    }
}




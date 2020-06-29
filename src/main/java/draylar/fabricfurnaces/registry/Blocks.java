package draylar.fabricfurnaces.registry;

import draylar.fabricfurnaces.FabricFurnaces;
import draylar.fabricfurnaces.block.BaseFurnaceBlock;
import draylar.fabricfurnaces.block.CrystalFurnaceBlock;
import draylar.fabricfurnaces.config.FurnaceData;
import draylar.fabricfurnaces.item.FurnaceItem;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class Blocks {

    public static final List<Block> allFurnaces = new ArrayList<>();
    public static final List<Block> crystalFurnaces = new ArrayList<>();
    public static final List<Block> regularFurnaces = new ArrayList<>();

    public static void init() {
        FabricFurnaces.CONFIG.furnaceData.forEach(Blocks::registerFurnace);
        FabricFurnaces.CONFIG.furnaceData.forEach(Blocks::registerCrystalFurnace);
    }

    private static void registerFurnace(FurnaceData data) {
        BaseFurnaceBlock baseFurnace = register(data.getName(), new BaseFurnaceBlock(FabricBlockSettings.of(Material.STONE).hardness(3.5f).build(), data.getSpeedModifier(), data.getFuelModifier(), data.getDuplicationChance()));
        Registry.register(Registry.ITEM, data.getID(), new FurnaceItem(baseFurnace, new Item.Settings().group(FabricFurnaces.GROUP)));
        regularFurnaces.add(baseFurnace);
        allFurnaces.add(baseFurnace);
    }

    private static void registerCrystalFurnace(FurnaceData data) {
        BaseFurnaceBlock crystalFurnace = register(String.format("crystal_%s", data.getName()), new CrystalFurnaceBlock(FabricBlockSettings.of(Material.STONE).nonOpaque().hardness(3.5f).build().nonOpaque(), data.getSpeedModifier(), data.getFuelModifier(), data.getDuplicationChance()));
        Registry.register(Registry.ITEM, FabricFurnaces.id(String.format("crystal_%s", data.getName())), new FurnaceItem(crystalFurnace, new Item.Settings().group(FabricFurnaces.GROUP)));
        crystalFurnaces.add(crystalFurnace);
        allFurnaces.add(crystalFurnace);
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




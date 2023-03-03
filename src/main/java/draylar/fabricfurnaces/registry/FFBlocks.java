package draylar.fabricfurnaces.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

import draylar.fabricfurnaces.FabricFurnaces;
import draylar.fabricfurnaces.block.CrystalFurnaceBlock;
import draylar.fabricfurnaces.block.FabricFurnaceBlock;
import draylar.fabricfurnaces.config.FurnaceData;
import draylar.fabricfurnaces.item.FurnaceItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

public class FFBlocks {

    public static final List<Block> allFurnaces = new ArrayList<>();
    public static final List<Block> crystalFurnaces = new ArrayList<>();
    public static final List<Block> regularFurnaces = new ArrayList<>();

    public static void init() {
        FabricFurnaces.CONFIG.furnaceData.forEach(FFBlocks::registerFurnace);
        FabricFurnaces.CONFIG.furnaceData.forEach(FFBlocks::registerCrystalFurnace);
    }

    private static void registerFurnace(FurnaceData data) {
        FabricFurnaceBlock baseFurnace = register(data.getName(), new FabricFurnaceBlock(FabricBlockSettings.of(Material.STONE).hardness(3.5f).luminance(createLightLevelFromBlockState(13)), data.getSpeedModifier(), data.getFuelModifier(), data.getDuplicationChance()));
        FurnaceItem furnaceItem = new FurnaceItem(baseFurnace, new Item.Settings());
        ItemGroupEvents.modifyEntriesEvent(FabricFurnaces.GROUP).register(entries -> entries.add(furnaceItem));
        Registry.register(Registries.ITEM, data.getID(), furnaceItem);
        regularFurnaces.add(baseFurnace);
        allFurnaces.add(baseFurnace);
    }

    private static void registerCrystalFurnace(FurnaceData data) {
        FabricFurnaceBlock crystalFurnace = register(String.format("crystal_%s", data.getName()), new CrystalFurnaceBlock(FabricBlockSettings.of(Material.STONE).nonOpaque().hardness(3.5f).luminance(createLightLevelFromBlockState(13)).nonOpaque(), data.getSpeedModifier(), data.getFuelModifier(), data.getDuplicationChance()));
        FurnaceItem furnaceItem = new FurnaceItem(crystalFurnace, new Item.Settings());
        ItemGroupEvents.modifyEntriesEvent(FabricFurnaces.GROUP).register(entries -> entries.add(furnaceItem));
        Registry.register(Registries.ITEM, FabricFurnaces.id(String.format("crystal_%s", data.getName())), furnaceItem);
        crystalFurnaces.add(crystalFurnace);
        allFurnaces.add(crystalFurnace);
    }

    private static FabricFurnaceBlock register(String name, FabricFurnaceBlock block) {
        return Registry.register(Registries.BLOCK, new Identifier("fabric-furnaces", name), block);
    }

    public static List<Block> getFurnaces() {
        return allFurnaces;
    }

    public static List<Block> getCrystalFurnaces() {
        return crystalFurnaces;
    }

    private static ToIntFunction<BlockState> createLightLevelFromBlockState(int litLevel) {
        return (blockState) -> (Boolean)blockState.get(Properties.LIT) ? litLevel : 0;
    }
}




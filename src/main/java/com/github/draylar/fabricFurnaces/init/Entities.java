package com.github.draylar.fabricFurnaces.init;

import com.github.draylar.fabricFurnaces.furnaces.base.BaseFurnaceEntity;
import com.github.draylar.fabricFurnaces.furnaces.crystal.CrystalFurnaceEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Entities
{
    public static BlockEntityType<BaseFurnaceEntity> FABRIC_FURNACE = BlockEntityType.Builder.create(() -> new BaseFurnaceEntity(Entities.FABRIC_FURNACE, 1, 1, 0), Blocks.getRegularFurnaces()).build(null);
    public static BlockEntityType<CrystalFurnaceEntity> CRYSTAL_FURNACE = BlockEntityType.Builder.create(() -> new CrystalFurnaceEntity(1, 1, 0), Blocks.getCrystalFurnaces()).build(null);

    public static void register()
    {
        register("fabric_furnace", FABRIC_FURNACE);
        register("crystal_furnace", CRYSTAL_FURNACE);
    }

    private static void register(String name, BlockEntityType<? extends BaseFurnaceEntity> type)
    {
        Registry.register(Registry.BLOCK_ENTITY, new Identifier("fabric-furnaces", name), type);
    }
}

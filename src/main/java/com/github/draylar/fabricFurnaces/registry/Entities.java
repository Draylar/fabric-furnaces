package com.github.draylar.fabricFurnaces.registry;

import com.github.draylar.fabricFurnaces.entity.BaseFurnaceEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.ArrayUtils;

public class Entities {

    public static BlockEntityType<BaseFurnaceEntity> FABRIC_FURNACE = BlockEntityType.Builder.create(() ->
            new BaseFurnaceEntity(1, 1, 0),
            Blocks.getFurnaces().toArray(new Block[0])
    ).build(null);

    public static void init() {
        register("fabric_furnace", FABRIC_FURNACE);
    }

    private static void register(String name, BlockEntityType<? extends BaseFurnaceEntity> type) {
        Registry.register(Registry.BLOCK_ENTITY, new Identifier("fabric-furnaces", name), type);
    }
}

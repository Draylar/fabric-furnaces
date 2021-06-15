package draylar.fabricfurnaces.registry;

import draylar.fabricfurnaces.FabricFurnaces;
import draylar.fabricfurnaces.entity.FabricFurnaceEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class FFEntities {

    public static BlockEntityType<FabricFurnaceEntity> FABRIC_FURNACE = register(
            "fabric_furnace",
            FabricBlockEntityTypeBuilder.create(FabricFurnaceEntity::new,
                    FFBlocks.getFurnaces().toArray(new Block[0])
            ).build(null));

    public static void init() {
        // no-op
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, FabricFurnaces.id(name), type);
    }
}

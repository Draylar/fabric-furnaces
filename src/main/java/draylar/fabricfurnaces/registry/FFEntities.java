package draylar.fabricfurnaces.registry;

import draylar.fabricfurnaces.FabricFurnaces;
import draylar.fabricfurnaces.entity.BaseFurnaceEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class FFEntities {

    public static BlockEntityType<BaseFurnaceEntity> FABRIC_FURNACE = register(
            "fabric_furnace",
            BlockEntityType.Builder.create(() ->
                            new BaseFurnaceEntity(1, 1, 0),
                    FFBlocks.getFurnaces().toArray(new Block[0])
            ).build(null)
    );

    public static void init() {
        // no-op
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, FabricFurnaces.id(name), type);
    }
}

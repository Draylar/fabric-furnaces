package com.github.draylar.fabricFurnaces.furnaces.crystal;

import com.github.draylar.fabricFurnaces.furnaces.base.BaseFurnaceBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class CrystalFurnaceBlock extends BaseFurnaceBlock
{
    public CrystalFurnaceBlock(Settings block$Settings_1, float speedMultiplier, float fuelMultiplier, float dupeChance100)
    {
        super(block$Settings_1, speedMultiplier, fuelMultiplier, dupeChance100);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView_1) {
        return new CrystalFurnaceEntity(speed, fuel, dupe);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isTranslucent(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return true;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isSideInvisible(BlockState blockState_1, BlockState blockState_2, Direction direction_1) {
        return blockState_2.getBlock() == this ? true : super.isSideInvisible(blockState_1, blockState_2, direction_1);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public float getAmbientOcclusionLightLevel(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return 1.0F;
    }

    @Override
    public boolean isSimpleFullBlock(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return false;
    }
}

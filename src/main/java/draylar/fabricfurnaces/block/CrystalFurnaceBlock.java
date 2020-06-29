package draylar.fabricfurnaces.block;

import draylar.fabricfurnaces.entity.BaseFurnaceEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class CrystalFurnaceBlock extends BaseFurnaceBlock {

    public CrystalFurnaceBlock(Settings settings, float speedMultiplier, float fuelMultiplier, float dupeChance100) {
        super(settings, speedMultiplier, fuelMultiplier, dupeChance100);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView_1) {
        return new BaseFurnaceEntity(speed, fuel, dupe);
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView view, BlockPos pos) {
        return true;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState neighbor, Direction facing) {
        return neighbor.getBlock() == this || super.isSideInvisible(state, neighbor, facing);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView view, BlockPos pos) {
        return 1.0F;
    }
}
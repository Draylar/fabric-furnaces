package draylar.fabricfurnaces.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class CrystalFurnaceBlock extends FabricFurnaceBlock {

    public CrystalFurnaceBlock(Settings settings, double speedMultiplier, double fuelMultiplier, int dupeChance100) {
        super(settings, speedMultiplier, fuelMultiplier, dupeChance100);
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
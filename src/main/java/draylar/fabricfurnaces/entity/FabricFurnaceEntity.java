package draylar.fabricfurnaces.entity;

import draylar.fabricfurnaces.block.FabricFurnaceBlock;
import draylar.fabricfurnaces.registry.FFEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class FabricFurnaceEntity extends AbstractFurnaceBlockEntity {

    public FabricFurnaceEntity(BlockPos pos, BlockState state) {
        super(FFEntities.FABRIC_FURNACE, pos, state, RecipeType.SMELTING);
    }

    @Override
    public int getFuelTime(ItemStack fuel) {
        return (int) (super.getFuelTime(fuel) / getFuelModifier());
    }

    @Override
    public Text getContainerName() {
        return Text.translatable("container.furnace");
    }

    @Override
    public ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new FurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public double getSpeedModifier() {
        if(getCachedState().getBlock() instanceof FabricFurnaceBlock) {
            return ((FabricFurnaceBlock) getCachedState().getBlock()).getSpeedModifier();
        }

        return 1;
    }

    public double getFuelModifier() {
        if(getCachedState().getBlock() instanceof FabricFurnaceBlock) {
            return ((FabricFurnaceBlock) getCachedState().getBlock()).getFuelModifier();
        }

        return 1;
    }

    public int getDuplicationChance() {
        if(getCachedState().getBlock() instanceof FabricFurnaceBlock) {
            return ((FabricFurnaceBlock) getCachedState().getBlock()).getDuplicationChance();
        }

        return 0;
    }
}

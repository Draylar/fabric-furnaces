package draylar.fabricfurnaces.mixin;

import draylar.fabricfurnaces.entity.FabricFurnaceEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceEntityMixin extends BlockEntity {

    @Unique private static final Random ff_random = new Random();
    @Unique @Nullable private static AbstractFurnaceBlockEntity ff_entityContext = null;

    private AbstractFurnaceEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD"))
    private static void resetCraftingContext(World world, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity, CallbackInfo ci) {
        ff_entityContext = blockEntity;
    }

    @Inject(
            method = "craftRecipe",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;increment(I)V", shift = At.Shift.AFTER))
    private static void doubleIncrementOutput(Recipe<?> recipe, DefaultedList<ItemStack> slots, int count, CallbackInfoReturnable<Boolean> cir) {
        if(ff_entityContext instanceof FabricFurnaceEntity) {
            boolean doubled = ff_random.nextInt(100) < ((FabricFurnaceEntity) ff_entityContext).getDuplicationChance();
            if(doubled) {
                slots.get(2).increment(1);
            }
        }
    }

    @Inject(
            method = "craftRecipe",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/DefaultedList;set(ILjava/lang/Object;)Ljava/lang/Object;", ordinal = 0, shift = At.Shift.AFTER))
    private static void doubleSetOutput(Recipe<?> recipe, DefaultedList<ItemStack> slots, int count, CallbackInfoReturnable<Boolean> cir) {
        if(ff_entityContext instanceof FabricFurnaceEntity) {
            boolean doubled = ff_random.nextInt(100) < ((FabricFurnaceEntity) ff_entityContext).getDuplicationChance();
            if(doubled) {
                slots.get(2).increment(1);
            }
        }
    }

    @Inject(
            method = "getCookTime",
            at = @At("RETURN"), cancellable = true)
    private static void modifyCookTime(World world, AbstractFurnaceBlockEntity furnace, CallbackInfoReturnable<Integer> cir) {
        Integer original = cir.getReturnValue();

        if(ff_entityContext instanceof FabricFurnaceEntity) {
            cir.setReturnValue((int) (original / ((FabricFurnaceEntity) ff_entityContext).getSpeedModifier()));
        }
    }
}

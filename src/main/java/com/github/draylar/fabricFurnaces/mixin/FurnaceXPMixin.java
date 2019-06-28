package com.github.draylar.fabricFurnaces.mixin;

import com.github.draylar.fabricFurnaces.furnaces.base.BaseFurnaceEntity;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.container.FurnaceOutputSlot;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FurnaceOutputSlot.class)
public abstract class FurnaceXPMixin extends Slot
{
    @Shadow @Final private PlayerEntity player;

    public FurnaceXPMixin(Inventory inventory_1, int int_1, int int_2, int int_3)
    {
        super(inventory_1, int_1, int_2, int_3);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;onCraft(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;I)V"), method = "onCrafted(Lnet/minecraft/item/ItemStack;)V")
    private void craft(ItemStack itemStack_1, CallbackInfo ci)
    {
        if (!this.player.world.isClient && this.inventory instanceof BaseFurnaceEntity) {
            ((BaseFurnaceEntity)this.inventory).dropExperience(this.player);
        }
    }
}

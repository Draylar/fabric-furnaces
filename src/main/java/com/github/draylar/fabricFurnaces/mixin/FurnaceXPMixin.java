package com.github.draylar.fabricFurnaces.mixin;

import com.github.draylar.fabricFurnaces.entity.BaseFurnaceEntity;
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
public abstract class FurnaceXPMixin extends Slot {

    @Shadow
    @Final
    private PlayerEntity player;

    public FurnaceXPMixin(Inventory inventory, int invSlot, int xPosition, int yPosition) {
        super(inventory, invSlot, xPosition, yPosition);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;onCraft(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;I)V"), method = "onCrafted(Lnet/minecraft/item/ItemStack;)V")
    private void craft(ItemStack stack, CallbackInfo ci) {
        if (!this.player.world.isClient && this.inventory instanceof BaseFurnaceEntity) {
            ((BaseFurnaceEntity) this.inventory).dropExperience(this.player);
        }
    }
}

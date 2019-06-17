package com.github.draylar.fabricFurnaces.furnaces;

import com.github.draylar.fabricFurnaces.furnaces.base.BaseFurnaceBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.World;

import java.util.List;

public class FurnaceItem extends BlockItem
{
    private BaseFurnaceBlock block;

    public FurnaceItem(BaseFurnaceBlock block, Settings settings)
    {
        super(block, settings);
        this.block = block;
    }

    @Override
    public void buildTooltip(ItemStack stack, World world, List<Component> list, TooltipContext context)
    {
        // add top label section
        list.add(new TextComponent(""));
        list.add(new TranslatableComponent("fabric-furnaces.text.tooltiplabel"));

        // add stats
        list.add(new TextComponent(" " + new TranslatableComponent("fabric-furnaces.text.speedlabel").getText() + block.getSpeedMultiplier() + "x"));
        list.add(new TextComponent(" " + new TranslatableComponent("fabric-furnaces.text.fuellabel").getText() + block.getFuelMultiplier() + "x"));


        if(block.getDupeChance() > 0)
        {
            list.add(new TextComponent(" " + new TranslatableComponent("fabric-furnaces.text.dupelabel").getText() + block.getDupeChance() + "%"));
        }

        super.buildTooltip(stack, world, list, context);
    }
}

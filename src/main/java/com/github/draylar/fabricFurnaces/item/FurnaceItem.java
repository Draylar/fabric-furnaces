package com.github.draylar.fabricFurnaces.item;

import com.github.draylar.fabricFurnaces.block.BaseFurnaceBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

import java.util.List;

public class FurnaceItem extends BlockItem {

    private BaseFurnaceBlock block;

    public FurnaceItem(BaseFurnaceBlock block, Settings settings) {
        super(block, settings);
        this.block = block;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> list, TooltipContext context) {
        // add top label section
        list.add(new LiteralText(""));
        list.add(new TranslatableText("fabric-furnaces.text.tooltiplabel"));

        // add stats
        list.add(new LiteralText(" " + new TranslatableText("fabric-furnaces.text.speedlabel").asFormattedString() + block.getSpeedMultiplier() + "x"));
        list.add(new LiteralText(" " + new TranslatableText("fabric-furnaces.text.fuellabel").asFormattedString() + block.getFuelMultiplier() + "x"));


        if (block.getDupeChance() > 0) {
            list.add(new LiteralText(" " + new TranslatableText("fabric-furnaces.text.dupelabel").asFormattedString() + block.getDupeChance() + "%"));
        }

        super.appendTooltip(stack, world, list, context);
    }
}

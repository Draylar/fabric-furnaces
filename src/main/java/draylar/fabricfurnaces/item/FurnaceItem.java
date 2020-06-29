package draylar.fabricfurnaces.item;

import draylar.fabricfurnaces.block.BaseFurnaceBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class FurnaceItem extends BlockItem {

    private final BaseFurnaceBlock block;

    public FurnaceItem(BaseFurnaceBlock block, Settings settings) {
        super(block, settings);
        this.block = block;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> list, TooltipContext context) {
        // add top label section
        list.add(new LiteralText(""));
        list.add(new TranslatableText("fabric-furnaces.text.tooltiplabel").formatted(Formatting.GRAY));

        // add stats
        list.add(new TranslatableText("fabric-furnaces.text.speedlabel", block.getSpeedMultiplier()).formatted(Formatting.DARK_GREEN));
        list.add(new TranslatableText("fabric-furnaces.text.fuellabel", block.getFuelMultiplier()).formatted(Formatting.DARK_GREEN));

        if (block.getDupeChance() > 0) {
            list.add(new TranslatableText("fabric-furnaces.text.dupelabel", block.getDupeChance()).formatted(Formatting.DARK_GREEN).append(new LiteralText("%").formatted(Formatting.DARK_GREEN)));
        }

        super.appendTooltip(stack, world, list, context);
    }
}

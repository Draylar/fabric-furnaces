package draylar.fabricfurnaces.item;

import draylar.fabricfurnaces.block.FabricFurnaceBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class FurnaceItem extends BlockItem {

    private final FabricFurnaceBlock block;

    public FurnaceItem(FabricFurnaceBlock block, Settings settings) {
        super(block, settings);
        this.block = block;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> list, TooltipContext context) {
        // add top label section
        list.add(Text.of(""));
        list.add(Text.translatable("fabric-furnaces.text.tooltiplabel").formatted(Formatting.GRAY));

        // add stats
        list.add(Text.translatable("fabric-furnaces.text.speedlabel", block.getSpeedModifier()).formatted(Formatting.DARK_GREEN));
        list.add(Text.translatable("fabric-furnaces.text.fuellabel", block.getFuelModifier()).formatted(Formatting.DARK_GREEN));

        if (block.getDuplicationChance() > 0) {
            list.add(Text.translatable("fabric-furnaces.text.dupelabel", block.getDuplicationChance()).formatted(Formatting.DARK_GREEN).append(Text.of("%")));
        }

        super.appendTooltip(stack, world, list, context);
    }
}

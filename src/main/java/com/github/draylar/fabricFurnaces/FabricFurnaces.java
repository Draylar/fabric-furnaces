package com.github.draylar.fabricFurnaces;

import com.github.draylar.fabricFurnaces.init.Blocks;
import com.github.draylar.fabricFurnaces.init.Entities;
import com.github.draylar.fabricFurnaces.init.Items;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

public class FabricFurnaces implements ModInitializer
{
	@Override
	public void onInitialize()
	{
		Entities.register();
		Blocks.register();
		Items.register();

		if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			for(Block block : Blocks.getCrystalFurnaces()) {
				BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
			}
		}
	}
}

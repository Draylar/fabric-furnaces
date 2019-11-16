package com.github.draylar.fabricFurnaces;

import com.github.draylar.fabricFurnaces.registry.Blocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class FabricFurnacesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Blocks.getCrystalFurnaces().forEach(block -> {
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
        });
    }
}

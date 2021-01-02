package draylar.fabricfurnaces;

import draylar.fabricfurnaces.registry.FFBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class FabricFurnacesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FFBlocks.getCrystalFurnaces().forEach(block -> {
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
        });
    }
}

package draylar.fabricfurnaces;

import draylar.fabricfurnaces.config.FabricFurnacesConfig;
import draylar.fabricfurnaces.registry.FFBlocks;
import draylar.fabricfurnaces.registry.FFEntities;
import draylar.omegaconfig.OmegaConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class FabricFurnaces implements ModInitializer {

	public static final FabricFurnacesConfig CONFIG = OmegaConfig.register(FabricFurnacesConfig.class);
	public static final ItemGroup GROUP = FabricItemGroup.builder(id("group"))
	.icon(() -> new ItemStack(Registries.ITEM.get(id("fabric_furnace"))))
	.build();

	@Override
	public void onInitialize() {
		FFBlocks.init();
		FFEntities.init();
	}

	public static Identifier id(String name) {
		return new Identifier("fabric-furnaces", name);
	}
}
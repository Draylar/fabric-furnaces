package draylar.fabricfurnaces;

import draylar.fabricfurnaces.config.FabricFurnacesConfig;
import draylar.fabricfurnaces.registry.FFBlocks;
import draylar.fabricfurnaces.registry.FFEntities;
import draylar.omegaconfig.OmegaConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FabricFurnaces implements ModInitializer {

	public static final FabricFurnacesConfig CONFIG = OmegaConfig.register(FabricFurnacesConfig.class);
	public static final ItemGroup GROUP = FabricItemGroupBuilder.build(id("group"), () -> new ItemStack(Registry.ITEM.get(id("fabric_furnace"))));

	@Override
	public void onInitialize() {
		FFBlocks.init();
		FFEntities.init();
	}

	public static Identifier id(String name) {
		return new Identifier("fabric-furnaces", name);
	}
}
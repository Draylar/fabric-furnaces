package draylar.fabricfurnaces;

import draylar.fabricfurnaces.config.FabricFurnacesConfig;
import draylar.fabricfurnaces.registry.Blocks;
import draylar.fabricfurnaces.registry.Entities;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Jankson;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FabricFurnaces implements ModInitializer {

	public static final FabricFurnacesConfig CONFIG = AutoConfig.register(FabricFurnacesConfig.class, JanksonConfigSerializer::new).getConfig();
	public static final ItemGroup GROUP = FabricItemGroupBuilder.build(id("group"), () -> new ItemStack(Registry.ITEM.get(id("fabric_furnace"))));

	@Override
	public void onInitialize() {
		Blocks.init();
		Entities.init();
	}

	public static Identifier id(String name) {
		return new Identifier("fabric-furnaces", name);
	}
}
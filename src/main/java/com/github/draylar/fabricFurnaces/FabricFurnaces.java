package com.github.draylar.fabricFurnaces;

import com.github.draylar.fabricFurnaces.registry.Blocks;
import com.github.draylar.fabricFurnaces.registry.Entities;
import net.fabricmc.api.ModInitializer;

public class FabricFurnaces implements ModInitializer {

	@Override
	public void onInitialize() {
		Blocks.init();
		Entities.init();
	}
}
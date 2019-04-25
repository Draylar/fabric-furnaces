package com.github.draylar.fabricFurnaces;

import com.github.draylar.fabricFurnaces.init.Blocks;
import com.github.draylar.fabricFurnaces.init.Entities;
import com.github.draylar.fabricFurnaces.init.Items;
import net.fabricmc.api.ModInitializer;

public class FabricFurnaces implements ModInitializer
{
	@Override
	public void onInitialize()
	{
		Entities.register();
		Blocks.register();
		Items.register();
	}
}

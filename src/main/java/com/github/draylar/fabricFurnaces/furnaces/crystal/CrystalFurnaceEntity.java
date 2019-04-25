package com.github.draylar.fabricFurnaces.furnaces.crystal;

import com.github.draylar.fabricFurnaces.furnaces.base.BaseFurnaceEntity;
import com.github.draylar.fabricFurnaces.init.Entities;

public class CrystalFurnaceEntity extends BaseFurnaceEntity
{
    public CrystalFurnaceEntity(float speedMultiplier, float fuelMultiplier, float duplicateChanceOutOf100)
    {
        super(Entities.CRYSTAL_FURNACE, speedMultiplier, fuelMultiplier, duplicateChanceOutOf100);
    }
}

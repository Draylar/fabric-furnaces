package draylar.fabricfurnaces.config;

import draylar.fabricfurnaces.FabricFurnaces;
import net.minecraft.util.Identifier;

public class FurnaceData {

    private final String name;
    private final float speedModifier;
    private final float fuelModifier;
    private final float duplicationChance;

    public FurnaceData(String name, float speedModifier, float fuelModifier, float duplicationChance) {
        this.name = name;
        this.speedModifier = speedModifier;
        this.fuelModifier = fuelModifier;
        this.duplicationChance = duplicationChance;
    }

    public String getName() {
        return String.format("%s_furnace", name);
    }

    public Identifier getID() {
        return FabricFurnaces.id(getName());
    }

    public float getSpeedModifier() {
        return speedModifier;
    }

    public float getFuelModifier() {
        return fuelModifier;
    }

    public float getDuplicationChance() {
        return duplicationChance;
    }

    public static FurnaceData of(String name, float speedModifier, float fuelModifier, float duplicationChance) {
        return new FurnaceData(name, speedModifier, fuelModifier, duplicationChance);
    }
}

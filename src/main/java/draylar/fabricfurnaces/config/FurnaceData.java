package draylar.fabricfurnaces.config;

import draylar.fabricfurnaces.FabricFurnaces;
import net.minecraft.util.Identifier;

public class FurnaceData {

    private final String name;
    private final double speedModifier;
    private final double fuelModifier;
    private final int duplicationChance;

    public FurnaceData(String name, double speedModifier, double fuelModifier, int duplicationChance) {
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

    public double getSpeedModifier() {
        return speedModifier;
    }

    public double getFuelModifier() {
        return fuelModifier;
    }

    public int getDuplicationChance() {
        return duplicationChance;
    }

    public static FurnaceData of(String name, double speedModifier, double fuelModifier, int duplicationChance) {
        return new FurnaceData(name, speedModifier, fuelModifier, duplicationChance);
    }
}

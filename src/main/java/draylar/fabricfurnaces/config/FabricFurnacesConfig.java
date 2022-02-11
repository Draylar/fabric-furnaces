package draylar.fabricfurnaces.config;

import draylar.omegaconfig.api.Comment;
import draylar.omegaconfig.api.Config;

import java.util.Arrays;
import java.util.List;

public class FabricFurnacesConfig implements Config {

    @Comment(value = "List of furnaces that will be registered when the game starts. WARNING: modifying this file may ruin Fabric Furnace blocks and the contents they contain. Remember to take backups before removing furnaces.")
    public List<FurnaceData> furnaceData = Arrays.asList(
            FurnaceData.of("fabric", 1.5f, 1.5f, 0),
            FurnaceData.of("iron", 2, 2, 0),
            FurnaceData.of("gold", 3f, 2, 0),
            FurnaceData.of("diamond", 3.5f, 3f, 0),
            FurnaceData.of("obsidian", 4f, 2f, 0),
            FurnaceData.of("nether", 4.5f, 3.5f, 0),
            FurnaceData.of("emerald", 8f, 8f, 33),
            FurnaceData.of("end", 12f, 10f, 66),
            FurnaceData.of("ethereal", 32f, 16f, 100)
    );

    @Override
    public String getName() {
        return "fabric-furnaces";
    }

    @Override
    public String getExtension() {
        return "json5";
    }
}

package draylar.fabricfurnaces.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;

import java.util.Arrays;
import java.util.List;

@Config(name = "fabric-furnaces")
public class FabricFurnacesConfig implements ConfigData {

    @Comment(value = "List of furnaces that will be registered when the game starts.\nWARNING: modifying this file may ruin Fabric Furnace blocks and the contents they contain.\nRemember to take backups before removing furnaces.")
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
}

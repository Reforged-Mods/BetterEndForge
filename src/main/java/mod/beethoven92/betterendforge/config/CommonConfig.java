package mod.beethoven92.betterendforge.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig 
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
   
    public static final ForgeConfigSpec.ConfigValue<Integer> BIOME_SIZE_LAND;
    public static final ForgeConfigSpec.ConfigValue<Integer> BIOME_SIZE_VOID;
    public static final ForgeConfigSpec.BooleanValue DRAGON_FIGHT_ENABLED;
    public static final ForgeConfigSpec.BooleanValue GENERATE_VANILLA_PORTAL;
    public static final ForgeConfigSpec.BooleanValue GENERATE_OBSIDIAN_PILLARS;
	public static final ForgeConfigSpec.BooleanValue CUSTOM_CHORUS_PLANT_ENABLED;
	public static final ForgeConfigSpec.BooleanValue CHORUS_IN_VANILLA_BIOMES_ENABLED;
    public static final ForgeConfigSpec.BooleanValue NEW_GENERATOR_ENABLED;
    public static final ForgeConfigSpec.BooleanValue NO_RING_VOID;
    public static final ForgeConfigSpec.BooleanValue GENERATE_CENTRAL_ISLAND;
    public static final ForgeConfigSpec.ConfigValue<Integer> END_CITY_FAIL_CHANCE;
    
    static 
    {
        BUILDER.push("Common settings");
        
        BUILDER.comment("\nLand biome map size.\nDefault value: 256");
        BIOME_SIZE_LAND = BUILDER.define("biomeSizeLand", 256);
       
        BUILDER.comment("\nVoid biome map size.\nDefault value: 256");
        BIOME_SIZE_VOID = BUILDER.define("biomeSizeVoid", 256);
        
        BUILDER.comment("\nEnable/disable dragon fight.\nDefault value: true");
        DRAGON_FIGHT_ENABLED = BUILDER.define("dragonFightEnabled", true);

        BUILDER.comment("\nEnable/disable vanilla portal generation.\nDefault value: true");
        GENERATE_VANILLA_PORTAL = BUILDER.define("generateVanillaPortal", true);
        
        BUILDER.comment("\nEnable/disable obsidian pillars generation.\nDefault value: true");
        GENERATE_OBSIDIAN_PILLARS = BUILDER.define("generateObsidianPillars", true);
       
        BUILDER.comment("\nEnable/disable custom chorus plant.\nDefault value: true");
        CUSTOM_CHORUS_PLANT_ENABLED = BUILDER.define("customChorusPlantEnabled", true);
        
        BUILDER.comment("\nEnable/disable chorus generation in vanilla biomes.\nDefault value: true");
        CHORUS_IN_VANILLA_BIOMES_ENABLED = BUILDER.define("chorusInVanillaBiomesEnabled", true);
        
        BUILDER.comment("\nEnable/disable new terrain generation.\nDefault value: false");
        NEW_GENERATOR_ENABLED = BUILDER.define("enableNewGenerator", false);

        BUILDER.comment("\nEnable/disable the void ring around central island.\nDefault value: false");
        NO_RING_VOID = BUILDER.define("noRingVoid", false);
        
        BUILDER.comment("\nEnable/disable generation of the central island.\nDefault value: false");
        GENERATE_CENTRAL_ISLAND = BUILDER.define("generateCentralIsland", false);
        
        BUILDER.comment("\nSet the chance for end city generation to fail. Higher values means lower chance of spawning\nDefault value: 5");
        END_CITY_FAIL_CHANCE = BUILDER.define("endCityGenerationFailChance", 5);
        
        BUILDER.pop();
        
        COMMON_CONFIG = BUILDER.build();
    }
    
    public static ForgeConfigSpec getConfig() 
    {
        return COMMON_CONFIG;
    }
    
    public static int biomeSizeLand()
    {
    	return BIOME_SIZE_LAND.get();
    }
    
    public static int biomeSizeVoid()
    {
    	return BIOME_SIZE_VOID.get();
    }
    
    public static boolean isDragonFightEnabled()
    {
    	return DRAGON_FIGHT_ENABLED.get();
    }
    
    public static boolean shouldGenerateVanillaPortal()
    {
    	return GENERATE_VANILLA_PORTAL.get();
    }
    
    public static boolean shouldGenerateObsidianPillars()
    {
    	return GENERATE_OBSIDIAN_PILLARS.get();
    }
    
    public static boolean isCustomChorusPlantEnabled()
    {
    	return CUSTOM_CHORUS_PLANT_ENABLED.get();
    }
    
    public static boolean isChorusInVanillaBiomesEnabled()
    {
    	return CHORUS_IN_VANILLA_BIOMES_ENABLED.get();
    }
    
    public static boolean isNewGeneratorEnabled() 
    {
        return NEW_GENERATOR_ENABLED.get();
    }
    
    public static boolean noRingVoid() 
    {
        return NO_RING_VOID.get();
    }
    
    public static boolean shouldGenerateCentralIsland() 
    {
        return GENERATE_CENTRAL_ISLAND.get();
    }
    
    public static int endCityFailChance() 
    {
        return END_CITY_FAIL_CHANCE.get();
    }
}
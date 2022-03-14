package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.entity.CubozoaEntity;
import mod.beethoven92.betterendforge.common.entity.DragonflyEntity;
import mod.beethoven92.betterendforge.common.entity.EndFishEntity;
import mod.beethoven92.betterendforge.common.entity.EndSlimeEntity;
import mod.beethoven92.betterendforge.common.entity.ShadowWalkerEntity;
import mod.beethoven92.betterendforge.common.entity.SilkMothEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID, bus = Bus.MOD)
public class ModEntityTypes 
{
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = 
			DeferredRegister.create(ForgeRegistries.ENTITIES, BetterEnd.MOD_ID);

	public static final RegistryObject<EntityType<EndFishEntity>> END_FISH = ENTITY_TYPES.register("end_fish", 
			() -> EntityType.Builder.<EndFishEntity>of(EndFishEntity::new, MobCategory.WATER_AMBIENT).
			sized(0.5f, 0.5f).
			build(new ResourceLocation(BetterEnd.MOD_ID, "end_fish").toString()));
	
	public static final RegistryObject<EntityType<DragonflyEntity>> DRAGONFLY = ENTITY_TYPES.register("dragonfly", 
			() -> EntityType.Builder.<DragonflyEntity>of(DragonflyEntity::new, MobCategory.AMBIENT).
			sized(0.6f, 0.5f).
			build(new ResourceLocation(BetterEnd.MOD_ID, "dragonfly").toString()));
	
	public static final RegistryObject<EntityType<ShadowWalkerEntity>> SHADOW_WALKER = ENTITY_TYPES.register("shadow_walker", 
			() -> EntityType.Builder.<ShadowWalkerEntity>of(ShadowWalkerEntity::new, MobCategory.MONSTER).
			sized(0.6f, 1.95f).
			build(new ResourceLocation(BetterEnd.MOD_ID, "shadow_walker").toString()));
	
	public static final RegistryObject<EntityType<EndSlimeEntity>> END_SLIME = ENTITY_TYPES.register("end_slime", 
			() -> EntityType.Builder.<EndSlimeEntity>of(EndSlimeEntity::new, MobCategory.MONSTER).
			sized(2, 2).
			build(new ResourceLocation(BetterEnd.MOD_ID, "end_slime").toString()));
	
	public static final RegistryObject<EntityType<CubozoaEntity>> CUBOZOA = ENTITY_TYPES.register("cubozoa", 
			() -> EntityType.Builder.<CubozoaEntity>of(CubozoaEntity::new, MobCategory.WATER_AMBIENT).
			sized(0.6f, 1f).
			build(new ResourceLocation(BetterEnd.MOD_ID, "cubozoa").toString()));
	
	public static final RegistryObject<EntityType<SilkMothEntity>> SILK_MOTH = ENTITY_TYPES.register("silk_moth", 
			() -> EntityType.Builder.<SilkMothEntity>of(SilkMothEntity::new, MobCategory.AMBIENT).
			sized(0.6F, 0.6F).
			build(new ResourceLocation(BetterEnd.MOD_ID, "silk_moth").toString()));

	
	@SubscribeEvent
	public static void registerGlobalEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(ModEntityTypes.END_FISH.get(), EndFishEntity.registerAttributes().build());
		event.put(ModEntityTypes.DRAGONFLY.get(), DragonflyEntity.registerAttributes().build());
		event.put(ModEntityTypes.SHADOW_WALKER.get(), ShadowWalkerEntity.registerAttributes().build());
		event.put(ModEntityTypes.END_SLIME.get(), EndSlimeEntity.registerAttributes().build());
		event.put(ModEntityTypes.CUBOZOA.get(), CubozoaEntity.registerAttributes().build());
		event.put(ModEntityTypes.SILK_MOTH.get(), SilkMothEntity.registerAttributes().build());
	}
	
	public static void registerEntitySpawns()
	{
		SpawnPlacements.register(ModEntityTypes.END_FISH.get(), 
				SpawnPlacements.Type.IN_WATER, 
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndFishEntity::canSpawn);
		
		SpawnPlacements.register(ModEntityTypes.DRAGONFLY.get(), 
				SpawnPlacements.Type.NO_RESTRICTIONS, 
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DragonflyEntity::canSpawn);
		
		SpawnPlacements.register(ModEntityTypes.SHADOW_WALKER.get(), 
				SpawnPlacements.Type.ON_GROUND, 
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ShadowWalkerEntity::canSpawn);
		
		SpawnPlacements.register(ModEntityTypes.END_SLIME.get(), 
				SpawnPlacements.Type.ON_GROUND, 
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndSlimeEntity::canSpawn);
		
		SpawnPlacements.register(ModEntityTypes.CUBOZOA.get(), 
				SpawnPlacements.Type.IN_WATER, 
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CubozoaEntity::canSpawn);
		
		SpawnPlacements.register(ModEntityTypes.SILK_MOTH.get(), 
				SpawnPlacements.Type.NO_RESTRICTIONS, 
				Heightmap.Types.MOTION_BLOCKING, SilkMothEntity::canSpawn);
	}
}

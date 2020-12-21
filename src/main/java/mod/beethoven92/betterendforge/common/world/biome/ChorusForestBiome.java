package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;

public class ChorusForestBiome extends BetterEndBiome
{

	public ChorusForestBiome() 
	{
		super(new BiomeTemplate("chorus_forest").
				setFogColor(87, 26, 87).
				setFogDensity(1.5F).
				setGrassColor(122, 45, 122).
				setFoliageColor(122, 45, 122).
				setWaterColor(73, 30, 73).
				setWaterFogColor(73, 30, 73).
				setSurface(ModBlocks.CHORUS_NYLIUM.get()).
				setParticles(ParticleTypes.PORTAL, 0.01F).
				setAmbientSound(ModSoundEvents.AMBIENT_CHORUS_FOREST.get()).
				setMusic(ModSoundEvents.MUSIC_CHORUS_FOREST.get()).
				addFeature(Decoration.UNDERGROUND_ORES, ModConfiguredFeatures.VIOLECITE_LAYER).
				addFeature(Decoration.LAKES, ModConfiguredFeatures.END_LAKE_RARE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.PYTHADENDRON).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.PYTHADENDRON_BUSH).
				addFeature(Decoration.VEGETAL_DECORATION, Features.CHORUS_PLANT).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHORUS_GRASS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.PURPLE_POLYPORE_DENSE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.TAIL_MOSS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.TAIL_MOSS_WOOD).
				//addFeature(EndFeatures.CHARNIA_PURPLE).
				addStructure(StructureFeatures.END_CITY). // END CITY
				//addMobSpawn(EndEntities.END_SLIME, 5, 1, 2).
				setMobSpawn(EntityClassification.MONSTER, EntityType.ENDERMAN, 50, 1, 4));
	}

}

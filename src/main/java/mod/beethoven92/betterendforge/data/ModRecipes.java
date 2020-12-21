package mod.beethoven92.betterendforge.data;

import java.util.function.Consumer;

import mod.beethoven92.betterendforge.common.block.material.StoneMaterial;
import mod.beethoven92.betterendforge.common.block.material.WoodenMaterial;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public class ModRecipes extends RecipeProvider
{
	public ModRecipes(DataGenerator generatorIn) 
	{
		super(generatorIn);
	}
	
	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) 
	{
		// BLOCKS
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.ENDER_BLOCK.get()).key('#', Items.ENDER_PEARL).patternLine("##").patternLine("##").addCriterion("has_ender_pearl", hasItem(Items.ENDER_PEARL)).build(consumer);
	    ShapelessRecipeBuilder.shapelessRecipe(Items.ENDER_PEARL, 4).addIngredient(ModBlocks.ENDER_BLOCK.get()).addCriterion("has_ender_block", hasItem(ModBlocks.ENDER_BLOCK.get())).build(consumer, "ender_pearl_from_ender_block");
	   
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.AURORA_CRYSTAL.get()).key('#', ModItems.CRYSTAL_SHARDS.get()).patternLine("##").patternLine("##").addCriterion("has_crystal_shard", hasItem(ModItems.CRYSTAL_SHARDS.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.END_LOTUS.log.get()).key('#', ModBlocks.END_LOTUS_STEM.get()).patternLine("##").patternLine("##").addCriterion("has_end_lotus_stem", hasItem(ModBlocks.END_LOTUS_STEM.get())).build(consumer);

	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.END_STONE_SMELTER.get()).key('#', Blocks.END_STONE_BRICKS).key('V', Items.BUCKET).patternLine("###").patternLine("V V").patternLine("###").addCriterion("has_end_stone_bricks", hasItem(Blocks.END_STONE_BRICKS)).build(consumer);
	    
	    // DYES
		ShapelessRecipeBuilder.shapelessRecipe(Items.BLUE_DYE).addIngredient(ModBlocks.BLUE_VINE_SEED.get()).setGroup("blue_dye").addCriterion("has_blue_vine_seed", hasItem(ModBlocks.BLUE_VINE_SEED.get())).build(consumer, "blue_dye_from_blue_vine_seed");
		ShapelessRecipeBuilder.shapelessRecipe(Items.CYAN_DYE).addIngredient(ModBlocks.CREEPING_MOSS.get()).setGroup("cyan_dye").addCriterion("has_creeping_moss", hasItem(ModBlocks.CREEPING_MOSS.get())).build(consumer, "cyan_dye_from_creeping_moss");
		ShapelessRecipeBuilder.shapelessRecipe(Items.CYAN_DYE).addIngredient(ModBlocks.CYAN_MOSS.get()).setGroup("cyan_dye").addCriterion("has_cyan_moss", hasItem(ModBlocks.CYAN_MOSS.get())).build(consumer, "cyan_dye_from_cyan_moss");
		ShapelessRecipeBuilder.shapelessRecipe(Items.YELLOW_DYE).addIngredient(ModBlocks.UMBRELLA_MOSS.get()).setGroup("yellow_dye").addCriterion("has_umbrella_moss", hasItem(ModBlocks.UMBRELLA_MOSS.get())).build(consumer, "yellow_dye_from_umbrella_moss");
		ShapelessRecipeBuilder.shapelessRecipe(Items.YELLOW_DYE).addIngredient(ModBlocks.UMBRELLA_MOSS_TALL.get()).setGroup("yellow_dye").addCriterion("has_umbrella_moss_tall", hasItem(ModBlocks.UMBRELLA_MOSS_TALL.get())).build(consumer, "yellow_dye_from_umbrella_moss_tall");
		ShapelessRecipeBuilder.shapelessRecipe(Items.BLACK_DYE).addIngredient(ModBlocks.SHADOW_PLANT.get()).setGroup("black_dye").addCriterion("has_shadow_plant", hasItem(ModBlocks.SHADOW_PLANT.get())).build(consumer, "black_dye_from_shadow_plant");
		ShapelessRecipeBuilder.shapelessRecipe(Items.PURPLE_DYE).addIngredient(ModBlocks.PURPLE_POLYPORE.get()).setGroup("purple_dye").addCriterion("has_purple_polypore", hasItem(ModBlocks.PURPLE_POLYPORE.get())).build(consumer, "purple_dye_from_purple_polypore");
		ShapelessRecipeBuilder.shapelessRecipe(Items.GRAY_DYE).addIngredient(ModBlocks.TAIL_MOSS.get()).setGroup("gray_dye").addCriterion("has_tail_moss", hasItem(ModBlocks.TAIL_MOSS.get())).build(consumer, "gray_dye_from_tail_moss");
		
		// MISC
		ShapedRecipeBuilder.shapedRecipe(Items.PAPER, 3).key('#', ModItems.END_LILY_LEAF_DRIED.get()).patternLine("###").addCriterion("has_end_lily_leaf_dried", hasItem(ModItems.END_LILY_LEAF_DRIED.get())).build(consumer, "paper_from_end_lily_leaf_dried");
		
		// PEDESTALS
		registerPedestal(ModBlocks.QUARTZ_PEDESTAL.get(), Blocks.QUARTZ_SLAB, Blocks.QUARTZ_PILLAR, consumer, "quartz");
		registerPedestal(ModBlocks.PURPUR_PEDESTAL.get(), Blocks.PURPUR_SLAB, Blocks.PURPUR_PILLAR, consumer, "purpur");
		
		ShapedRecipeBuilder.shapedRecipe(ModBlocks.INFUSION_PEDESTAL.get()).key('O', Items.ENDER_PEARL).key('Y', Items.ENDER_EYE).key('#', Blocks.OBSIDIAN).patternLine(" Y ").patternLine("O#O").patternLine(" # ").addCriterion("has_ender_pearl", hasItem(Items.ENDER_PEARL)).addCriterion("has_ender_eye", hasItem(Items.ENDER_EYE)).addCriterion("has_obsidian", hasItem(Blocks.OBSIDIAN)).build(consumer);
		
		// FURNACE
		CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ModItems.END_FISH_RAW.get()), ModItems.END_FISH_COOKED.get(), 0.35F, 200).addCriterion("has_end_fish_raw", hasItem(ModItems.END_FISH_RAW.get())).build(consumer);
		CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ModItems.END_LILY_LEAF.get()), ModItems.END_LILY_LEAF_DRIED.get(), 0.35F, 200).addCriterion("has_end_lily_leaf", hasItem(ModItems.END_LILY_LEAF.get())).build(consumer);
		CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ModBlocks.ENDSTONE_DUST.get()), Blocks.GLASS.asItem(), 0.35F, 200).addCriterion("has_end_stone_dust", hasItem(ModBlocks.ENDSTONE_DUST.get())).build(consumer, "glass_from_end_stone_dust");
		CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ModItems.SHADOW_BERRY_RAW.get()), ModItems.SHADOW_BERRY_COOKED.get(), 0.35F, 200).addCriterion("has_shadow_berry_raw", hasItem(ModItems.SHADOW_BERRY_RAW.get())).build(consumer);
		
		// ARMORS AND TOOLS
		String material = "terminite";    
	    
		makeMaterialAndBlockRecipes(ModBlocks.TERMINITE_BLOCK.get(), ModItems.TERMINITE_INGOT.get(), consumer, material);
	    
	    makeHelmetRecipe(ModItems.TERMINITE_HELMET.get(), ModItems.TERMINITE_INGOT.get(), consumer, material);
		makeChestplateRecipe(ModItems.TERMINITE_CHESTPLATE.get(), ModItems.TERMINITE_INGOT.get(), consumer, material);
		makeLeggingsRecipe(ModItems.TERMINITE_LEGGINGS.get(), ModItems.TERMINITE_INGOT.get(), consumer, material);
		makeBootsRecipe(ModItems.TERMINITE_BOOTS.get(), ModItems.TERMINITE_INGOT.get(), consumer, material);
		
		makeSwordRecipe(ModItems.TERMINITE_SWORD.get(), ModItems.TERMINITE_INGOT.get(), consumer, material);
		makeAxeRecipe(ModItems.TERMINITE_AXE.get(), ModItems.TERMINITE_INGOT.get(), consumer, material);
		makePickaxeRecipe(ModItems.TERMINITE_PICKAXE.get(), ModItems.TERMINITE_INGOT.get(), consumer, material);
		makeShovelRecipe(ModItems.TERMINITE_SHOVEL.get(), ModItems.TERMINITE_INGOT.get(), consumer, material);
		makeHoeRecipe(ModItems.TERMINITE_HOE.get(), ModItems.TERMINITE_INGOT.get(), consumer, material);
		makeHammerRecipe(ModItems.TERMINITE_HAMMER.get(), ModItems.TERMINITE_INGOT.get(), consumer, material);
		
		material = "aeternium";
		
		makeMaterialAndBlockRecipes(ModBlocks.AETERNIUM_BLOCK.get(), ModItems.AETERNIUM_INGOT.get(), consumer, material);
		
		makeHelmetRecipe(ModItems.AETERNIUM_HELMET.get(), ModItems.AETERNIUM_INGOT.get(), consumer, material);
		makeChestplateRecipe(ModItems.AETERNIUM_CHESTPLATE.get(), ModItems.AETERNIUM_INGOT.get(), consumer, material);
		makeLeggingsRecipe(ModItems.AETERNIUM_LEGGINGS.get(), ModItems.AETERNIUM_INGOT.get(), consumer, material);
		makeBootsRecipe(ModItems.AETERNIUM_BOOTS.get(), ModItems.AETERNIUM_INGOT.get(), consumer, material);
		
		makeSwordRecipe(ModItems.AETERNIUM_SWORD.get(), ModItems.AETERNIUM_INGOT.get(), consumer, material);
		makeAxeRecipe(ModItems.AETERNIUM_AXE.get(), ModItems.AETERNIUM_INGOT.get(), consumer, material);
		makePickaxeRecipe(ModItems.AETERNIUM_PICKAXE.get(), ModItems.AETERNIUM_INGOT.get(), consumer, material);
		makeShovelRecipe(ModItems.AETERNIUM_SHOVEL.get(), ModItems.AETERNIUM_INGOT.get(), consumer, material);
		makeHoeRecipe(ModItems.AETERNIUM_HOE.get(), ModItems.AETERNIUM_INGOT.get(), consumer, material);
		makeHammerRecipe(ModItems.AETERNIUM_HAMMER.get(), ModItems.AETERNIUM_INGOT.get(), consumer, material);
		
		makeHammerRecipe(ModItems.GOLDEN_HAMMER.get(), Items.GOLD_INGOT, consumer, "gold");
		makeHammerRecipe(ModItems.IRON_HAMMER.get(), Items.IRON_INGOT, consumer, "iron");
		makeHammerRecipe(ModItems.DIAMOND_HAMMER.get(), Items.DIAMOND, consumer, "diamond");
		makeHammerRecipe(ModItems.NETHERITE_HAMMER.get(), Items.NETHERITE_INGOT, consumer, "netherite");
		
		// WOODEN MATERIALS
		makeWoodenMaterialRecipes(ModBlocks.MOSSY_GLOWSHROOM, consumer);
		makeWoodenMaterialRecipes(ModBlocks.LACUGROVE, consumer);
		makeWoodenMaterialRecipes(ModBlocks.END_LOTUS, consumer);
		makeWoodenMaterialRecipes(ModBlocks.PYTHADENDRON, consumer);
		makeWoodenMaterialRecipes(ModBlocks.DRAGON_TREE, consumer);
		
		// STONE MATERIALS
		makeStoneMaterialRecipes(ModBlocks.FLAVOLITE, consumer);
		makeStoneMaterialRecipes(ModBlocks.VIOLECITE, consumer);
	}
	
	private void makeWoodenMaterialRecipes(WoodenMaterial material, Consumer<IFinishedRecipe> consumer)
	{
		ShapelessRecipeBuilder.shapelessRecipe(material.planks.get(), 4).addIngredient(material.logItemTag).setGroup("end_planks").addCriterion("has_logs", hasItem(material.logItemTag)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(material.stairs.get(), 4).key('#', material.planks.get()).patternLine("#  ").patternLine("## ").patternLine("###").setGroup("end_planks__stairs").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(material.slab.get(), 6).key('#', material.planks.get()).patternLine("###").setGroup("end_planks_slabs").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.fence.get(), 3).key('#', Items.STICK).key('W', material.planks.get()).patternLine("W#W").patternLine("W#W").setGroup("end_planks_fences").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.gate.get()).key('#', Items.STICK).key('W', material.planks.get()).patternLine("#W#").patternLine("#W#").setGroup("end_planks_gates").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapelessRecipeBuilder.shapelessRecipe(material.button.get()).addIngredient(material.planks.get()).setGroup("end_planks_buttons").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.pressurePlate.get()).key('#', material.planks.get()).patternLine("##").setGroup("end_planks_plates").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.trapdoor.get(), 2).key('#', material.planks.get()).patternLine("###").patternLine("###").setGroup("end_trapdoors").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.door.get(), 3).key('#', material.planks.get()).patternLine("##").patternLine("##").patternLine("##").setGroup("end_doors").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	}
	
	private void makeStoneMaterialRecipes(StoneMaterial material, Consumer<IFinishedRecipe> consumer)
	{
		ShapedRecipeBuilder.shapedRecipe(material.bricks.get(), 4).key('#', material.stone.get()).patternLine("##").patternLine("##").setGroup("end_bricks").addCriterion("has_stone", hasItem(material.stone.get())).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(material.polished.get(), 4).key('#', material.bricks.get()).patternLine("##").patternLine("##").setGroup("end_tile").addCriterion("has_bricks", hasItem(material.bricks.get())).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(material.tiles.get(), 4).key('#', material.polished.get()).patternLine("##").patternLine("##").setGroup("end_small_tile").addCriterion("has_polished_stone", hasItem(material.polished.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.pillar.get()).key('#', material.slab.get()).patternLine("#").patternLine("#").setGroup("end_pillar").addCriterion("has_stone_slabs", hasItem(material.slab.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.stairs.get(), 4).key('#', material.stone.get()).patternLine("#  ").patternLine("## ").patternLine("###").setGroup("end_stone_stairs").addCriterion("has_stone", hasItem(material.stone.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.slab.get(), 6).key('#', material.stone.get()).patternLine("###").setGroup("end_stone_slabs").addCriterion("has_stone", hasItem(material.stone.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.brick_stairs.get(), 4).key('#', material.bricks.get()).patternLine("#  ").patternLine("## ").patternLine("###").setGroup("end_stone_stairs").addCriterion("has_bricks", hasItem(material.bricks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.brick_slab.get(), 6).key('#', material.bricks.get()).patternLine("###").setGroup("end_stone_slabs").addCriterion("has_bricks", hasItem(material.bricks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.wall.get(), 6).key('#', material.stone.get()).patternLine("###").patternLine("###").setGroup("end_wall").addCriterion("has_stone", hasItem(material.stone.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.brick_wall.get(), 6).key('#', material.bricks.get()).patternLine("###").patternLine("###").setGroup("end_wall").addCriterion("has_bricks", hasItem(material.bricks.get())).build(consumer);
	    ShapelessRecipeBuilder.shapelessRecipe(material.button.get()).addIngredient(material.stone.get()).setGroup("end_stone_buttons").addCriterion("has_stone", hasItem(material.stone.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.pressure_plate.get()).key('#', material.stone.get()).patternLine("##").setGroup("end_stone_plates").addCriterion("has_stone", hasItem(material.stone.get())).build(consumer);
	}
	
	private void makeMaterialAndBlockRecipes(Block block, Item ingot, Consumer<IFinishedRecipe> consumer, String material)
	{
	    ShapedRecipeBuilder.shapedRecipe(block).key('#', ingot).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_" + material + "_ingot", hasItem(ingot)).build(consumer);
	    ShapelessRecipeBuilder.shapelessRecipe(ingot, 9).addIngredient(block).setGroup(material + "_ingot").addCriterion("has_" + material + "_block", hasItem(block)).build(consumer, material + "_ingot_from_" + material + "_block");
	}
	
	private void makeHelmetRecipe(Item result, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(result).key('X', ingredient).patternLine("XXX").patternLine("X X").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeChestplateRecipe(Item result, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(result).key('X', ingredient).patternLine("X X").patternLine("XXX").patternLine("XXX").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeLeggingsRecipe(Item result, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(result).key('X', ingredient).patternLine("XXX").patternLine("X X").patternLine("X X").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeBootsRecipe(Item result, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(result).key('X', ingredient).patternLine("X X").patternLine("X X").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeSwordRecipe(Item sword, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(sword).key('#', Items.STICK).key('X', ingredient).patternLine("X").patternLine("X").patternLine("#").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makePickaxeRecipe(Item pickaxe, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(pickaxe).key('#', Items.STICK).key('X', ingredient).patternLine("XXX").patternLine(" # ").patternLine(" # ").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeAxeRecipe(Item axe, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(axe).key('#', Items.STICK).key('X', ingredient).patternLine("XX").patternLine("X#").patternLine(" #").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeShovelRecipe(Item shovel, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(shovel).key('#', Items.STICK).key('X', ingredient).patternLine("X").patternLine("#").patternLine("#").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeHoeRecipe(Item hoe, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(hoe).key('#', Items.STICK).key('X', ingredient).patternLine("XX").patternLine(" #").patternLine(" #").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeHammerRecipe(Item hammer, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(hammer).key('#', Items.STICK).key('X', ingredient).patternLine("X X").patternLine("X#X").patternLine(" # ").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void registerPedestal(Block pedestal, Block slab, Block pillar, Consumer<IFinishedRecipe> consumer, String material) 
	{
		ShapedRecipeBuilder.shapedRecipe(pedestal, 2).key('S', slab).key('#', pillar).patternLine("S").patternLine("#").patternLine("S").addCriterion("has_" + material + "_slab", hasItem(slab)).addCriterion("has_" + material + "_pillar", hasItem(pillar)).build(consumer);
	}
}
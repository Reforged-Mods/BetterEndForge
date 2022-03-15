package mod.beethoven92.betterendforge.common.tileentity;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import mod.beethoven92.betterendforge.common.block.EndStoneSmelter;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import mod.beethoven92.betterendforge.common.inventory.EndStoneSmelterContainer;
import mod.beethoven92.betterendforge.common.recipes.AlloyingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.ItemLike;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

public class EndStoneSmelterTileEntity extends BaseContainerBlockEntity implements RecipeHolder, StackedContentsCompatible, WorldlyContainer
{
	private static final int[] TOP_SLOTS = new int[] { 0, 1 };
	private static final int[] BOTTOM_SLOTS = new int[] { 2, 3 };
	private static final int[] SIDE_SLOTS = new int[] { 2 };
	private static final Map<Item, Integer> AVAILABLE_FUELS = Maps.newHashMap();
	
	private final Object2IntOpenHashMap<ResourceLocation> recipesUsed;
	protected NonNullList<ItemStack> items;
	protected final ContainerData data;
	private Recipe<?> lastRecipe;
	private int smeltTimeTotal;
	private int smeltTime;
	private int burnTime;
	private int fuelTime;

	public EndStoneSmelterTileEntity(BlockPos pos, BlockState state)
	{
		super(ModTileEntityTypes.END_STONE_SMELTER.get(), pos, state);
		
		this.recipesUsed = new Object2IntOpenHashMap<ResourceLocation>();
		this.items= NonNullList.withSize(4, ItemStack.EMPTY);
		this.data = new ContainerData() 
		{
		 	public int get(int index) 
		 	{
		 		switch(index) 
		 		{
			 		case 0:
			 			return EndStoneSmelterTileEntity.this.burnTime;
			 		case 1:
			 			return EndStoneSmelterTileEntity.this.fuelTime;
			 		case 2:
			 			return EndStoneSmelterTileEntity.this.smeltTime;
			 		case 3:
			 			return EndStoneSmelterTileEntity.this.smeltTimeTotal;
			 		default:
			 			return 0;
		 		}
		 	}

		 	public void set(int index, int value) 
		 	{
		 		switch(index) 
		 		{
			 		case 0:
			 			EndStoneSmelterTileEntity.this.burnTime = value;
			 			break;
			 		case 1:
			 			EndStoneSmelterTileEntity.this.fuelTime = value;
			 			break;
			 		case 2:
			 			EndStoneSmelterTileEntity.this.smeltTime = value;
			 			break;
			 		case 3:
			 			EndStoneSmelterTileEntity.this.smeltTimeTotal = value;
		 		}
		 	}

		 	public int getCount() {
		 		return 4;
		 	}
		 };
		 
		 this.registerFuels();
	}
	
	@Override
	public void saveAdditional(CompoundTag compound)
	{
		super.saveAdditional(compound);
		compound.putShort("BurnTime", (short) burnTime);
		compound.putShort("FuelTime", (short) fuelTime);
		compound.putShort("SmeltTime", (short) smeltTime);
		compound.putShort("SmeltTimeTotal", (short) smeltTimeTotal);
		ContainerHelper.saveAllItems(compound, this.items);
		CompoundTag usedRecipes = new CompoundTag();
		this.recipesUsed.forEach((identifier, integer) -> {
			usedRecipes.putInt(identifier.toString(), integer);
		});
		compound.put("RecipesUsed", usedRecipes);
	}
	
	@Override
	public void load(CompoundTag nbt)
	{	
		super.load(nbt);
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(nbt, items);
		this.burnTime = nbt.getShort("BurnTime");
		this.fuelTime = nbt.getShort("FuelTime");
		this.smeltTime = nbt.getShort("SmeltTime");
		this.smeltTimeTotal = nbt.getShort("SmeltTimeTotal");
		CompoundTag compound = nbt.getCompound("RecipesUsed");
		Iterator<String> recipes = compound.getAllKeys().iterator();
		while(recipes.hasNext()) 
		{
			String id = recipes.next();
			this.recipesUsed.put(new ResourceLocation(id), compound.getInt(id));
		}
	}
	
	private void registerFuels() 
	{
		registerFuel(Items.LAVA_BUCKET, 16000);
		registerFuel(Blocks.COAL_BLOCK, 12000);
		registerFuel(Items.BLAZE_ROD, 2000);
	}
	
	private boolean isBurning() 
	{
		return this.burnTime > 0;
	}
	
	public void dropExperience(Player player) 
	{
		List<Recipe<?>> list = Lists.newArrayList();
		ObjectIterator<Entry<ResourceLocation>> usedRecipes = this.recipesUsed.object2IntEntrySet().iterator();
		while(usedRecipes.hasNext()) 
		{
			Entry<ResourceLocation> entry = usedRecipes.next();
			level.getRecipeManager().byKey(entry.getKey()).ifPresent((recipe) -> {
				list.add(recipe);
				if (recipe instanceof AlloyingRecipe) 
				{
					AlloyingRecipe alloying = (AlloyingRecipe) recipe;
					this.dropExperience(player.level, player.position(), entry.getIntValue(), alloying.getExperience());
				} else 
				{
					BlastingRecipe blasting = (BlastingRecipe) recipe;
					this.dropExperience(player.level, player.position(), entry.getIntValue(), blasting.getExperience());
				}
			});
		}
		player.awardRecipes(list);
		this.recipesUsed.clear();
	}
	
	private void dropExperience(Level world, Vec3 vec3d, int i, float f) 
	{
		int j = Mth.floor(i * f);
		float g = Mth.frac(i * f);
		if (g != 0.0F && Math.random() < g) {
			j++;
		}

		while(j > 0) 
		{
			int k = ExperienceOrb.getExperienceValue(j);
			j -= k;
			world.addFreshEntity(new ExperienceOrb(world, vec3d.x, vec3d.y, vec3d.z, k));
		}
	}

	public static <T extends BlockEntity> void commonTick(Level level, BlockPos pos, BlockState state, T tile) {
		if (tile instanceof EndStoneSmelterTileEntity tick) {
			tick.tick(level, pos, state);
		}
	}

	public void tick(Level level, BlockPos pos, BlockState state)
	{
		boolean initialBurning = this.isBurning();
		if (initialBurning) 
		{
			this.burnTime--;
		}
		
		boolean burning = this.isBurning();
		if (!level.isClientSide)
		{
			ItemStack fuel = this.items.get(2);
			if (!burning && (fuel.isEmpty() || items.get(0).isEmpty() && items.get(1).isEmpty())) 
			{
				if (smeltTime > 0)
				{
					this.smeltTime = Mth.clamp(smeltTime - 2, 0, smeltTimeTotal);
				}
			} 
			else 
			{
				Recipe<?> recipe = level.getRecipeManager().getRecipeFor(AlloyingRecipe.TYPE, this, level).orElse(null);
				if (recipe == null) 
				{
					recipe = level.getRecipeManager().getRecipeFor(RecipeType.BLASTING, this, level).orElse(null);
				}
				boolean accepted = this.canAcceptRecipeOutput(recipe);
				if (!burning && accepted) 
				{
					this.burnTime = this.getBurnTime(fuel);
					this.fuelTime = this.burnTime;
					burning = this.isBurning();
					if (burning)
					{
						if (!fuel.isEmpty()) 
						{
							Item item = fuel.getItem();
							fuel.shrink(1);
							if (fuel.isEmpty()) 
							{
								Item remainFuel = item.getCraftingRemainingItem();
								this.items.set(2, remainFuel == null ? ItemStack.EMPTY : new ItemStack(remainFuel));
							}
						}
						this.setChanged();
					}
				}

				if (burning && accepted) 
				{
					this.smeltTime++;
					if (smeltTime == smeltTimeTotal) 
					{
						this.smeltTime = 0;
						this.smeltTimeTotal = this.getSmeltTime();
						this.craftRecipe(recipe);
						this.setChanged();
					}
				} 
				else 
				{
					this.smeltTime = 0;
				}
			}

			if (initialBurning != burning) 
			{
				level.setBlock(worldPosition, state.setValue(EndStoneSmelter.LIT, burning), 3);
				this.setChanged();
			}
		}
	}

	protected boolean canAcceptRecipeOutput(Recipe<?> recipe) 
	{
		if (recipe == null) return false;
		boolean validInput = false;
		if (recipe instanceof AlloyingRecipe) 
		{
			validInput = !items.get(0).isEmpty() &&
					!items.get(1).isEmpty();
		} 
		else 
		{
			validInput = !items.get(0).isEmpty() ||
					!items.get(1).isEmpty();
		}
		if (validInput) 
		{
			ItemStack result = recipe.getResultItem();
			if (result.isEmpty()) 
			{
				return false;
			} 
			else 
			{
				ItemStack output = this.items.get(3);
				int outCount = output.getCount();
				int total = outCount + result.getCount();
				if (output.isEmpty()) 
				{
					return true;
				} 
				else if (!output.sameItemStackIgnoreDurability(result)) 
				{
					return false;
				} 
				else if (outCount < this.getMaxStackSize() && outCount < output.getMaxStackSize()) 
				{
					return this.getMaxStackSize() >= total;
				} 
				else 
				{
					return output.getCount() < result.getMaxStackSize();
				}
			}
		}
		
		return false;
	}

	private void craftRecipe(Recipe<?> recipe) 
	{
		if (recipe == null || !canAcceptRecipeOutput(recipe)) return;
		
		ItemStack result = recipe.getResultItem();
		ItemStack output = this.items.get(3);
		if (output.isEmpty())
		{
			this.items.set(3, result.copy());
		} 
		else if (output.getItem() == result.getItem()) 
		{
			output.grow(result.getCount());
		}

		if (!this.level.isClientSide)
		{
			this.setRecipeUsed(recipe);
		}
		
		if (recipe instanceof AlloyingRecipe) 
		{
			this.items.get(0).shrink(1);
			this.items.get(1).shrink(1);
		} 
		else
		{
			if (!this.items.get(0).isEmpty()) 
			{
				this.items.get(0).shrink(1);
			} 
			else 
			{
				this.items.get(1).shrink(1);
			}
		}
	}
	
	@Override
	public int getContainerSize()
	{
		return this.items.size();
	}

	@Override
	public boolean isEmpty() 
	{
		for(ItemStack itemstack : this.items) 
		{
			if (!itemstack.isEmpty())
			{
				return false;
	        }
	    }		
		return true;
	}

	@Override
	public ItemStack getItem(int index) 
	{
		return this.items.get(index);
	}

	@Override
	public ItemStack removeItem(int index, int count) 
	{
		return ContainerHelper.removeItem(this.items, index, count);
	}

	@Override
	public ItemStack removeItemNoUpdate(int index)
	{
		return ContainerHelper.takeItem(this.items, index);
	}

	@Override
	public void setItem(int index, ItemStack stack) 
	{	
		ItemStack itemStack = this.items.get(index);
		boolean stackValid = !stack.isEmpty() && stack.sameItemStackIgnoreDurability(itemStack) && ItemStack.tagMatches(stack, itemStack);
		this.items.set(index, stack);
		if (stack.getCount() > this.getMaxStackSize()) 
		{
			stack.setCount(this.getMaxStackSize());
		}
		if ((index == 0 || index == 1) && !stackValid) 
		{
			this.smeltTimeTotal = this.getSmeltTime();
			this.smeltTime = 0;
			this.setChanged();
		}
	}
	
	protected int getSmeltTime()
	{
		int smeltTime = this.level.getRecipeManager().getRecipeFor(AlloyingRecipe.TYPE, this, level)
				.map(AlloyingRecipe::getSmeltTime).orElse(0);
		if (smeltTime == 0) 
		{
			smeltTime = this.level.getRecipeManager().getRecipeFor(RecipeType.BLASTING, this, level)
				.map(BlastingRecipe::getCookingTime).orElse(200);
			smeltTime /= 1.5;
		}
		return smeltTime;
	}
	
	@Override
	public int[] getSlotsForFace(Direction side) 
	{
		if (side == Direction.DOWN) 
		{
			return BOTTOM_SLOTS;
		} 
		else 
		{
			return side == Direction.UP ? TOP_SLOTS : SIDE_SLOTS;
		}
	}

	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, Direction direction) 
	{
		return this.canPlaceItem(index, itemStackIn);
	}

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) 
	{
		if (direction == Direction.DOWN && index == 2) 
		{
			if (stack.getItem() != Items.BUCKET && stack.getItem() != Items.WATER_BUCKET) 
			{
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean stillValid(Player player) 
	{
		if (this.level.getBlockEntity(this.worldPosition) != this) 
		{
			return false;
		} 
		else 
		{
			return player.distanceToSqr(this.worldPosition.getX() + 0.5D, this.worldPosition.getY() + 0.5D, this.worldPosition.getZ() + 0.5D) <= 64.0D;
		}
	}

	@Override
	public void clearContent()
	{
		this.items.clear();
	}

	@Override
	protected Component getDefaultName() 
	{
		return new TranslatableComponent("container.betterendforge.end_stone_smelter");
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory player) 
	{
		return new EndStoneSmelterContainer(id, player, this, this.data);
	}

	@Override
	public void fillStackedContents(StackedContents helper) 
	{
		for(ItemStack itemstack : this.items) 
		{
			helper.accountStack(itemstack);
		}
	}

	@Override
	public void setRecipeUsed(Recipe<?> recipe) 
	{
		if (recipe != null) 
		{
			ResourceLocation recipeId = recipe.getId();
			this.recipesUsed.addTo(recipeId, 1);
			this.lastRecipe = recipe;
		}
	}
	   
	@Override
	public Recipe<?> getRecipeUsed() 
	{
		return this.lastRecipe;
	}
	
	@Override
	public boolean canPlaceItem(int index, ItemStack stack) 
	{
		if (index == 3) 
		{
			return false;
		}
		else if (index != 2) 
		{
			return true;
		} 
		else 
		{
			ItemStack itemStack = this.items.get(2);
			return isFuel(stack) || stack.getItem() == Items.BUCKET && itemStack.getItem() != Items.BUCKET;
		}
	}
	
	public static boolean isFuel(ItemStack stack) 
	{
		return AVAILABLE_FUELS.containsKey(stack.getItem()) && net.minecraftforge.common.ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0;
	}
	
	protected int getBurnTime(ItemStack fuel) 
	{
		if (fuel.isEmpty()) 
		{
			return 0;
		} 
		else 
		{
			Item item = fuel.getItem();
			return AVAILABLE_FUELS.getOrDefault(item, 0);
		}
	}
	
	public static void registerFuel(ItemLike fuel, int time) 
	{
		if (AVAILABLE_FUELS.containsKey(fuel))
		{
			AVAILABLE_FUELS.replace(fuel.asItem(), time);
		} 
		else 
		{
			AVAILABLE_FUELS.put(fuel.asItem(), time);
		}
	}
	
	public static void registerFuel(Tag<Item> tag, int time)
	{
		Iterator<Item> tagItems = tag.getValues().iterator();
		tagItems.forEachRemaining(item -> registerFuel(item, time));
	}
	
	public static Map<Item, Integer> getAvailableFuels() 
	{
		return AVAILABLE_FUELS;
	}
	
	net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
			net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

	@Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing)
	{
		if (!this.remove && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			if (facing == Direction.UP) return handlers[0].cast();
			else if (facing == Direction.DOWN) return handlers[1].cast();
			else return handlers[2].cast();
	    }
		return super.getCapability(capability, facing);
	}

	@Override
	public void invalidateCaps()
	{
		super.invalidateCaps();
	    for (int x = 0; x < handlers.length; x++) handlers[x].invalidate();
	}
}

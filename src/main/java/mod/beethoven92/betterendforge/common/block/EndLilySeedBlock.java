package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.UnderwaterPlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;

public class EndLilySeedBlock extends UnderwaterPlantBlockWithAge
{
	public EndLilySeedBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public void grow(ISeedReader world, Random random, BlockPos pos) 
	{	
		if (canGrow(world, pos)) 
		{
			BlockHelper.setWithoutUpdate(world, pos, ModBlocks.END_LILY.get().getDefaultState().with(EndLilyBlock.SHAPE, TripleShape.BOTTOM));
			BlockPos up = pos.up();
			while (world.getFluidState(up).isSource()) 
			{
				BlockHelper.setWithoutUpdate(world, up, ModBlocks.END_LILY.get().getDefaultState().with(EndLilyBlock.SHAPE, TripleShape.MIDDLE));
				up = up.up();
			}
			BlockHelper.setWithoutUpdate(world, up, ModBlocks.END_LILY.get().getDefaultState().with(EndLilyBlock.SHAPE, TripleShape.TOP));
		}
	}
	
	private boolean canGrow(ISeedReader world, BlockPos pos)
	{
		BlockPos up = pos.up();
		while (world.getBlockState(up).getFluidState().getFluid().equals(Fluids.WATER.getStillFluid())) 
		{
			up = up.up();
		}
		return world.isAirBlock(up);
	}
}

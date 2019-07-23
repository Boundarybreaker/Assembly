package teamreborn.assembly.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.block.MaterialColor;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;
import teamreborn.assembly.api.SapSource;
import teamreborn.assembly.util.block.AssemblyProperties;

import java.util.Random;

public class RubberLogBlock extends LogBlock implements SapSource {

	public RubberLogBlock(MaterialColor materialColor, Settings settings) {
		super(materialColor, settings);
		setDefaultState(this.getDefaultState()
			.with(AssemblyProperties.ALIVE, false)
			.with(AssemblyProperties.NORTH_LATEX, false)
			.with(AssemblyProperties.SOUTH_LATEX, false)
			.with(AssemblyProperties.WEST_LATEX, false)
			.with(AssemblyProperties.EAST_LATEX, false));
	}

	@Override
	protected void appendProperties(StateFactory.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(AssemblyProperties.ALIVE);
		builder.add(AssemblyProperties.NORTH_LATEX);
		builder.add(AssemblyProperties.SOUTH_LATEX);
		builder.add(AssemblyProperties.WEST_LATEX);
		builder.add(AssemblyProperties.EAST_LATEX);
	}

	@Override
	public boolean isSideSapSource(ViewableWorld world, BlockPos pos, BlockState state, Direction side) {
		switch (side) {
			case NORTH:
				return state.get(AssemblyProperties.NORTH_LATEX);
			case SOUTH:
				return state.get(AssemblyProperties.SOUTH_LATEX);
			case WEST:
				return state.get(AssemblyProperties.WEST_LATEX);
			case EAST:
				return state.get(AssemblyProperties.EAST_LATEX);
			default:
				return false;
		}
	}

	public static BooleanProperty getRandomLatexProperty(Random random) {
		int side = random.nextInt(4);
		if (side == 0) {
			return AssemblyProperties.NORTH_LATEX;
		} else if (side == 1) {
			return AssemblyProperties.EAST_LATEX;
		} else if (side == 2) {
			return AssemblyProperties.SOUTH_LATEX;
		} else {
			return AssemblyProperties.WEST_LATEX;
		}
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return state.get(AssemblyProperties.ALIVE) && !(state.get(AssemblyProperties.NORTH_LATEX) || state.get(AssemblyProperties.SOUTH_LATEX) || state.get(AssemblyProperties.EAST_LATEX) || state.get(AssemblyProperties.WEST_LATEX));
	}

	@Override
	public void onScheduledTick(BlockState state, World world, BlockPos pos, Random random) {
		if (random.nextInt(40) == 0) {
			if (random.nextInt(5) == 0) {
				world.setBlockState(pos, state.with(AssemblyProperties.ALIVE, false));
			} else {
				world.setBlockState(pos, state.with(getRandomLatexProperty(random), true));
			}
		}
	}

}

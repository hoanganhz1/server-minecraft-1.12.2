package xyz.pixelatedw.mineminenomi.blocks;

import com.mojang.serialization.MapCodec;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class DialBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
	// Fields
	protected static final VoxelShape SHAPE = Block.box(4D,0D,4D,12D,4D,12D);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public DialBlock() {
		super(BlockBehaviour.Properties.of().strength(0.2F));
		this.registerDefaultState(((BlockState)(((BlockState)(this.stateDefinition.any())).setValue((Property)(WATERLOGGED),(Comparable)(Boolean.valueOf(false))))));
	}
	public DialBlock(BlockBehaviour.Properties var1) {
		super(var1);
		this.registerDefaultState(((BlockState)(((BlockState)(this.stateDefinition.any())).setValue((Property)(WATERLOGGED),(Comparable)(Boolean.valueOf(false))))));
	}
	public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
		return SHAPE;
	}
	public boolean propagatesSkylightDown(BlockState var1, BlockGetter var2, BlockPos var3) {
		return true;
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean skipRendering(BlockState var1, BlockState var2, Direction var3) {
		return true;
	}
	public int getLightBlock(BlockState var1, BlockGetter var2, BlockPos var3) {
		return 0;
	}
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
		Property[] var2 = new Property[]{WATERLOGGED};
		var1.add(var2);
		Property[] var3 = new Property[]{FACING};
		var1.add(var3);
	}
	public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
		if (((Boolean)(var1.getValue((Property)(WATERLOGGED)))).booleanValue()){
			var4.scheduleTick(var5,(Fluid)(Fluids.WATER),Fluids.WATER.getTickDelay((LevelReader)(var4)));
		}
		return super.updateShape(var1,var2,var3,var4,var5,var6);
	}
	public FluidState getFluidState(BlockState var1) {
		return (((Boolean)(var1.getValue((Property)(WATERLOGGED)))).booleanValue()) ? (Fluids.WATER.getSource(false)) : (super.getFluidState(var1));
	}
	public BlockState getStateForPlacement(BlockPlaceContext var1) {
		FluidState var2 = var1.getLevel().getFluidState(var1.getClickedPos());
		BlockState var3 = ((BlockState)(this.defaultBlockState().setValue((Property)(FACING),(Comparable)(var1.getHorizontalDirection()))));
		return ((BlockState)(var3.setValue((Property)(WATERLOGGED),(Comparable)(Boolean.valueOf((var2.is(FluidTags.WATER)) && ((var2.getAmount()) == (8)))))));
	}
	protected MapCodec<DialBlock> codec() {
		return MapCodec.unit((Supplier)(((Supplier<DialBlock>)(DialBlock::new))));
	}
}
package xyz.pixelatedw.mineminenomi.blocks;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;

public class DenDenMushiBlock extends Block implements SimpleWaterloggedBlock {
	// Fields
	private static final VoxelShape SHAPE = Block.box(4D,0D,4D,12D,9D,12D);
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final IntegerProperty TYPE = IntegerProperty.create("den_type",0,2);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public DenDenMushiBlock() {
		super(BlockBehaviour.Properties.of().strength(0.4F).noCollission());
		this.registerDefaultState(((BlockState)(((BlockState)(((BlockState)(((BlockState)(this.stateDefinition.any())).setValue((Property)(FACING),(Comparable)(Direction.NORTH)))).setValue((Property)(TYPE),(Comparable)(Integer.valueOf(0))))).setValue((Property)(WATERLOGGED),(Comparable)(Boolean.valueOf(false))))));
	}
	@Nullable()
	public @Nullable() BlockState getStateForPlacement(BlockPlaceContext var1) {
		BlockState var2 = this.defaultBlockState();
		BlockPos var3 = var1.getClickedPos();
		Direction[] var4 = var1.getNearestLookingDirections();
		FluidState var5 = var1.getLevel().getFluidState(var1.getClickedPos());
		int var6 = ((Integer)(var1.getItemInHand().getOrDefault(ModDataComponents.TYPE,Integer.valueOf(0)))).intValue();
		var2 = ((BlockState)(var2.setValue((Property)(TYPE),(Comparable)(Integer.valueOf(var6)))));
		Direction[] var7 = var4;
		int var8 = var7.length;
		int var9 = 0;
		do{
			if ((var9) < (var8)){
				Direction var10 = var7[var9];
				if (var10.getAxis().isHorizontal()){
					Direction var11 = var10.getOpposite();
					BlockState var12 = ((BlockState)(var2.setValue((Property)(FACING),(Comparable)(var11))));
					var2 = ((BlockState)(var12.setValue((Property)(WATERLOGGED),(Comparable)(Boolean.valueOf((var5.is(FluidTags.WATER)) && ((var5.getAmount()) == (8)))))));
					if (var2.canSurvive((LevelReader)(var1.getLevel()),var3)){
						return var2;
					}
				}
				var9++;
				continue;
			}else{
				break;
			}
		} while (true);
		return null;
	}
	public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
		return SHAPE;
	}
	public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
		return SHAPE;
	}
	public FluidState getFluidState(BlockState var1) {
		return (((Boolean)(var1.getValue((Property)(WATERLOGGED)))).booleanValue()) ? (Fluids.WATER.getSource(false)) : (super.getFluidState(var1));
	}
	public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
		if (((Boolean)(var1.getValue((Property)(WATERLOGGED)))).booleanValue()){
			var4.scheduleTick(var5,(Fluid)(Fluids.WATER),Fluids.WATER.getTickDelay((LevelReader)(var4)));
		}
		return (((var2.getOpposite()) == (var1.getValue((Property)(FACING)))) && (!(var1.canSurvive((LevelReader)(var4),var5)))) ? (Blocks.AIR.defaultBlockState()) : (var1);
	}
	public BlockState rotate(BlockState var1, Rotation var2) {
		return ((BlockState)(var1.setValue((Property)(FACING),(Comparable)(var2.rotate(((Direction)(var1.getValue((Property)(FACING)))))))));
	}
	public BlockState mirror(BlockState var1, Mirror var2) {
		return var1.rotate(var2.getRotation(((Direction)(var1.getValue((Property)(FACING))))));
	}
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
		Property[] var2 = new Property[]{WATERLOGGED};
		var1.add(var2);
		Property[] var3 = new Property[]{FACING};
		var1.add(var3);
		Property[] var4 = new Property[]{TYPE};
		var1.add(var4);
	}
	public boolean propagatesSkylightDown(BlockState var1, BlockGetter var2, BlockPos var3) {
		return true;
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean skipRendering(BlockState var1, BlockState var2, Direction var3) {
		return true;
	}
}
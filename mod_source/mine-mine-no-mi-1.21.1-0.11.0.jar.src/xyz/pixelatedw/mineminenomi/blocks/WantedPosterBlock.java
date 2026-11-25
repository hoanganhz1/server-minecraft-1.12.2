package xyz.pixelatedw.mineminenomi.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.enums.CanvasSize;
import xyz.pixelatedw.mineminenomi.api.helpers.BlocksHelper;
import xyz.pixelatedw.mineminenomi.api.items.components.WantedPosterData;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.WantedPosterBlockEntity;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.items.WantedPosterItem;

public class WantedPosterBlock extends Block implements EntityBlock {
	// Fields
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	public static final EnumProperty<CanvasSize> SIZE = BlocksHelper.CANVAS_SIZE;
	private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap((Map)(ImmutableMap.of(Direction.NORTH,Block.box(0D,-2.5D,14D,16D,18.5D,16D),Direction.SOUTH,Block.box(0D,-2.5D,0D,16D,18.5D,2D),Direction.EAST,Block.box(0D,-2.5D,0D,2D,18.5D,16D),Direction.WEST,Block.box(14D,-2.5D,0D,16D,18.5D,16D))));

	public WantedPosterBlock() {
		super(BlockBehaviour.Properties.of().strength(0.2F).noCollission().isSuffocating(BlocksHelper::never));
		this.registerDefaultState(((BlockState)(((BlockState)(((BlockState)(this.stateDefinition.any())).setValue((Property)(FACING),(Comparable)(Direction.NORTH)))).setValue((Property)(SIZE),(Comparable)(CanvasSize.SMALL)))));
	}
	public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
		return ((VoxelShape)(SHAPES.get(var1.getValue((Property)(FACING)))));
	}
	public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
		Direction var4 = ((Direction)(var1.getValue((Property)(FACING))));
		return var2.getBlockState(var3.offset(var4.getOpposite().getNormal())).isFaceSturdy((BlockGetter)(var2),var3,var4);
	}
	@Nullable()
	public BlockState getStateForPlacement(BlockPlaceContext var1) {
		BlockState var2 = this.defaultBlockState();
		BlockPos var3 = var1.getClickedPos();
		Direction[] var4 = var1.getNearestLookingDirections();
		var2 = ((BlockState)(var2.setValue((Property)(SIZE),(Comparable)(((CanvasSize)(var1.getItemInHand().getOrDefault(ModDataComponents.CANVAS_SIZE,CanvasSize.SMALL)))))));
		Direction[] var5 = var4;
		int var6 = var5.length;
		int var7 = 0;
		do{
			if ((var7) < (var6)){
				Direction var8 = var5[var7];
				if (var8.getAxis().isHorizontal()){
					Direction var9 = var8.getOpposite();
					var2 = ((BlockState)(var2.setValue((Property)(FACING),(Comparable)(var9))));
					if (var2.canSurvive((LevelReader)(var1.getLevel()),var3)){
						return var2;
					}
				}
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
		return null;
	}
	public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
		return (((var2.getOpposite()) == (var1.getValue((Property)(FACING)))) && (!(var1.canSurvive((LevelReader)(var4),var5)))) ? (Blocks.AIR.defaultBlockState()) : (var1);
	}
	public BlockState rotate(BlockState var1, Rotation var2) {
		return ((BlockState)(var1.setValue((Property)(FACING),(Comparable)(var2.rotate(((Direction)(var1.getValue((Property)(FACING)))))))));
	}
	public BlockState mirror(BlockState var1, Mirror var2) {
		return var1.rotate(var2.getRotation(((Direction)(var1.getValue((Property)(FACING))))));
	}
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
		Property[] var2 = new Property[]{FACING};
		var1.add(var2);
		Property[] var3 = new Property[]{SIZE};
		var1.add(var3);
	}
	public boolean propagatesSkylightDown(BlockState var1, BlockGetter var2, BlockPos var3) {
		return true;
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean skipRendering(BlockState var1, BlockState var2, Direction var3) {
		return true;
	}
	public void playerDestroy(Level var1, Player var2, BlockPos var3, BlockState var4, @Nullable() BlockEntity var5, ItemStack var6) {
		ItemStack var7 = ((Block)(ModBlocks.WANTED_POSTER.get())).asItem().getDefaultInstance();
		if ((var5) != (null)){
			WantedPosterBlockEntity var8 = ((WantedPosterBlockEntity)(var5));
			var7.set(ModDataComponents.WANTED_POSTER_DATA,var8.getWantedPosterData());
		}
		WantedPosterItem.setCanvasSize(var7,((CanvasSize)(var4.getValue((Property)(SIZE)))));
		Block.popResource(var1,var3,var7);
	}
	public InteractionResult useWithoutItem(BlockState var1, Level var2, BlockPos var3, Player var4, BlockHitResult var5) {
		return InteractionResult.FAIL;
	}
	public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
		return new WantedPosterBlockEntity(var1,var2);
	}
}
package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;

public class DesignBarrelBlock extends Block {
	// Fields
	public static final DirectionProperty FACING = BlockStateProperties.FACING;

	public DesignBarrelBlock() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.5F).sound(SoundType.WOOD));
		this.registerDefaultState(((BlockState)(((BlockState)(this.stateDefinition.any())).setValue((Property)(FACING),(Comparable)(Direction.NORTH)))));
	}
	public RenderShape getRenderShape(BlockState var1) {
		return RenderShape.MODEL;
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
	}
	public BlockState getStateForPlacement(BlockPlaceContext var1) {
		return ((BlockState)(this.defaultBlockState().setValue((Property)(FACING),(Comparable)(var1.getNearestLookingDirection().getOpposite()))));
	}
	public InteractionResult useWithoutItem(BlockState var1, Level var2, BlockPos var3, Player var4, BlockHitResult var5) {
		var2.setBlock(var3,Blocks.BARREL.withPropertiesOf(var1),3);
		return InteractionResult.PASS;
	}
}
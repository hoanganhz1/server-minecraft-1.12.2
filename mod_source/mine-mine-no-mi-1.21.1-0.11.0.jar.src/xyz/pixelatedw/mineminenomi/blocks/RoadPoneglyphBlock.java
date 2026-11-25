package xyz.pixelatedw.mineminenomi.blocks;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.RoadPoneglyphBlockEntity;

public class RoadPoneglyphBlock extends Block implements EntityBlock {
	// Fields
	public static final IntegerProperty TEXTURE = IntegerProperty.create("texture",0,2);

	public RoadPoneglyphBlock() {
		super(BlockBehaviour.Properties.of().strength(3.4028235e+38F).noLootTable());
		this.registerDefaultState(((BlockState)(((BlockState)(this.stateDefinition.any())).setValue((Property)(TEXTURE),(Comparable)(Integer.valueOf(0))))));
	}
	public boolean propagatesSkylightDown(BlockState var1, BlockGetter var2, BlockPos var3) {
		return true;
	}
	@Nullable()
	public BlockState getStateForPlacement(BlockPlaceContext var1) {
		BlockState var2 = this.defaultBlockState();
		BlockState var3 = ((BlockState)(var2.setValue((Property)(TEXTURE),(Comparable)(Integer.valueOf(var1.getLevel().getRandom().nextInt(3))))));
		return var3;
	}
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
		Property[] var2 = new Property[]{TEXTURE};
		var1.add(var2);
	}
	public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
		return new RoadPoneglyphBlockEntity(var1,var2);
	}
}
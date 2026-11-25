package xyz.pixelatedw.mineminenomi.blocks;

import com.mojang.serialization.MapCodec;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.CustomSpawnerBlockEntity;
import xyz.pixelatedw.mineminenomi.init.ModBlockEntities;

public class CustomSpawnerBlock extends BaseEntityBlock {
	// Fields
	public static final MapCodec<CustomSpawnerBlock> CODEC = simpleCodec((Function)(((Function<BlockBehaviour.Properties, CustomSpawnerBlock>)(CustomSpawnerBlock::new))));

	public CustomSpawnerBlock() {
		super(BlockBehaviour.Properties.of().strength(3.4028235e+38F).noCollission());
	}
	public CustomSpawnerBlock(BlockBehaviour.Properties var1) {
		super(var1);
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
	@Nullable()
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level var1, BlockState var2, BlockEntityType<T> var3) {
		return (BlockEntityTicker<T>) (BlockEntityTicker) (createTickerHelper((BlockEntityType)(var3),(BlockEntityType)(((BlockEntityType)(ModBlockEntities.CUSTOM_SPAWNER.get()))),(BlockEntityTicker)(((net.minecraft.world.level.block.entity.BlockEntityTicker)(((net.minecraft.world.level.block.entity.BlockEntityTicker)(CustomSpawnerBlockEntity::tick)))))));
	}
	public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
		return new CustomSpawnerBlockEntity(var1,var2);
	}
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return (MapCodec<? extends BaseEntityBlock>) (MapCodec) (CODEC);
	}
}
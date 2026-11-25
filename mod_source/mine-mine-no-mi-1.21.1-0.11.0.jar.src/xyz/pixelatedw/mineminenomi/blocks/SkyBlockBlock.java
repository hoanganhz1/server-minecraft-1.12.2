package xyz.pixelatedw.mineminenomi.blocks;

import java.util.function.ToIntFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.helpers.BlocksHelper;

public class SkyBlockBlock extends Block {
	// Fields
	public static final IntegerProperty TYPE = IntegerProperty.create("type",0,3);

	public SkyBlockBlock() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).lightLevel((l0) -> {
			return 1;
		}).strength(0.7F).requiresCorrectToolForDrops().sound(SoundType.WOOL).isValidSpawn(BlocksHelper::never));
		this.registerDefaultState(((BlockState)(((BlockState)(this.stateDefinition.any())).setValue((Property)(TYPE),(Comparable)(Integer.valueOf(0))))));
	}
	@Nullable()
	public @Nullable() BlockState getStateForPlacement(BlockPlaceContext var1) {
		return ((BlockState)(this.defaultBlockState().setValue((Property)(TYPE),(Comparable)(Integer.valueOf(var1.getLevel().random.nextInt(4))))));
	}
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
		Property[] var2 = new Property[]{TYPE};
		var1.add(var2);
	}
	public boolean propagatesSkylightDown(BlockState var1, BlockGetter var2, BlockPos var3) {
		return true;
	}
	public void fallOn(Level var1, BlockState var2, BlockPos var3, Entity var4, float var5) {
		var4.causeFallDamage(var5,0.05F,var4.damageSources().fall());
	}
}
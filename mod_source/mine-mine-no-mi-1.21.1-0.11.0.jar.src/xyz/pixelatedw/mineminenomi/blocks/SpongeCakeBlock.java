package xyz.pixelatedw.mineminenomi.blocks;

import java.util.function.ToIntFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import xyz.pixelatedw.mineminenomi.api.helpers.BlocksHelper;

public class SpongeCakeBlock extends CakeBlock {
	// Fields
	protected static final VoxelShape[] SHAPES;

	public SpongeCakeBlock() {
		super(BlockBehaviour.Properties.of().forceSolidOn().lightLevel((l0) -> {
			return 0;
		}).strength(0.7F).sound(SoundType.WOOL).isValidSpawn(BlocksHelper::never));
	}
	public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
		return SHAPES[((Integer)(var1.getValue((Property)(BITES)))).intValue()];
	}
	public boolean propagatesSkylightDown(BlockState var1, BlockGetter var2, BlockPos var3) {
		return true;
	}
	public void fallOn(Level var1, BlockState var2, BlockPos var3, Entity var4, float var5) {
		var4.causeFallDamage(var5,0.01F,var4.damageSources().fall());
	}
	static  {
		VoxelShape[] var0 = new VoxelShape[]{Block.box(0D,0D,0D,16D,8D,16D),Block.box(3D,0D,0D,16D,8D,16D),Block.box(5D,0D,0D,16D,8D,16D),Block.box(7D,0D,0D,16D,8D,16D),Block.box(9D,0D,0D,16D,8D,16D),Block.box(11D,0D,0D,16D,8D,16D),Block.box(13D,0D,0D,16D,8D,16D)};
		SHAPES = var0;
	}
}
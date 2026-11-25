package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class PoisonBlock extends Block {
	// Fields
	protected static final VoxelShape SHAPE = Block.box(0D,0D,0D,16D,1D,16D);
	private static final int ALIVE_TICKS = 800;
	private static final int RANDOM_TICKS = 400;

	public PoisonBlock() {
		super(BlockBehaviour.Properties.of().strength(0.5F));
	}
	public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
		return SHAPE;
	}
	public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
		return SHAPE;
	}
	public boolean isFullCube(BlockState var1) {
		return false;
	}
	public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
		BlockState var4 = var2.getBlockState(var3.below());
		return (var4.isFaceSturdy((BlockGetter)(var2),var3,Direction.UP)) && (!(var4.getBlock() instanceof PoisonBlock));
	}
	public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
		return (!(var1.canSurvive((LevelReader)(var4),var5))) ? (Blocks.AIR.defaultBlockState()) : (var1);
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean skipRendering(BlockState var1, BlockState var2, Direction var3) {
		return (var2.getBlock()) == (this);
	}
	public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
		if (var4 instanceof LivingEntity){
			LivingEntity var5 = ((LivingEntity)(var4));
			if (!(var5.hasEffect((Holder)(ModEffects.DOKU_POISON)))){
				var5.addEffect(new MobEffectInstance((Holder)(ModEffects.DOKU_POISON),100,0));
			}else{
				return;
			}
		}
	}
	public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
		if (!(this.canSurvive(var1,(LevelReader)(var2),var3))){
			var2.levelEvent(2001,var3,Block.getId(var1));
			NuWorld.setBlockStateInChunk(var2,var3,Blocks.AIR.defaultBlockState(),2);
			return;
		}else{
			var2.scheduleTick(var3,(Block)(this),(800) + (var2.getRandom().nextInt(400)));
			return;
		}
	}
	public void tick(BlockState var1, ServerLevel var2, BlockPos var3, RandomSource var4) {
		var2.levelEvent(2001,var3,Block.getId(var1));
		NuWorld.setBlockStateInChunk((Level)(var2),var3,Blocks.AIR.defaultBlockState(),2);
	}
}
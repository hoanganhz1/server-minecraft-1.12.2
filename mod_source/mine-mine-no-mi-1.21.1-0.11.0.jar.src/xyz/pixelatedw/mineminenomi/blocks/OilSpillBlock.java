package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;

public class OilSpillBlock extends Block {
	// Fields
	protected static final VoxelShape SHAPE = Block.box(0D,0D,0D,16D,1D,16D);
	private int ticks;

	public OilSpillBlock() {
		super(BlockBehaviour.Properties.of().friction(0.98F).strength(0.5F).sound(SoundType.SLIME_BLOCK).randomTicks());
		this.ticks = 400;
	}
	public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
		return SHAPE;
	}
	public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
		return SHAPE;
	}
	public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
		return (Block.isShapeFullBlock(var1.getCollisionShape((BlockGetter)(var2),var3.below()))) && (Block.isFaceFull(var1.getCollisionShape((BlockGetter)(var2),var3.below()),Direction.UP));
	}
	public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
		return (!(var1.canSurvive((LevelReader)(var4),var5))) ? (Blocks.AIR.defaultBlockState()) : (var1);
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean skipRendering(BlockState var1, BlockState var2, Direction var3) {
		return (var2.getBlock()) == (this);
	}
	public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
		if (var2.getBlockState(var3.below()).isAir()){
			var2.setBlock(var3,Blocks.AIR.defaultBlockState(),3);
			return;
		}else{
			var2.scheduleTick(var3,(Block)(this),1);
			return;
		}
	}
	public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
		float var5 = (float)(Math.min(1.5D,(var4.getDeltaMovement().x) * (1.149999976158142D)));
		float var6 = (float)(Math.min(1.5D,(var4.getDeltaMovement().z) * (1.149999976158142D)));
		AbilityHelper.setDeltaMovement(var4,(double)(var5),var4.getDeltaMovement().y,(double)(var6));
	}
	public void tick(BlockState var1, ServerLevel var2, BlockPos var3, RandomSource var4) {
		if (var2.isClientSide){
			return;
		}else{
			if ((this.ticks) > (0)){
				this.ticks = (this.ticks) - (1);
				if (this.areFlamesClose((Level)(var2),var3)){
					var2.explode((Entity)(null),(double)(var3.getX()),(double)(var3.getY()),(double)(var3.getZ()),3F,true,(ServerConfig.isAbilityGriefingEnabled()) ? (Level.ExplosionInteraction.BLOCK) : (Level.ExplosionInteraction.NONE));
				}
			}else{
				var2.setBlockAndUpdate(var3,Blocks.AIR.defaultBlockState());
				this.ticks = (400) + (var4.nextInt(10));
			}
			var2.scheduleTick(var3,(Block)(this),10,TickPriority.EXTREMELY_HIGH);
			return;
		}
	}
	private boolean areFlamesClose(Level var1, BlockPos var2) {
		int var3 = 2;
		BlockPos.MutableBlockPos var4 = new BlockPos.MutableBlockPos();
		int var5 = -(var3);
		LOOP_2:
		do{
			if ((var5) < (var3)){
				int var6 = -(var3);
				LOOP_1:
				do{
					if ((var6) < (var3)){
						int var7 = -(var3);
						do{
							if ((var7) < (var3)){
								int var8 = (var2.getX()) + (var5);
								int var9 = (var2.getY()) + (var6);
								var4.set(var8,var9,(var2.getZ()) + (var7));
								if ((var1.getBlockState((BlockPos)(var4)).getBlock()) == (Blocks.FIRE)){
									return true;
								}else{
									var7++;
									continue;
								}
							}else{
								break;
							}
						} while (true);
						var6++;
						continue LOOP_1;
					}else{
						break;
					}
				} while (true);
				var5++;
				continue LOOP_2;
			}else{
				break;
			}
		} while (true);
		return false;
	}
}
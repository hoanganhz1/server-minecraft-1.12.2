package xyz.pixelatedw.mineminenomi.blocks;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.beta.BetaLauncherAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.DiableJambeAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class MucusBlock extends Block {
	// Fields
	protected static final VoxelShape SHAPE = Block.box(0D,0D,0D,16D,1D,16D);
	private int ticks;

	public MucusBlock() {
		super(BlockBehaviour.Properties.of().strength(8F).randomTicks());
		this.ticks = 1200;
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
		return var2.getBlockState(var3.below()).isFaceSturdy((BlockGetter)(var2),var3,Direction.UP);
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
			Function<AbilityData, BetaLauncherAbility> var6 = (l0) -> {
			return ((BetaLauncherAbility)(l0.getEquippedAbility(((AbilityCore)(BetaLauncherAbility.INSTANCE.get())))));
		};
			if (AbilityData.get(var5).map(var6).isPresent()){
				return;
			}else{
				if (!(var5.hasEffect((Holder)(ModEffects.STICKY)))){
					var5.addEffect(new MobEffectInstance((Holder)(ModEffects.STICKY),10,0,false,false,false));
				}else{
					return;
				}
			}
		}
	}
	public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
		var2.scheduleTick(var3,(Block)(this),1);
	}
	public void tick(BlockState var1, ServerLevel var2, BlockPos var3, RandomSource var4) {
		if (var2.isClientSide){
			return;
		}else{
			if ((this.ticks) > (0)){
				this.ticks = (this.ticks) - (1);
				if (this.areFlamesClose((Level)(var2),var3)){
					var2.explode((Entity)(null),(double)(var3.getX()),(double)(var3.getY()),(double)(var3.getZ()),4F,true,(ServerConfig.isAbilityGriefingEnabled()) ? (Level.ExplosionInteraction.BLOCK) : (Level.ExplosionInteraction.NONE));
				}
			}else{
				var2.setBlockAndUpdate(var3,Blocks.AIR.defaultBlockState());
				this.ticks = (1200) + (var4.nextInt(400));
			}
			var2.scheduleTick(var3,(Block)(this),1,TickPriority.EXTREMELY_HIGH);
			return;
		}
	}
	private boolean areFlamesClose(Level var1, BlockPos var2) {
		int var3 = 2;
		int var4 = -(var3);
		LOOP_2:
		do{
			if ((var4) < (var3)){
				int var5 = -(var3);
				LOOP_1:
				do{
					if ((var5) < (var3)){
						int var6 = -(var3);
						do{
							if ((var6) < (var3)){
								int var7 = (var2.getX()) + (var4);
								int var8 = (var2.getY()) + (var5);
								int var9 = (var2.getZ()) + (var6);
								if ((var1.getBlockState(new BlockPos(var7,var8,var9)).getBlock()) == (Blocks.FIRE)){
									return true;
								}else{
									var6++;
									continue;
								}
							}else{
								break;
							}
						} while (true);
						var5++;
						continue LOOP_1;
					}else{
						break;
					}
				} while (true);
				var4++;
				continue LOOP_2;
			}else{
				break;
			}
		} while (true);
		List var5_1 = TargetHelper.getEntitiesInArea((LevelAccessor)(var1),(LivingEntity)(null),var2,(double)(var3),TargetPredicate.EVERYTHING,LivingEntity.class);
		if (!(var5_1.isEmpty())){
			Iterator var6_1 = var5_1.iterator();
			do{
				if (var6_1.hasNext()){
					LivingEntity var7_1 = ((LivingEntity)(var6_1.next()));
					if (var7_1.isOnFire()){
						return true;
					}else{
						if (var7_1 instanceof Player){
							Player var8_1 = ((Player)(var7_1));
							Function<AbilityData, DiableJambeAbility> var9_1 = (l0) -> {
			return ((DiableJambeAbility)(l0.getEquippedAbility(((AbilityCore)(DiableJambeAbility.INSTANCE.get())))));
		};
							DiableJambeAbility var10 = ((DiableJambeAbility)(((java.util.Optional)(AbilityData.get((LivingEntity)(var8_1)))).map((Function)(var9_1)).orElse(null)));
							if (((var10) != (null)) && (var10.isContinuous())){
								return true;
							}else{
								continue;
							}
						}else{
							continue;
						}
					}
				}else{
					break;
				}
			} while (true);
		}
		return false;
	}
	public int getFireSpreadSpeed(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
		return 600;
	}
	public int getFlammability(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
		return 1000;
	}
}
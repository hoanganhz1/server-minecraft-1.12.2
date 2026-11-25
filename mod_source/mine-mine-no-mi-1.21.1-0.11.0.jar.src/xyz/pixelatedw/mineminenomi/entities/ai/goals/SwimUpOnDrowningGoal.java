package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;

public class SwimUpOnDrowningGoal extends TickedGoal<PathfinderMob> {
	// Fields
	private static final int MAX_AIR_ATTEMPTS = 20;
	private static final int NEXT_AIR_ATTEMPT_COOLDOWN = 300;
	private boolean isFishman;
	private boolean isDrowining;
	private BlockPos surfacePosition;
	private int airAttempts;
	private long lastAirAttempt;

	public SwimUpOnDrowningGoal(PathfinderMob var1) {
		super(var1);
		this.airAttempts = 0;
		this.lastAirAttempt = 0L;
		this.isFishman = ((Boolean)(EntityStatsData.get((LivingEntity)(var1)).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isFishman)))).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	public boolean canUse() {
		if (this.isFishman){
			return false;
		}else{
			if (!(((PathfinderMob)(this.entity)).isEyeInFluidType(((FluidType)(NeoForgeMod.WATER_TYPE.value()))))){
				return false;
			}else{
				if ((((PathfinderMob)(this.entity)).getAirSupply()) >= ((((PathfinderMob)(this.entity)).getMaxAirSupply()) / (2))){
					return false;
				}else{
					return true;
				}
			}
		}
	}
	public boolean canContinueToUse() {
		if (!(((PathfinderMob)(this.entity)).isEyeInFluidType(((FluidType)(NeoForgeMod.WATER_TYPE.value()))))){
			return false;
		}else{
			if ((((PathfinderMob)(this.entity)).getAirSupply()) >= ((((PathfinderMob)(this.entity)).getMaxAirSupply()) / (2))){
				return false;
			}else{
				return true;
			}
		}
	}
	public void start() {
		super.start();
		this.isDrowining = true;
		BlockPos.MutableBlockPos var1 = new BlockPos.MutableBlockPos();
		var1.set((Vec3i)(((PathfinderMob)(this.entity)).blockPosition()));
		if (((PathfinderMob)(this.entity)).level().canSeeSky(((PathfinderMob)(this.entity)).blockPosition())){
			int var2 = 0;
			do{
				if ((var2) < (20)){
					if (((PathfinderMob)(this.entity)).level().getFluidState(var1.above(var2)).isEmpty()){
						this.surfacePosition = var1.immutable();
						break;
					}else{
						var2++;
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}else{
			this.tryFindingAir();
		}
	}
	public void tick() {
		super.tick();
		if ((this.surfacePosition) != (null)){
			((PathfinderMob)(this.entity)).getNavigation().stop();
			((PathfinderMob)(this.entity)).getNavigation().moveTo((double)(this.surfacePosition.getX()),(double)(this.surfacePosition.getY()),(double)(this.surfacePosition.getZ()),1.5499999523162842D);
		}else{
			if ((((PathfinderMob)(this.entity)).level().getGameTime()) >= ((this.lastAirAttempt) + (300L))){
				this.tryFindingAir();
			}else{
				return;
			}
		}
	}
	public void stop() {
		super.stop();
		this.isDrowining = false;
		this.surfacePosition = null;
		this.airAttempts = 0;
	}
	private void tryFindingAir() {
		do{
			if ((this.airAttempts) < (20)){
				this.surfacePosition = this.lookForAir((LevelAccessor)(((PathfinderMob)(this.entity)).level()),40,40);
				if ((this.surfacePosition) != (null)){
					break;
				}else{
					this.airAttempts = (this.airAttempts) + (1);
					continue;
				}
			}else{
				break;
			}
		} while (true);
		this.lastAirAttempt = ((PathfinderMob)(this.entity)).level().getGameTime();
	}
	@Nullable()
	protected BlockPos lookForAir(LevelAccessor var1, int var2, int var3) {
		BlockPos var4 = ((PathfinderMob)(this.entity)).blockPosition();
		int var5 = var4.getX();
		int var6 = var4.getY();
		int var7 = var4.getZ();
		float var8 = (float)((((var2) * (var2)) * (var3)) * (2));
		BlockPos var9 = null;
		BlockPos.MutableBlockPos var10 = new BlockPos.MutableBlockPos();
		int var11 = (var5) - (var2);
		LOOP_2:
		do{
			if ((var11) <= ((var5) + (var2))){
				int var12 = (var6) - (var3);
				LOOP_1:
				do{
					if ((var12) <= ((var6) + (var3))){
						int var13 = (var7) - (var2);
						do{
							if ((var13) <= ((var7) + (var2))){
								var10.set(var11,var12,var13);
								if (var1.getFluidState((BlockPos)(var10)).isEmpty()){
									float var14 = (float)(((((var11) - (var5)) * ((var11) - (var5))) + (((var12) - (var6)) * ((var12) - (var6)))) + (((var13) - (var7)) * ((var13) - (var7))));
									if ((var14) < (var8)){
										var8 = var14;
										var9 = var10.immutable();
										var13++;
										continue;
									}else{
										var13++;
										continue;
									}
								}else{
									var13++;
									continue;
								}
							}else{
								break;
							}
						} while (true);
						var12++;
						continue LOOP_1;
					}else{
						break;
					}
				} while (true);
				var11++;
				continue LOOP_2;
			}else{
				break;
			}
		} while (true);
		return var9;
	}
}
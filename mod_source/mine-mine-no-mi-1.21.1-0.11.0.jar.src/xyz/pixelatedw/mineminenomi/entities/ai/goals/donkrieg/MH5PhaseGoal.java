package xyz.pixelatedw.mineminenomi.entities.ai.goals.donkrieg;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInfo;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.mobs.BruteEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.GruntEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.kriegpirates.DonKriegEntity;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;

public class MH5PhaseGoal extends TickedGoal<DonKriegEntity> {
	// Fields
	private BlockPos[] spawnPositions;
	private int findIter;
	private int spawnIter;
	private final FactionsWorldData worldData;
	private final int spawnIterMax;
	private final int spawnRate;

	public MH5PhaseGoal(DonKriegEntity var1) {
		super(var1);
		this.spawnPositions = new BlockPos[4];
		this.findIter = 0;
		this.spawnIter = 0;
		this.worldData = FactionsWorldData.get();
		this.spawnRate = (var1.isDifficultyHardOrAbove()) ? (40) : (60);
		this.spawnIterMax = (var1.isDifficultyHardOrAbove()) ? (7) : (5);
	}
	public boolean canUse() {
		if ((this.hasSpawnArrayEmptyPos()) && ((this.findIter) < (100))){
			this.findSpawnPos();
			return false;
		}else{
			if (!(((DonKriegEntity)(this.entity)).hasMH5PhaseActive())){
				return false;
			}else{
				return true;
			}
		}
	}
	public boolean canContinueToUse() {
		if (!(((DonKriegEntity)(this.entity)).hasMH5PhaseActive())){
			return false;
		}else{
			if ((this.spawnIter) >= (this.spawnIterMax)){
				return false;
			}else{
				return true;
			}
		}
	}
	public void start() {
		super.start();
	}
	public void tick() {
		super.tick();
		if (((DonKriegEntity)(this.entity)).level().isClientSide){
			return;
		}else{
			if (((this.getTickCount()) % ((long)(this.spawnRate))) == (0L)){
				BlockPos[] var1 = this.spawnPositions;
				int var2 = var1.length;
				int var3 = 0;
				do{
					if ((var3) < (var2)){
						BlockPos var4 = var1[var3];
						if ((var4) == (null)){
							var3++;
							continue;
						}else{
							OPEntity var5 = null;
							if ((((((DonKriegEntity)(this.entity)).isDifficultyHardOrAbove()) && ((((DonKriegEntity)(this.entity)).getRandom().nextInt(3)) == (0))) ? (1) : (0)) != (0)){
								var5 = BruteEntity.createPirateBrute(((EntityType)(ModMobs.PIRATE_BRUTE.get())),((DonKriegEntity)(this.entity)).level());
								var5.setPos((double)(var4.getX()),(double)(var4.getY()),(double)(var4.getZ()));
								this.worldData.addTemporaryCrewMember(ModNPCGroups.KRIEG_PIRATES,(LivingEntity)(var5));
							}else{
								var5 = GruntEntity.createPirateGrunt(((EntityType)(ModMobs.PIRATE_GRUNT.get())),((DonKriegEntity)(this.entity)).level());
								var5.setPos((double)(var4.getX()),(double)(var4.getY()),(double)(var4.getZ()));
								this.worldData.addTemporaryCrewMember(ModNPCGroups.KRIEG_PIRATES,(LivingEntity)(var5));
							}
							if ((((DonKriegEntity)(this.entity)).getChallengeInfo().getChallengerGroup().size()) > (1)){
								var5.setTarget(((LivingEntity)(WyHelper.shuffle((Collection)(((DonKriegEntity)(this.entity)).getChallengeInfo().getChallengerGroup())).get(0))));
							}else{
								var5.setTarget(((DonKriegEntity)(this.entity)).getTarget());
							}
							((DonKriegEntity)(this.entity)).level().addFreshEntity((Entity)(var5));
							if ((((DonKriegEntity)(this.entity)).getRandom().nextInt(3)) == (0)){
								var5.setItemSlot(EquipmentSlot.HEAD,((Item)(ModArmors.MH5_GAS_MASK.get())).getDefaultInstance());
								var3++;
								continue;
							}else{
								var3++;
								continue;
							}
						}
					}else{
						break;
					}
				} while (true);
				this.spawnIter = (this.spawnIter) + (1);
			}
			return;
		}
	}
	public void stop() {
		super.stop();
		this.cleanSpawnArray();
		this.findIter = 0;
	}
	private void findSpawnPos() {
		BlockPos var1 = WyHelper.findValidGroundLocation(((DonKriegEntity)(this.entity)).level(),((DonKriegEntity)(this.entity)).blockPosition(),20,10);
		if ((var1) != (null)){
			int var2 = 0;
			do{
				if ((var2) < (this.spawnPositions.length)){
					if ((this.spawnPositions[var2]) == (null)){
						this.spawnPositions[var2] = var1;
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
			this.findIter = (this.findIter) + (1);
		}
	}
	private boolean hasSpawnArrayEmptyPos() {
		boolean var1 = false;
		BlockPos[] var2 = this.spawnPositions;
		int var3 = var2.length;
		int var4 = 0;
		do{
			if ((var4) < (var3)){
				if ((var2[var4]) == (null)){
					var1 = true;
					break;
				}else{
					var4++;
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return var1;
	}
	private void cleanSpawnArray() {
		Arrays.fill((Object[])(this.spawnPositions),(Object)(null));
	}
}
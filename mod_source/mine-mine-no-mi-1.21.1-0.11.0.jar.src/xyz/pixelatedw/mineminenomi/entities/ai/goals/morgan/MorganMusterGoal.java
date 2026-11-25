package xyz.pixelatedw.mineminenomi.entities.ai.goals.morgan;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.IMobDetails;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.SniperEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.marines.MorganEntity;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class MorganMusterGoal extends TickedGoal<MorganEntity> {
	// Fields
	private boolean used;

	public MorganMusterGoal(MorganEntity var1) {
		super(var1);
	}
	public boolean canUse() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			if (this.used){
				return false;
			}else{
				if (((MorganEntity)(this.entity)).isDifficultyStandard()){
					return false;
				}else{
					if (((double)(((MorganEntity)(this.entity)).getHealth())) > (WyHelper.percentage(50D,(double)(((MorganEntity)(this.entity)).getMaxHealth())))){
						return false;
					}else{
						return true;
					}
				}
			}
		}
	}
	public boolean canContinueToUse() {
		return false;
	}
	public void start() {
		super.start();
		EntityType var1 = ((EntityType)(ModMobs.MARINE_SNIPER.get()));
		((SniperEntity)(var1.spawn(((ServerLevel)(((MorganEntity)(this.entity)).level())),new BlockPos(-25,64,0),MobSpawnType.EVENT))).setFactionInit((IMobDetails)(((xyz.pixelatedw.mineminenomi.api.entities.IMobDetails)(SniperEntity::initMarine))));
		EntityType var2 = ((EntityType)(ModMobs.MARINE_SNIPER.get()));
		((SniperEntity)(var2.spawn(((ServerLevel)(((MorganEntity)(this.entity)).level())),new BlockPos(25,64,0),MobSpawnType.EVENT))).setFactionInit((IMobDetails)(((xyz.pixelatedw.mineminenomi.api.entities.IMobDetails)(SniperEntity::initMarine))));
		EntityType var3 = ((EntityType)(ModMobs.MARINE_SNIPER.get()));
		((SniperEntity)(var3.spawn(((ServerLevel)(((MorganEntity)(this.entity)).level())),new BlockPos(0,64,25),MobSpawnType.EVENT))).setFactionInit((IMobDetails)(((xyz.pixelatedw.mineminenomi.api.entities.IMobDetails)(SniperEntity::initMarine))));
		EntityType var4 = ((EntityType)(ModMobs.MARINE_SNIPER.get()));
		((SniperEntity)(var4.spawn(((ServerLevel)(((MorganEntity)(this.entity)).level())),new BlockPos(0,64,-25),MobSpawnType.EVENT))).setFactionInit((IMobDetails)(((xyz.pixelatedw.mineminenomi.api.entities.IMobDetails)(SniperEntity::initMarine))));
		this.used = true;
	}
}
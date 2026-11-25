package xyz.pixelatedw.mineminenomi.api.entities.revenge;

import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;

public class GankingRevengeCheck extends BaseRevengeCheck implements ITickRevengeCheck {
	// Fields
	private final int revengeGain;
	private final float gankRadius;

	public GankingRevengeCheck(int var1, float var2) {
		this.revengeGain = var1;
		this.gankRadius = var2;
	}
	public boolean check(LivingEntity var1) {
		if ((TargetHelper.getEntitiesInArea((LevelAccessor)(var1.level()),var1,(double)(this.gankRadius),(TargetPredicate)(null),LivingEntity.class).size()) > (1)){
			return true;
		}else{
			return false;
		}
	}
	public int revengeMeterGain() {
		return this.revengeGain;
	}
}
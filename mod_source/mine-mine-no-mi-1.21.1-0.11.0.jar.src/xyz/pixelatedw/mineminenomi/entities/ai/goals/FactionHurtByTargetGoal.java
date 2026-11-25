package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;

public class FactionHurtByTargetGoal extends HurtByTargetGoal {
	// Fields
	@Nullable() private Predicate<Entity> factionPredicate;

	public FactionHurtByTargetGoal(PathfinderMob var1, @Nullable() Predicate<Entity> var2, Class<?>... var3) {
		super(var1,var3);
		this.factionPredicate = var2;
	}
	public boolean canContinueToUse() {
		if (((this.factionPredicate) != (null)) && (!(((java.util.function.Predicate)(this.factionPredicate)).test((Object)(this.targetMob))))){
			return false;
		}else{
			return super.canContinueToUse();
		}
	}
	protected void alertOther(Mob var1, LivingEntity var2) {
		Function var3 = (Function) ((Function<EntityStatsData, Optional>) ((l0) -> {
			return l0.getFaction();
		}));
		Faction var4 = ((Faction)(EntityStatsData.get((LivingEntity)(this.mob)).flatMap(var3).orElse(null)));
		Function var5 = (Function) ((Function<EntityStatsData, Optional>) ((l0) -> {
			return l0.getFaction();
		}));
		Faction var6 = ((Faction)(EntityStatsData.get((LivingEntity)(var1)).flatMap(var5).orElse(null)));
		if (((var4) != (null)) && (((var6) != (null)) && (var4.equals(var6)))){
			super.alertOther(var1,var2);
			return;
		}else{
			return;
		}
	}
	protected boolean canAttack(@Nullable() LivingEntity var1, TargetingConditions var2) {
		if (((this.factionPredicate) != (null)) && (((java.util.function.Predicate)(this.factionPredicate)).test((Object)(var1)))){
			return super.canAttack(var1,var2);
		}else{
			return false;
		}
	}
}
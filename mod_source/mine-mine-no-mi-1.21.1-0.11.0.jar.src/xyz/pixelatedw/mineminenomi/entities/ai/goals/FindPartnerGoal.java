package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInfo;
import xyz.pixelatedw.mineminenomi.api.challenges.IChallengeBoss;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.ai.IGoalPartner;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;

public class FindPartnerGoal<P extends Mob & IGoalPartner<T>, T extends Mob & IGoalPartner<P>> extends TickedGoal<T> {
	// Fields
	private static final int COOLDOWN = 100;
	private Predicate<Entity> partnerTest;
	private InProgressChallenge challenge;
	private boolean setPartnerBack;

	public FindPartnerGoal(T var1, EntityType<P> var2) {
		super(var1);
		EntityType<P> var3 = var2;
		final EntityType<P> var3_f1 = var3;
		Predicate<Entity> var4 = (l0) -> {
			if (((java.lang.Object)(l0.getType())).equals((Object)(var3_f1))){
				return true;
			}else{
				return false;
			}
		};
		this.partnerTest = var4;
	}
	public FindPartnerGoal<P, T> setPartnerBack(boolean var1) {
		this.setPartnerBack = var1;
		return this;
	}
	public boolean canUse() {
		if (((((IGoalPartner)(this.entity)).getPartner()) != (null)) && (((Mob)(((IGoalPartner)(this.entity)).getPartner())).isAlive())){
			return false;
		}else{
			T var1 = (T) (this.entity);
			if (var1 instanceof IChallengeBoss){
				IChallengeBoss var2 = ((IChallengeBoss)(var1));
				this.challenge = var2.getChallengeInfo().getInProgressChallengeData();
			}
			if (!(this.hasTimePassedSinceLastEnd(100F))){
				return false;
			}else{
				return true;
			}
		}
	}
	public boolean canContinueToUse() {
		return false;
	}
	public void start() {
		if ((this.challenge) != (null)){
			Optional var1 = this.challenge.getEnemies().stream().filter(this.partnerTest).findFirst();
			if (var1.isPresent()){
				this.setPartner((P)(((Mob)(var1.get()))));
			}else{
				return;
			}
		}else{
			List var1_1 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.entity.level()),(LivingEntity)(this.entity),20D,new TargetPredicate().selector(this.partnerTest),LivingEntity.class);
			var1_1.remove(this.entity);
			this.setPartner((P)(((Mob)(((java.util.Optional)(var1_1.stream().findAny())).orElse((Object)(null))))));
		}
	}
	private void setPartner(P var1) {
		if (((var1) != (null)) && (var1.isAlive())){
			((IGoalPartner)(this.entity)).setPartner(var1);
			if (this.setPartnerBack){
				((IGoalPartner)(var1)).setPartner(this.entity);
			}else{
				return;
			}
		}
	}
}
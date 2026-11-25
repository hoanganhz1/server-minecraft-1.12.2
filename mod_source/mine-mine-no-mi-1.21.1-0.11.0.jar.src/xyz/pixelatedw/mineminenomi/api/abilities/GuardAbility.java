package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MobEffectComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ContinuousComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.GuardTooltip;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public abstract class GuardAbility extends Ability {
	// Fields
	protected final ContinuousComponent continuousComponent;
	protected final ChangeStatsComponent statsComponent;
	protected final DamageTakenComponent damageTakenComponent;
	protected final PoolComponent poolComponent;
	protected final MobEffectComponent effectComponent;
	private float totalGuarded;

	public GuardAbility(AbilityCore<? extends GuardAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),this.isParallel());
		this.statsComponent = new ChangeStatsComponent((IAbility)(this));
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnHurtEvent(200,(DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::onHurtEvent))));
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.GUARD_ABILITY,new AbilityPool[0]);
		this.effectComponent = new MobEffectComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.statsComponent,this.damageTakenComponent,this.poolComponent,this.effectComponent};
		this.addComponents(var2);
		this.addUseEvent(200,(Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.clearTotalGuarded();
		this.continuousComponent.triggerContinuity(var1,this.getHoldTime());
	}
	private float onHurtEvent(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		if (this.continuousComponent.isContinuous()){
			if (((!(this.getContinueUseChecksStream().anyMatch((Predicate)(((Predicate<Ability$ICanUseEvent>)((l0) -> {
			return l0.canUse(var1,var2).isFail();
		})))))) ? (1) : (0)) == (0)){
				return var4;
			}else{
				GuardAbility$GuardValue var5 = this.getGuard(var1);
				int var6 = 0;
				if (this.canGuardBreak(var1)){
					if ((var5.getBreakKind()) == (GuardAbility$GuardBreakKind.PER_HIT)){
						var6 = (((var5.getBreakLimit()) > (0F)) && ((var4) > (var5.getBreakLimit()))) ? (1) : (0);
					}else{
						if ((var5.getBreakKind()) == (GuardAbility$GuardBreakKind.TOTAL)){
							var6 = (((var5.getBreakLimit()) > (0F)) && ((this.totalGuarded) > (var5.getBreakLimit()))) ? (1) : (0);
							this.totalGuarded = (this.totalGuarded) + (var4);
						}
					}
				}
				float var7 = var4;
				if ((var6) == (0)){
					switch (var5.kind.ordinal()){
					case 0:

					case 1:

					default:
						var7 = (var7) - (0F);
						this.onGuard(var1,var3,var4,var7);
					}
				}
				float var8 = Math.max(var7,0F);
				return var8;
			}
		}else{
			return var4;
		}
	}
	public void clearTotalGuarded() {
		this.totalGuarded = 0F;
	}
	public void blockMovement(LivingEntity var1) {
		this.blockMovement(var1,2147483647);
	}
	public void blockMovement(LivingEntity var1, int var2) {
		this.effectComponent.applyEffect(var1,(Holder)(ModEffects.MOVEMENT_BLOCKED),var2,0);
	}
	public void removeMovementBlock(LivingEntity var1) {
		this.effectComponent.removeEffect(var1,(Holder)(ModEffects.MOVEMENT_BLOCKED));
	}
	public abstract void onGuard(LivingEntity var0, DamageSource var1, float var2, float var3);
	public abstract GuardAbility$GuardValue getGuard(LivingEntity var0);
	public float getHoldTime() {
		return -1F;
	}
	public boolean isParallel() {
		return true;
	}
	public boolean canGuardBreak(LivingEntity var1) {
		return (this.getGuard(var1).getBreakLimit()) > (0F);
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		GuardAbility var3 = this;
		Consumer<ContinuousComponentTooltip> var4 = (l0) -> {
			l0.holdTime(this.getHoldTime());
		};
		ContinuousComponentTooltip.generate(var2,var4);
		GuardAbility var5 = this;
		Player var6 = var1;
		final Player var6_f1 = var6;
		Consumer<GuardTooltip> var7 = (l0) -> {
			l0.guard(this.getGuard((LivingEntity)(var6_f1))).canBreak(this.canGuardBreak((LivingEntity)(var6_f1)));
		};
		GuardTooltip.generate(var2,var7);
	}
}
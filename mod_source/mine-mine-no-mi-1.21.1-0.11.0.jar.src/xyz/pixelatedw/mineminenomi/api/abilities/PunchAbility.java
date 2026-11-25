package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$HitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ChangeStatsComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ContinuousComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.PunchDamageTooltip;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;

public abstract class PunchAbility extends Ability {
	// Fields
	protected final ContinuousComponent continuousComponent;
	protected final ChangeStatsComponent statsComponent;
	protected final HitTriggerComponent hitTriggerComponent;
	protected final PoolComponent poolComponent;
	private int uses;
	private boolean markForStopping;
	private boolean consumedUse;

	public PunchAbility(AbilityCore<? extends PunchAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),this.isParallel()).addStartEvent(90,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent(90,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::tickContinuityEvent)))).addEndEvent(200,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.statsComponent = new ChangeStatsComponent((IAbility)(this));
		this.hitTriggerComponent = new HitTriggerComponent((IAbility)(this)).addTryHitEvent(200,(HitTriggerComponent$ITryHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent)(this::tryHitEvent)))).addOnHitEvent(200,(HitTriggerComponent$IOnHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent)(this::onHitEvent))));
		this.poolComponent = new PoolComponent((IAbility)(this),ServerConfig.getPunchAbilityLimit(),ModAbilityPools.PUNCH_ABILITY,new AbilityPool[0]);
		this.uses = 0;
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.statsComponent,this.hitTriggerComponent,this.poolComponent};
		this.addComponents(var2);
		this.addUseEvent(200,(Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,this.getPunchHoldTime());
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.uses = 0;
		this.markForStopping = false;
		this.consumedUse = false;
		this.statsComponent.applyModifiers(var1);
	}
	private void tickContinuityEvent(LivingEntity var1, IAbility var2) {
		if (this.markForStopping){
			this.continuousComponent.stopContinuity(var1);
			this.markForStopping = false;
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.statsComponent.removeModifiers(var1);
		float var3 = this.getPunchCooldown();
		if (((var3) > (0F)) && (this.consumedUse)){
			this.cooldownComponent.startCooldown(var1,var3);
		}
		this.consumedUse = false;
	}
	private HitTriggerComponent$HitResult tryHitEvent(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		if (!(this.continuousComponent.isContinuous())){
			return HitTriggerComponent$HitResult.PASS;
		}else{
			boolean var5 = var3.getDirectEntity() instanceof Projectile;
			if ((isDirectHit(var3)) && (!((!(var5)) && (this.canTriggerHit(var1).isFail())))){
				if (((!(this.getContinueUseChecksStream().anyMatch((Predicate)(((Predicate<Ability$ICanUseEvent>)((l0) -> {
			return l0.canUse(var1,var4).isFail();
		})))))) ? (1) : (0)) == (0)){
					return HitTriggerComponent$HitResult.PASS;
				}else{
					if ((this.getPunchDamage()) > (0F)){
						IDamageSourceHandler.getHandler(var3).addAbilityDamage(this.getPunchDamage(),this.getCore());
					}
					return HitTriggerComponent$HitResult.HIT;
				}
			}else{
				return HitTriggerComponent$HitResult.PASS;
			}
		}
	}
	private boolean onHitEvent(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		boolean var5 = this.onHitEffect(var1,var2,var3);
		this.increaseUses();
		return var5;
	}
	protected void consumeUseAndStop() {
		this.consumedUse = true;
		this.markForStopping = true;
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		PunchAbility var3 = this;
		Consumer<CooldownComponentTooltip> var4 = (l0) -> {
			l0.cooldown(this.getPunchCooldown());
		};
		CooldownComponentTooltip.generate(var2,var4);
		PunchAbility var5 = this;
		Consumer<ContinuousComponentTooltip> var6 = (l0) -> {
			l0.holdTime(this.getPunchHoldTime());
		};
		ContinuousComponentTooltip.generate(var2,var6);
		PunchAbility var7 = this;
		Consumer<PunchDamageTooltip> var8 = (l0) -> {
			l0.damage(this.getPunchDamage());
		};
		PunchDamageTooltip.generate(var2,var8);
		Consumer<ChangeStatsComponentTooltip> var9 = (l0) -> {
		};
		ChangeStatsComponentTooltip.generate(var2,var9);
	}
	protected void increaseUses() {
		this.uses = (this.uses) + (1);
		if (((this.getUseLimit()) > (0)) && ((this.uses) >= (this.getUseLimit()))){
			this.consumeUseAndStop();
		}
	}
	public float getPunchDamage() {
		return 0F;
	}
	public abstract float getPunchCooldown();
	public abstract boolean onHitEffect(LivingEntity var0, LivingEntity var1, DamageSource var2);
	public float getPunchHoldTime() {
		return -1F;
	}
	public boolean isParallel() {
		return false;
	}
	public Result canTriggerHit(LivingEntity var1) {
		return AbilityUseConditions.requiresEmptyHand(var1,this);
	}
	public abstract int getUseLimit();
	public static boolean isDirectHit(DamageSource var0) {
		Entity var1 = var0.getEntity();
		Entity var2 = var0.getDirectEntity();
		IDamageSourceHandler var3 = IDamageSourceHandler.getHandler(var0);
		int var4 = (((var1) == (var2)) && ((var0.is(DamageTypes.PLAYER_ATTACK)) || ((var0.is(DamageTypes.MOB_ATTACK)) || (var3.hasType(SourceType.VANILLA))))) ? (1) : (0);
		boolean var5 = var3.hasType(SourceType.FIST);
		int var6 = ((!(var0.is(DamageTypeTags.IS_EXPLOSION))) && ((!(var3.hasElement(SourceElement.EXPLOSION))) && (!(var3.hasElement(SourceElement.SHOCKWAVE))))) ? (1) : (0);
		boolean var7 = var3.hasType(SourceType.INDIRECT);
		return (((var4) != (0)) || (var5)) && (((var6) != (0)) && (!(var7)));
	}
}
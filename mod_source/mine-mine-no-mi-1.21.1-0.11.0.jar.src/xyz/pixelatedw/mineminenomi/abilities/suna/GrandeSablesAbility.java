package xyz.pixelatedw.mineminenomi.abilities.suna;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DashComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GrandeSablesAbility extends Ability {
	// Fields
	private static final float COOLDOWN_BONUS = 0.8F;
	private static final int COOLDOWN = 200;
	private static final int HOLD_TIME = 50;
	private static final float DAMAGE = 8F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GrandeSablesAbility>> INSTANCE;
	private final ContinuousComponent continuityComponent;
	private final HitTrackerComponent hitTrackerComponent;
	private final DealDamageComponent dealDamageComponent;
	private final DashComponent dashComponent;
	private Interval particleInterval;

	public GrandeSablesAbility(AbilityCore<GrandeSablesAbility> var1) {
		super((AbilityCore)(var1));
		this.continuityComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.hitTrackerComponent = new HitTrackerComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.dashComponent = new DashComponent((IAbility)(this));
		this.particleInterval = new Interval(15);
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuityComponent,this.hitTrackerComponent,this.dealDamageComponent,this.dashComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresDryUser))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuityComponent.triggerContinuity(var1,50F);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.hitTrackerComponent.clearHits();
		AbilityHelper.setDeltaMovement((Entity)(var1),0D,2D,0D);
		this.particleInterval.restartIntervalToZero();
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.getBonusManager().removeBonus(SunaHelper.DESERT_COOLDOWN_BONUS);
		if (SunaHelper.isFruitBoosted(var1)){
			this.cooldownComponent.getBonusManager().addBonus(SunaHelper.DESERT_COOLDOWN_BONUS,"Desert Cooldown Bonus",BonusOperation.MUL,0.8F);
		}
		this.cooldownComponent.startCooldown(var1,200F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[2];
			((java.lang.Object[]) (lv2_2))[0] = ImmutablePair.of((Object)("Surrounds the user into a sand tornado, increasing their speed and pulling all nearby entities towards it damaging them."),(Object)(null));
			Object[] lv2_3 = new Object[1];
			int lv2_4 = Math.round(19.999998F);
			String lv2_5 = "§a" + lv2_4 + "%§r";
			lv2_3[0] = lv2_5;
			((java.lang.Object[]) (lv2_2))[1] = ImmutablePair.of((Object)("While in a desert the cooldown of this ability is reduced by %s."),lv2_3);
			Component[] lv2_6 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityDescriptionLine$IDescriptionLine[] lv2_7 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F),ContinuousComponent.getTooltip(50F),DealDamageComponent.getTooltip(8F)};
			SourceType[] lv2_8 = new SourceType[]{SourceType.INTERNAL};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GrandeSablesAbility::new)))).addDescriptionLine(lv2_6).addAdvancedDescriptionLine(lv2_7).setSourceType(lv2_8).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("grande_sables","Grande Sables",var0));
	}

	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if ((this.continuityComponent.getContinueTime()) < (3F)){
			return;
		}else{
			this.dashComponent.dashXYZ(var1,1.5F,0F);
			var1.addEffect(new MobEffectInstance((Holder)(ModEffects.VANISH),10,0,false,false));
			if (this.particleInterval.canTick()){
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.SABLES.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
			}
			float var3 = 8F;
			float var4 = 20F;
			AABB var5 = new AABB(var1.blockPosition()).inflate((double)(var3),(double)(var4),(double)(var3));
			AbilityDamageSource var6 = this.dealDamageComponent.getDamageSource(var1);
			IDamageSourceHandler var7 = IDamageSourceHandler.getHandler(var6);
			var7.bypassLogia();
			var7.addAbilityPiercing(1F,this.getCore());
			LivingEntity var8 = var1;
			final LivingEntity var8_f1 = var8;
			Predicate<Entity> var9 = (l0) -> {
			return (l0) != (var8_f1);
		};
			Iterator var10 = var1.level().getEntitiesOfClass(Entity.class,var5,var9).iterator();
			do{
				if (var10.hasNext()){
					Entity var11 = ((Entity)(var10.next()));
					AbilityHelper.setDeltaMovement(var11,(var11.getDeltaMovement().x) + (((var1.getX()) - (var11.getX())) / (20D)),(var11.getDeltaMovement().y) + (((var1.getY()) - (var11.getY())) / (20D)),(var11.getDeltaMovement().z) + (((var1.getZ()) - (var11.getZ())) / (20D)));
					if ((this.hitTrackerComponent.canHit(var11)) && ((var11 instanceof LivingEntity) && ((var1.distanceTo(var11)) < (2F)))){
						this.dealDamageComponent.hurtTarget(var1,((LivingEntity)(var11)),8F,var6);
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return;
		}
	}}
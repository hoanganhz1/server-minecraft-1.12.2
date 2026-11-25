package xyz.pixelatedw.mineminenomi.abilities.supa;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DashComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.supa.SparklingDaisyAftereffectProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.BreakingBlocksParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class SparklingDaisyAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 140F;
	private static final int HOLD_TIME = 20;
	private static final int CHARGE_TIME = 40;
	private static final float RANGE = 1.6F;
	private static final float DAMAGE = 25F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SparklingDaisyAbility>> INSTANCE;
	private final HitTrackerComponent hitTrackerComponent;
	private final ChargeComponent chargeComponent;
	private final AnimationComponent animationComponent;
	private final ContinuousComponent continuousComponent;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;
	private final ProjectileComponent projectileComponent;
	private final ExplosionComponent explosionComponent;
	private final DashComponent dashComponent;
	private BreakingBlocksParticleEffect$Details details;
	private boolean isFull;
	private int projectiles;

	public SparklingDaisyAbility(AbilityCore<SparklingDaisyAbility> var1) {
		super((AbilityCore)(var1));
		this.hitTrackerComponent = new HitTrackerComponent((IAbility)(this));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent((ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::chargeStartsEvent)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::chargeEndsEvent))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::continuousTickEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::continuousEndsEvent))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<SparklingDaisyAftereffectProjectile>) (this::createProjectile))))));
		this.explosionComponent = new ExplosionComponent((IAbility)(this));
		this.dashComponent = new DashComponent((IAbility)(this));
		this.details = new BreakingBlocksParticleEffect$Details(20);
		this.isFull = false;
		this.projectiles = 1;
		AbilityComponent[] var2 = new AbilityComponent[]{this.hitTrackerComponent,this.chargeComponent,this.animationComponent,this.continuousComponent,this.rangeComponent,this.dealDamageComponent,this.explosionComponent,this.dashComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.hitTrackerComponent.clearHits();
		if (this.isFull){
			this.chargeComponent.startCharging(var1,40F);
		}else{
			this.dashComponent.timedDashXYZ(var1,2F,20);
			this.continuousComponent.startContinuity(var1,20F);
		}
	}
	private void chargeStartsEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.start(var1,ModAnimations.SPARKLING_DAISY_CHARGE);
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),40,0));
	}
	private void chargeEndsEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.stop(var1);
		this.dashComponent.timedDashXYZ(var1,2F,20);
		this.continuousComponent.startContinuity(var1,20F);
		var1.removeEffect((Holder)(ModEffects.MOVEMENT_BLOCKED));
	}
	private void continuousEndsEvent(LivingEntity var1, IAbility var2) {
		if (this.isFull){
			if ((this.projectiles) > (0)){
				int var3 = -(this.projectiles);
				do{
					if ((var3) <= (this.projectiles)){
						SparklingDaisyAftereffectProjectile var4 = ((SparklingDaisyAftereffectProjectile)(this.projectileComponent.getNewProjectile(var1)));
						var4.shootFromRotation((Entity)(var1),var1.getXRot(),(var1.getYRot()) + ((float)((var3) * (55))),0F,0.85F,1F);
						var1.level().addFreshEntity((Entity)(var4));
						var3++;
						continue;
					}else{
						break;
					}
				} while (true);
			}
			this.animationComponent.stop(var1);
		}
		this.cooldownComponent.startCooldown(var1,140F);
	}
	public void setFullForm() {
		this.isFull = true;
	}
	public void setProjectilesAmount(int var1) {
		this.projectiles = var1;
	}
	private SparklingDaisyAftereffectProjectile createProjectile(LivingEntity var1) {
		return new SparklingDaisyAftereffectProjectile(var1.level(),var1,(IAbility)(this));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Launches the user forward, slicing anything in their path"),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityDescriptionLine$IDescriptionLine[] lv2_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(140F),RangeComponent.getTooltip(1.6F,RangeComponent$RangeType.AOE),DealDamageComponent.getTooltip(25F)};
			SourceType[] lv2_5 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SparklingDaisyAbility::new)))).addDescriptionLine(lv2_3).addAdvancedDescriptionLine(lv2_4).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv2_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("sparkling_daisy","Sparkling Daisy",var0));
	}

	private void continuousTickEvent(LivingEntity var1, IAbility var2) {
		if ((var1.isAlive()) && (!(var1.level().isClientSide))){
			Iterator var3 = this.rangeComponent.getTargetsInLine(var1,1.6F,1.6F).iterator();
			do{
				if (var3.hasNext()){
					LivingEntity var4 = ((LivingEntity)(var3.next()));
					if (this.hitTrackerComponent.canHit((Entity)(var4))){
						this.dealDamageComponent.hurtTarget(var1,var4,25F);
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			LivingEntity var4_1 = var1;
			final LivingEntity var4_1_f1 = var4_1;
			Predicate<BlockPos> var5 = (l0) -> {
			return ((double)(l0.getY())) >= (var4_1_f1.getY());
		};
			SimpleBlockPlacer var6 = new SimpleBlockPlacer().setBlock(Blocks.AIR.defaultBlockState()).setRule(DefaultProtectionRules.CORE_FOLIAGE_ORE).setSize(3).setPositionPredicate(var5);
			var6.generate(var1.level(),var1.blockPosition(),BlockGenerators.CUBE);
			if ((var6.getPlacedPositions().size()) > (0)){
				this.details.setPositions((List)(Lists.newArrayList((Iterable)(var6.getPlacedPositions()))));
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.BREAKING_BLOCKS.get())),(Entity)(var1),0D,0D,0D,(ParticleEffect$Details)(this.details));
			}else{
				return;
			}
		}
	}}
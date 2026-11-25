package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.GuardAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.GuardAbility$GuardValue;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class FujiazamiAbility extends GuardAbility {
	// Fields
	private static final int HOLD_TIME = 80;
	private static final int MIN_COOLDOWN = 80;
	private static final float MAX_COOLDOWN = 200F;
	private static final GuardAbility$GuardValue WALK_GUARD = GuardAbility$GuardValue.percentage(0.5F);
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<FujiazamiAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final AnimationComponent animationComponent;
	private Interval particleInterval;

	public FujiazamiAbility(AbilityCore<FujiazamiAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.particleInterval = new Interval(2);
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.animationComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(ToriPhoenixHelper::requiresEitherPoint))));
		this.addContinueUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(ToriPhoenixHelper::requiresEitherPoint))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresInAir))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,80F);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.particleInterval.restartIntervalToZero();
		this.animationComponent.start(var1,ModAnimations.CROSSED_ARMS);
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.stop(var1);
		float var3 = (80F) + ((this.continuousComponent.getContinueTime()) * (1.5F));
		this.cooldownComponent.startCooldown(var1,var3);
	}
	public GuardAbility$GuardValue getGuard(LivingEntity var1) {
		return WALK_GUARD;
	}
	public boolean canGuardBreak(LivingEntity var1) {
		return false;
	}
	public void onGuard(LivingEntity var1, DamageSource var2, float var3, float var4) {
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("While midair, the user forms a protective swirl of fire in front of them capable of blocking most attacks."),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityDescriptionLine$IDescriptionLine[] lv2_4 = new AbilityDescriptionLine$IDescriptionLine[2];
			lv2_4[0] = AbilityDescriptionLine.NEW_LINE;
			Supplier[] lv2_5 = new Supplier[]{ModMorphs.PHOENIX_ASSAULT,ModMorphs.PHOENIX_FLY};
			lv2_4[1] = AbilityTooltipsHelper.getRequiredMorphTooltip(lv2_5);
			AbilityDescriptionLine$IDescriptionLine[] lv2_6 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(80F,200F),ContinuousComponent.getTooltip(80F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(FujiazamiAbility::new)))).addDescriptionLine(lv2_3).addDescriptionLine(lv2_4).addAdvancedDescriptionLine(lv2_6).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("fujiazami","Fujiazami",var0));
	}

	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		LivingEntity var10 = null;
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.REDUCED_FALL),4,1,false,false));
		boolean var3 = ((MorphInfo)(ModMorphs.PHOENIX_FLY.get())).isActive(var1);
		Vec3 var4 = var1.getLookAngle();
		int var5 = (var3) ? (3) : (2);
		double var6 = (var3) ? (1.25D) : (0.8D);
		int var7 = 0;
		LOOP_1:
		do{
			if ((var7) < ((var5) * (2))){
				double var8 = ((double)(var7)) / (2D);
				Vec3 var9 = new Vec3((var1.getX()) + ((var4.x) * (var8)),((var1.getY()) + ((double)(var1.getEyeHeight()))) + ((var4.y) * (var8)),(var1.getZ()) + ((var4.z) * (var8)));
				var10 = var1;
				final LivingEntity var10_f1 = var10;
				Predicate<Entity> var11 = (l0) -> {
			return (l0) != (var10_f1);
		};
				Iterator var12 = var1.level().getEntities((Entity)(var1),new AABB((var9.x) - (var6),(var9.y) - (var6),(var9.z) - (var6),(var9.x) + (var6),(var9.y) + ((var6) * (2D)),(var9.z) + (var6)),var11).iterator();
				do{
					if (var12.hasNext()){
						Entity var13 = ((Entity)(var12.next()));
						if (var13 instanceof LivingEntity){
							Vec3 var14 = var1.getLookAngle().normalize().multiply(3D,1D,3D);
							AbilityHelper.setDeltaMovement(var13,var14.x,0.5D,var14.z);
							continue;
						}else{
							if ((!(var13 instanceof AbstractArrow)) && (!(var13 instanceof ThrowableProjectile))){
								continue;
							}else{
								if (var13 instanceof NuProjectileEntity){
									NuProjectileEntity var14_1 = ((NuProjectileEntity)(var13));
									if ((var14_1.getDamage()) > ((float)((var3) ? (50) : (30)))){
										return;
									}
								}
								var13.discard();
								continue;
							}
						}
					}else{
						break;
					}
				} while (true);
				var7++;
				continue LOOP_1;
			}else{
				break;
			}
		} while (true);
		if ((this.particleInterval.canTick()) && (((this.continuousComponent.getContinueTime()) % (5F)) == (0F))){
			var4.multiply(2.5D,1D,2.5D);
			Vec3 var8_1 = var1.position().add(var4);
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.FUJIZAMI.get())),(Entity)(var1),var8_1.x,(var1.getY()) + ((double)((var1.getEyeHeight()) / (2F))),var8_1.z);
		}
	}}
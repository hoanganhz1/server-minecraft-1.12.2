package xyz.pixelatedw.mineminenomi.abilities.beta;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
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
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class HanamizuShinkenAbility extends Ability {
	// Fields
	private static final int HOLD_TIME = 60;
	private static final int COOLDOWN = 100;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<HanamizuShinkenAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final DamageTakenComponent damageTakenComponent;

	public HanamizuShinkenAbility(AbilityCore<HanamizuShinkenAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnHurtEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::damageTakenEvent))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.damageTakenComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,60F);
	}
	private void reflectProjectile(Entity var1) {
		AbilityHelper.setDeltaMovement(var1,(-(var1.getDeltaMovement().x)) / (2D),var1.getDeltaMovement().y,(-(var1.getDeltaMovement().z)) / (2D));
	}
	private float damageTakenEvent(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		IDamageSourceHandler var5 = ((IDamageSourceHandler)(var3));
		if ((this.continuousComponent.isContinuous()) && (!((!(var5.hasType(SourceType.PHYSICAL))) && (!(var5.hasType(SourceType.FIST)))))){
			Entity var6 = var3.getDirectEntity();
			if (((var6) != (null)) && (var6 instanceof LivingEntity)){
				AbilityHelper.setDeltaMovement(var6,var1.getLookAngle().multiply(4D,4D,4D));
			}
			return 0F;
		}else{
			return var4;
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.startCooldown(var1,100F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Creates a protecting wall of mucus, protecting the user from incoming physical attacks and physical projectiles."),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityDescriptionLine$IDescriptionLine[] lv2_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(100F),ContinuousComponent.getTooltip(60F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(HanamizuShinkenAbility::new)))).addDescriptionLine(lv2_3).addAdvancedDescriptionLine(lv2_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("hanamizu_shinken_shirahadori","Hanamizu Shinken Shirahadori",var0));
	}

	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		LivingEntity var9 = null;
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),5,0,false,false));
		Vec3 var3 = var1.getLookAngle();
		int var4 = 2;
		double var5 = 1.2D;
		int var6 = 0;
		LOOP_1:
		do{
			if ((var6) < ((var4) * (2))){
				double var7 = ((double)(var6)) / (2D);
				Vec3 var8 = new Vec3((var1.getX()) + ((var3.x) * (var7)),((var1.getY()) + ((double)(var1.getEyeHeight()))) + ((var3.y) * (var7)),(var1.getZ()) + ((var3.z) * (var7)));
				var9 = var1;
				final LivingEntity var9_f1 = var9;
				Predicate<Entity> var10 = (l0) -> {
			return (l0) != (var9_f1);
		};
				Iterator var11 = var1.level().getEntities((Entity)(var1),new AABB((var8.x) - (var5),(var8.y) - (var5),(var8.z) - (var5),(var8.x) + (var5),(var8.y) + ((var5) * (2D)),(var8.z) + (var5)),var10).iterator();
				do{
					if (var11.hasNext()){
						Entity var12 = ((Entity)(var11.next()));
						if (var12 instanceof NuProjectileEntity){
							NuProjectileEntity var13 = ((NuProjectileEntity)(var12));
							if (var13.isPhysical()){
								this.reflectProjectile((Entity)(var13));
								continue;
							}else{
								continue;
							}
						}else{
							if ((!(var12 instanceof ThrowableProjectile)) && (!(var12 instanceof AbstractArrow))){
								continue;
							}else{
								this.reflectProjectile(var12);
								continue;
							}
						}
					}else{
						break;
					}
				} while (true);
				var6++;
				continue LOOP_1;
			}else{
				break;
			}
		} while (true);
		if (((this.continuousComponent.getContinueTime()) % (5F)) == (0F)){
			var3.multiply(2.5D,1D,2.5D);
			Vec3 var7_1 = var1.position().add(var3);
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.HANAMIZU_SHINKEN_SHIRADORI.get())),(Entity)(var1),var7_1.x,var7_1.y,var7_1.z);
		}
	}}
package xyz.pixelatedw.mineminenomi.abilities.goro;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.goro.KariParticleEffect$Details;

public class KariAbility extends Ability {
	// Fields
	private static final int CHARGE_TIME = 60;
	private static final int MIN_COOLDOWN = 60;
	private static final int MAX_COOLDOWN = 240;
	private static final int EXPLOSION_SIZE = 4;
	private static final int EXPLOSION_DAMAGE = 10;
	private static final int RANGE = 4;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KariAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final RangeComponent rangeComponent;
	private final ExplosionComponent explosionComponent;
	private static final KariParticleEffect$Details DETAILS;
	private final Interval particleInterval;

	public KariAbility(AbilityCore<KariAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::onChargeTick)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::onChargeEnd))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.explosionComponent = new ExplosionComponent((IAbility)(this));
		this.particleInterval = new Interval(2);
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.rangeComponent,this.explosionComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		if (!(this.chargeComponent.isCharging())){
			this.particleInterval.restartIntervalToZero();
			this.chargeComponent.startCharging(var1,60F);
		}else{
			this.chargeComponent.stopCharging(var1);
		}
	}
	private void onChargeTick(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide()){
			return;
		}else{
			var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),2,1));
			if (this.particleInterval.canTick()){
				DETAILS.setRange(2);
				DETAILS.setSize(2F);
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.KARI.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ(),(ParticleEffect$Details)(DETAILS));
			}
			float var3 = this.rangeComponent.getBonusManager().applyBonus(4F);
			Iterator var4 = TargetHelper.getEntitiesInArea((LevelAccessor)(var1.level()),var1,(double)(var3),(TargetPredicate)(null),Entity.class).iterator();
			do{
				if (var4.hasNext()){
					Entity var5 = ((Entity)(var4.next()));
					if (var5 instanceof LivingEntity){
						AbilityHelper.setSecondsOnFireBy(var5,3,var1);
						continue;
					}else{
						if (var5 instanceof NuProjectileEntity){
							NuProjectileEntity var6 = ((NuProjectileEntity)(var5));
							if (IDamageSourceHandler.getHandler(var6.getDamageSource()).hasType(SourceType.PHYSICAL)){
								LivingEntity var7 = var6.getOwner();
								if (((var7) != (null)) && ((var7) != (var1))){
									AbilityHelper.setSecondsOnFireBy(var5,3,var1);
									continue;
								}else{
									continue;
								}
							}else{
								var5.discard();
								continue;
							}
						}else{
							continue;
						}
					}
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	private void onChargeEnd(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide()){
			return;
		}else{
			AbilityExplosion var3 = this.explosionComponent.createExplosion((Entity)(var1),var1.getX(),var1.getY(),var1.getZ(),4F);
			var3.setStaticDamage(10F);
			var3.setDestroyBlocks(false);
			var3.explode();
			var1.level().playSound((Player)(null),var1.getX(),var1.getY(),var1.getZ(),SoundEvents.LIGHTNING_BOLT_IMPACT,SoundSource.WEATHER,2F,(0.5F) + ((var1.getRandom().nextFloat()) * (0.2F)));
			float var4 = Math.max(60F,(240F) * (this.chargeComponent.getChargePercentage()));
			this.cooldownComponent.startCooldown(var1,var4);
			return;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("The user heats the air around them with lightning until it explodes with a thunder clap."),(Object)(null)),ImmutablePair.of((Object)("Can be used to avoid and neutralize projectiles."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(60F,240F),ChargeComponent.getTooltip(60F),RangeComponent.getTooltip(4F,RangeComponent$RangeType.AOE)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KariAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setSourceElement(SourceElement.FIRE).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("kari","Kari",var0));
		DETAILS = new KariParticleEffect$Details();
	}
}
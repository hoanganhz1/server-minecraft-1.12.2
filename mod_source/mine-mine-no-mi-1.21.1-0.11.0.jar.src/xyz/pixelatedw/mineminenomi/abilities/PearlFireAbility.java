package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$HitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class PearlFireAbility extends Ability {
	// Fields
	private static final int HOLD_TIME = 1200;
	private static final int MIN_COOLDOWN = 100;
	private static final int MAX_COOLDOWN = 1300;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<PearlFireAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final DamageTakenComponent damageTakenComponent;
	private final DealDamageComponent dealDamageComponent;
	private final HitTriggerComponent hitTriggerComponent;

	public PearlFireAbility(AbilityCore<PearlFireAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnHurtEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::damageTakenEvent))));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.hitTriggerComponent = new HitTriggerComponent((IAbility)(this)).addOnHitEvent((HitTriggerComponent$IOnHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent)(this::onHit)))).addTryHitEvent((HitTriggerComponent$ITryHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent)(this::tryHitEvent))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.damageTakenComponent,this.dealDamageComponent,this.hitTriggerComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(this::canUse))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,1200F);
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if (!(var1.level().isClientSide)){
			int var3 = 0;
			do{
				if ((var3) < (2)){
					double var4 = WyHelper.randomDouble();
					double var5 = (WyHelper.randomDouble()) / (3D);
					double var6 = WyHelper.randomDouble();
					SimpleParticleData var7 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MERA.get())));
					var7.setLife(35);
					var7.setSize((1F) + (var1.getRandom().nextFloat()));
					var7.setMotion((var4) / (200D),0.02D,(var6) / (200D));
					var7.setColor(1F,1F,1F,0.4F);
					WyHelper.spawnParticles(var7,((ServerLevel)(var1.level())),(var1.getX()) + (var4),((var1.getY()) + (var5)) + (0.5D),(var1.getZ()) + (var6));
					WyHelper.spawnParticles(var7,((ServerLevel)(var1.level())),(var1.getX()) + (var4),((var1.getY()) + (var5)) + (1.5D),(var1.getZ()) + (var6));
					var3++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.startCooldown(var1,(100F) + (this.continuousComponent.getContinueTime()));
	}
	private HitTriggerComponent$HitResult tryHitEvent(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		if (!(this.continuousComponent.isContinuous())){
			return HitTriggerComponent$HitResult.PASS;
		}else{
			return HitTriggerComponent$HitResult.HIT;
		}
	}
	public boolean onHit(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		AbilityHelper.setSecondsOnFireBy((Entity)(var2),2,var1);
		return true;
	}
	private float damageTakenEvent(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		if ((this.continuousComponent.isContinuous()) && (((var3.getDirectEntity()) != (null)) && (var3.getDirectEntity() instanceof LivingEntity))){
			this.dealDamageComponent.hurtTarget(var1,((LivingEntity)(var3.getDirectEntity())),(var4) / (3F));
			return (var4) - ((var4) / (3F));
		}else{
			return var4;
		}
	}
	private Result canUse(LivingEntity var1, IAbility var2) {
		if (hasArmorSetEquipped(var1)){
			return Result.success();
		}else{
			return Result.fail(null);
		}
	}
	private static boolean canUnlock(LivingEntity var0) {
		return hasArmorSetEquipped(var0);
	}
	private static boolean hasArmorSetEquipped(LivingEntity var0) {
		ItemStack var1 = var0.getItemBySlot(EquipmentSlot.HEAD);
		if ((!(var1.isEmpty())) && ((var1.getItem()) == (ModArmors.PEARL_HAT.get()))){
			ItemStack var2 = var0.getItemBySlot(EquipmentSlot.CHEST);
			if ((!(var2.isEmpty())) && ((var2.getItem()) == (ModArmors.PEARL_ARMOR.get()))){
				ItemStack var3 = var0.getItemBySlot(EquipmentSlot.LEGS);
				if ((!(var3.isEmpty())) && ((var3.getItem()) == (ModArmors.PEARL_LEGS.get()))){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Sets the pearl armor on fire causing any enemy that hits you to take damage and any enemy you hit to be set on fire."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(100F,1300F),ContinuousComponent.getTooltip(1200F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.EQUIPMENT,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(PearlFireAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(PearlFireAbility::canUnlock)))).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("pearl_fire","Pearl Fire",var0));
	}
}
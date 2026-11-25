package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.UUID;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.EffectsHelper;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.mixins.IMobEffectInstanceMixin;

public class MobEffectComponent extends AbilityComponent<IAbility> {
	// Fields
	private static final UUID EFFECT_TIME_BONUS_MANAGER_UUID = UUID.fromString("2cd2e9a0-6085-49e0-a37a-f10bb88cf489");
	private static final UUID EFFECT_AMP_BONUS_MANAGER_UUID = UUID.fromString("288e05b5-71b7-420b-8ecc-f369cd567b3e");
	private MobEffectComponent$ICheckMobEffectEvent checkMobEffectEvent;
	private final BonusManager timeBonusManager;
	private final BonusManager ampBonusManager;

	public MobEffectComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.MOB_EFFECT.get())),var1);
		MobEffectComponent$ICheckMobEffectEvent var2 = (l0, l1) -> {
			return true;
		};
		this.checkMobEffectEvent = var2;
		this.timeBonusManager = new BonusManager(EFFECT_TIME_BONUS_MANAGER_UUID);
		this.ampBonusManager = new BonusManager(EFFECT_AMP_BONUS_MANAGER_UUID);
		this.addBonusManager(this.timeBonusManager);
		this.addBonusManager(this.ampBonusManager);
	}
	public void applyEffect(LivingEntity var1, Holder<MobEffect> var2, int var3, int var4) {
		var3 = Math.round(this.timeBonusManager.applyBonus((float)(var3)));
		var4 = Math.round(this.ampBonusManager.applyBonus((float)(var4)));
		var1.addEffect(new MobEffectInstance(var2,var3,var4,false,false,true));
	}
	public void removeEffect(LivingEntity var1, Holder<MobEffect> var2) {
		EffectsHelper.removeAbilityEffect(var1,var2,this.getAbility());
	}
	public void addTimeToEffect(LivingEntity var1, Holder<MobEffect> var2, int var3) {
		MobEffectInstance var4 = var1.getEffect(var2);
		int var5 = Math.max((var4.getDuration()) + (var3),0);
		var5 = Math.round(this.timeBonusManager.applyBonus((float)(var5)));
		((IMobEffectInstanceMixin)(var4)).setDuration(var5);
	}
	public void addAmplifierToEffect(LivingEntity var1, Holder<MobEffect> var2, int var3) {
		MobEffectInstance var4 = var1.getEffect(var2);
		int var5 = Math.max((var4.getAmplifier()) + (var3),0);
		var5 = Math.round(this.ampBonusManager.applyBonus((float)(var5)));
		((IMobEffectInstanceMixin)(var4)).setAmplifier(var5);
	}
	public void setEffectCheck(MobEffectComponent$ICheckMobEffectEvent var1) {
		this.checkMobEffectEvent = var1;
	}
	public boolean checkEffect(LivingEntity var1, MobEffectInstance var2) {
		return this.checkMobEffectEvent.checkPotion(var1,var2);
	}
}
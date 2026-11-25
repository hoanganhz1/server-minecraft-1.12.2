package xyz.pixelatedw.mineminenomi.effects;

import java.util.Optional;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.EffectCure;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IIgnoreMilkEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ILingeringEffect;

public class BaseEffect extends MobEffect implements IIgnoreMilkEffect, IBindHandsEffect, IBindBodyEffect, ILingeringEffect {
	public BaseEffect(MobEffectCategory var1, int var2) {
		super(var1,var2);
	}
	public void addAttributeModifiers(AttributeMap var1, int var2) {
		super.addAttributeModifiers(var1,var2);
	}
	public void removeAttributeModifiers(AttributeMap var1) {
		super.removeAttributeModifiers(var1);
	}
	public void onStartEffect(LivingEntity var1, MobEffectInstance var2) {
		this.startEffect(var1,var2);
		if (this.shouldUpdateClient()){
			WyHelper.sendApplyEffectToAllNearby(var1,var1.position(),100,var2);
		}
	}
	public void onStopEffect(LivingEntity var1) {
		this.stopEffect(var1);
		if (this.shouldUpdateClient()){
			ResourceLocation var2 = BuiltInRegistries.MOB_EFFECT.getKey(this);
			Holder var3 = ((Holder)(BuiltInRegistries.MOB_EFFECT.getHolder(ResourceKey.create(Registries.MOB_EFFECT,var2)).orElseThrow()));
			WyHelper.sendRemoveEffectToAllNearby(var1,var1.position(),100,var3);
		}
	}
	public void startEffect(LivingEntity var1, MobEffectInstance var2) {
	}
	public void stopEffect(LivingEntity var1) {
	}
	public boolean shouldUpdateClient() {
		return false;
	}
	public boolean isBlockingRotation() {
		return false;
	}
	public boolean isBlockingSwings() {
		return false;
	}
	public boolean isRemoveable() {
		return true;
	}
	public boolean shouldRenderParticles(MobEffectInstance var1) {
		return true;
	}
	public boolean shouldRenderHUD(MobEffectInstance var1) {
		return true;
	}
	public void fillEffectCures(Set<EffectCure> var1, MobEffectInstance var2) {
		if (!(this.isRemoveable())){
			var1.clear();
		}
	}
}
package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.init.ModEnchantments;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class EnchantmentsHandler {
	public static void tryFlashDialEffect(LivingEntity var0, LivingEntity var1) {
		Optional var2 = ((Registry)(var0.registryAccess().registry(Registries.ENCHANTMENT).get())).getHolder(ModEnchantments.DIAL_FLASH);
		if (var2.isEmpty()){
			return;
		}else{
			int var3 = var0.getMainHandItem().getEnchantmentLevel(((Holder)(var2.get())));
			if ((var3) > (0)){
				var1.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,(200) * (var3),var3));
			}
			return;
		}
	}
	public static void tryImpactDialEffect(LivingEntity var0, LivingEntity var1) {
		ItemStack var2 = var0.getMainHandItem();
		Optional var3 = ((Registry)(var0.registryAccess().registry(Registries.ENCHANTMENT).get())).getHolder(ModEnchantments.DIAL_IMPACT);
		if (var3.isEmpty()){
			return;
		}else{
			int var4 = var2.getEnchantmentLevel(((Holder)(var3.get())));
			if ((var4) > (0)){
				var2.hurtAndBreak((WyHelper.randomWithRange(1,3)) * (var4),var1,EquipmentSlot.MAINHAND);
				CommonExplosionParticleEffect$Details var5 = new CommonExplosionParticleEffect$Details((double)(var4));
				AbilityExplosion var6 = new AbilityExplosion((Entity)(var0),(IAbility)(null),var1.getX(),(var1.getY()) + (1D),var1.getZ(),(float)(var4));
				var6.setDamageOwner(false);
				var6.setDestroyBlocks(false);
				var6.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(var5));
				var6.explode();
			}
			return;
		}
	}
}
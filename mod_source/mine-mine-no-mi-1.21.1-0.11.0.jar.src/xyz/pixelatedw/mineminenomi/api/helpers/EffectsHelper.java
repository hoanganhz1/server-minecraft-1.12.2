package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.effects.AbilityMobEffectInstance;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.mixins.ILivingEntityMixin;
import xyz.pixelatedw.mineminenomi.mixins.IMobEffectInstanceMixin;

public class EffectsHelper {
	public static boolean isHandcuffed(LivingEntity var0) {
		return (var0.hasEffect((Holder)(ModEffects.HANDCUFFED))) || ((var0.hasEffect((Holder)(ModEffects.HANDCUFFED_KAIROSEKI))) || (var0.hasEffect((Holder)(ModEffects.HANDCUFFED_EXPLOSIVE))));
	}
	public static void removeAbilityEffect(LivingEntity var0, Holder<MobEffect> var1, IAbility var2) {
		Iterator var3 = var0.getActiveEffects().iterator();
		do{
			if (var3.hasNext()){
				MobEffectInstance var4 = ((MobEffectInstance)(var3.next()));
				if (((var4) != (null)) && (var4.getEffect().equals(var1))){
					if (var4 instanceof AbilityMobEffectInstance){
						AbilityMobEffectInstance var5 = ((AbilityMobEffectInstance)(var4));
						if (var5.getParent().equals(var2)){
							var3.remove();
							((ILivingEntityMixin)(var0)).invokeOnEffectRemoved(var5);
							continue;
						}else{
							continue;
						}
					}else{
						var3.remove();
						((ILivingEntityMixin)(var0)).invokeOnEffectRemoved(var4);
						continue;
					}
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	public static boolean hasEffectOfType(LivingEntity var0, Class<? extends MobEffect> var1) {
		return getEffectOfType(var0,var1).isPresent();
	}
	public static Optional<MobEffectInstance> getEffectOfType(LivingEntity var0, Class<? extends MobEffect> var1) {
		Iterator var2 = var0.getActiveEffects().iterator();
		do{
			if (var2.hasNext()){
				MobEffectInstance var3 = ((MobEffectInstance)(var2.next()));
				if (((var3) != (null)) && ((var3.getEffect().getClass()) == (var1))){
					return (Optional<MobEffectInstance>) (Optional) (Optional.of(var3));
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return (Optional<MobEffectInstance>) (Optional) (Optional.empty());
	}
	public static void updateEffectDuration(LivingEntity var0, MobEffectInstance var1, int var2) {
		((IMobEffectInstanceMixin)(var1)).setDuration(var2);
		if ((var0.getServer()) != (null)){
			WyHelper.sendApplyEffectToAllNearby(var0,var0.position(),128,var1);
		}
	}
	public static void updateEffect(LivingEntity var0, MobEffectInstance var1, int var2, int var3) {
		((IMobEffectInstanceMixin)(var1)).setDuration(var2);
		((IMobEffectInstanceMixin)(var1)).setAmplifier(var3);
		if ((var0.getServer()) != (null)){
			WyHelper.sendApplyEffectToAllNearby(var0,var0.position(),128,var1);
		}
	}
}
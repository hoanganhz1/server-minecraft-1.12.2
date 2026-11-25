package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.LogiaInvulnerabilityAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.GuardAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class CombatHelper {
	@Nullable()
	public static @Nullable() LivingEntity canGrab(LivingEntity var0, boolean var1) {
		return canGrab(var0,(float)(Math.sqrt(AttributeHelper.getSquaredAttackRangeDistance(var0,3D))),0.4F,var1);
	}
	@Nullable()
	public static @Nullable() LivingEntity canGrab(LivingEntity var0, float var1, float var2, boolean var3) {
		Optional var4 = TargetHelper.getEntitiesInLine(var0,var1,var2,(TargetPredicate)(null),LivingEntity.class).stream().findFirst();
		if (var4.isPresent()){
			LivingEntity var5 = ((LivingEntity)(var4.get()));
			if (((var5) != (null)) && (var5.isAlive())){
				if ((!(isShieldBlocking(var5))) && ((!(isGuardBlocking(var5))) && (!(isDodging(var5))))){
					DamageSource var6 = var0.damageSources().mobAttack(var0);
					if (((LivingIncomingDamageEvent)(NeoForge.EVENT_BUS.post((Event)(new LivingIncomingDamageEvent(var5,new DamageContainer(var6,1F)))))).isCanceled()){
						return null;
					}else{
						return var5;
					}
				}else{
					return null;
				}
			}else{
				return null;
			}
		}else{
			if ((var3) && (var0 instanceof ServerPlayer)){
				((ServerPlayer)(var0)).sendSystemMessage(ModI18nAbilities.MESSAGE_NO_TARGET);
			}
			return null;
		}
	}
	@Deprecated()
	public static boolean isTargetBlocking(LivingEntity var0, LivingEntity var1) {
		if (isLogiaBlocking(var1)){
			return true;
		}else{
			if (isShieldBlocking(var1)){
				return true;
			}else{
				if (isGuardBlocking(var1)){
					return true;
				}else{
					if (isDodging(var1)){
						return true;
					}else{
						return false;
					}
				}
			}
		}
	}
	public static boolean isHakiBlocking(LivingEntity var0, LivingEntity var1) {
		float var2 = ((Float)(HakiData.get(var0).map((Function)(((Function<HakiData, Float>)(HakiData::getTotalHakiExp)))).orElse(Float.valueOf(0F)))).floatValue();
		float var3 = ((Float)(HakiData.get(var1).map((Function)(((Function<HakiData, Float>)(HakiData::getTotalHakiExp)))).orElse(Float.valueOf(0F)))).floatValue();
		if ((var3) < (5F)){
			return false;
		}else{
			float var4 = (var2) - (var3);
			if ((var4) < (-5F)){
				return true;
			}else{
				if ((var4) > (5F)){
					return false;
				}else{
					float var5 = (0.95F) + (((Mth.clamp(var4,0F,5F)) / (400F)) * (-1F));
					if ((var1.getRandom().nextFloat()) < (var5)){
						var0.level().playSound((Player)(null),var0.blockPosition(),((SoundEvent)(ModSounds.HAKI_GUARD.get())),SoundSource.PLAYERS,2F,1F);
						WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.HAKI_GUARD.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
						return true;
					}else{
						return false;
					}
				}
			}
		}
	}
	public static boolean isLogiaBlocking(LivingEntity var0) {
		MobEffectInstance var1 = var0.getEffect((Holder)(ModEffects.UNCONSCIOUS));
		if (((var1) != (null)) && ((var1.getAmplifier()) > (0))){
			return false;
		}else{
			if ((!(var0.hasEffect((Holder)(ModEffects.KAIROSEKI_WEAKNESS)))) && (!(var0.hasEffect((Holder)(ModEffects.WATER_WEAKNESS))))){
				AbilityData var2 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
				if ((var2) == (null)){
					return false;
				}else{
					Class var3 = LogiaInvulnerabilityAbility.class;
					Objects.requireNonNull(var3);
					Class var4 = LogiaInvulnerabilityAbility.class;
					Objects.requireNonNull(var4);
					Predicate<LogiaInvulnerabilityAbility> var5 = (l0) -> {
			return !(l0.isPaused());
		};
					if (var2.getPassiveAbilities(AbilityCategory.DEVIL_FRUITS.isAbilityPartofCategory()).stream().filter((Predicate)(((Predicate<IAbility>)(var3::isInstance)))).limit(1L).map((Function)(((Function<IAbility, LogiaInvulnerabilityAbility>)(var4::cast)))).filter(var5).findFirst().isPresent()){
						return true;
					}else{
						return false;
					}
				}
			}else{
				return false;
			}
		}
	}
	public static boolean isShieldBlocking(LivingEntity var0) {
		MobEffectInstance var1 = var0.getEffect((Holder)(ModEffects.UNCONSCIOUS));
		if (((var1) != (null)) && ((var1.getAmplifier()) > (0))){
			return false;
		}else{
			if (var0.isBlocking()){
				var0.playSound(SoundEvents.SHIELD_BLOCK,1F,(0.8F) + ((var0.level().random.nextFloat()) * (0.4F)));
				return true;
			}else{
				return false;
			}
		}
	}
	public static boolean isGuardBlocking(LivingEntity var0) {
		MobEffectInstance var1 = var0.getEffect((Holder)(ModEffects.UNCONSCIOUS));
		if (((var1) != (null)) && ((var1.getAmplifier()) > (0))){
			return false;
		}else{
			Function<AbilityData, Boolean> var2 = (l0) -> {
			Class lv2_1 = GuardAbility.class;
			Objects.requireNonNull(lv2_1);
			return Boolean.valueOf(l0.getEquippedAbilities((Predicate)(((Predicate<IAbility>)(lv2_1::isInstance)))).stream().anyMatch((Predicate)(((Predicate<GuardAbility>)(Ability::isContinuous)))));
		};
			if (((Boolean)(((java.util.Optional)(AbilityData.get(var0))).map((Function)(var2)).orElse(Boolean.valueOf(false)))).booleanValue()){
				var0.level().playSound((Player)(null),var0.blockPosition(),((SoundEvent)(ModSounds.GUARD.get())),SoundSource.PLAYERS,2F,1F);
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.GUARD.get())),(Entity)(var0),var0.getX(),var0.getY(),var0.getZ());
				return true;
			}else{
				return false;
			}
		}
	}
	public static boolean isDodging(LivingEntity var0) {
		MobEffectInstance var1 = var0.getEffect((Holder)(ModEffects.UNCONSCIOUS));
		if (((var1) != (null)) && ((var1.getAmplifier()) > (0))){
			return false;
		}else{
			AbilityData var2 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
			if ((var2) == (null)){
				return false;
			}else{
				Predicate<IAbility> var3 = (l0) -> {
			return l0.hasComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get())));
		};
				Function var4 = (Function) ((Function<IAbility, Optional>) ((l0) -> {
			return l0.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get())));
		}));
				return var2.getEquippedAbilitiesInPool(ModAbilityPools.DODGE_ABILITY).filter(var3).map(var4).filter((Predicate)(((Predicate<Optional>)(Optional::isPresent)))).anyMatch((Predicate)(((java.util.function.Predicate)((Predicate) ((Predicate<Optional>) ((l0) -> {
			return ((ContinuousComponent)(l0.get())).isContinuous();
		}))))));
			}
		}
	}
}
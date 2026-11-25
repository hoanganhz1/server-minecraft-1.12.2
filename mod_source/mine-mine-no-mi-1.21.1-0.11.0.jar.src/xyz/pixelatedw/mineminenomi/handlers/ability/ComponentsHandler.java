package xyz.pixelatedw.mineminenomi.handlers.ability;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.OutOfBodyAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BowTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageSourceComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$DamageState;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$HitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.CombatData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;

public class ComponentsHandler {
	public static boolean triggerBowShootComponents(LivingEntity var0) {
		AbilityData var1 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
		if ((var1) != (null)){
			Iterator var2 = var1.getEquippedAbilities().iterator();
			do{
				if (var2.hasNext()){
					Optional var3 = ((IAbility)(var2.next())).getComponent(((AbilityComponentKey)(ModAbilityComponents.BOW_TRIGGER.get())));
					if ((var3.isPresent()) && (((BowTriggerComponent)(var3.get())).tryShoot(var0))){
						return true;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
		return false;
	}
	public static boolean tryTriggerHitComponents(LivingEntity var0, LivingEntity var1, DamageSource var2) {
		HashSet var3 = new HashSet();
		AbilityData var4 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
		if ((var4) == (null)){
			return false;
		}else{
			Predicate<IAbility> var5 = (l0) -> {
			return l0.hasComponent(((AbilityComponentKey)(ModAbilityComponents.HIT_TRIGGER.get())));
		};
			Iterator var6 = var4.getEquippedAndPassiveAbilities(var5).iterator();
			do{
				if (var6.hasNext()){
					HitTriggerComponent var7 = ((HitTriggerComponent)(((IAbility)(var6.next())).getComponent(((AbilityComponentKey)(ModAbilityComponents.HIT_TRIGGER.get()))).get()));
					if ((var7.tryHit(var0,var1,var2)) == (HitTriggerComponent$HitResult.HIT)){
						var3.add(var7);
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			Set var7_1 = var3;
			final Set var7_1_f1 = var7_1;
			Consumer<CombatData> var8 = (l0) -> {
			l0.setHitTriggers((Set)(new HashSet((Collection)(var7_1_f1))));
		};
			((java.util.Optional)(CombatData.get(var0))).ifPresent((Consumer)(var8));
			var3.removeIf((Predicate)(((Predicate<HitTriggerComponent>)((l0) -> {
			return !(l0.getAbility().getCore().hasType(SourceType.FRIENDLY));
		}))));
			if ((((java.util.Set)(var3)).size()) > (0)){
				triggerHitComponents(var0,var1,var2,(Set)(var3));
				IDamageSourceHandler.getHandler(var2).reset();
				return true;
			}else{
				return false;
			}
		}
	}
	public static void triggerDamageSourceComponents(LivingEntity var0, LivingEntity var1, DamageSource var2) {
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			Predicate<IAbility> var4 = (l0) -> {
			return l0.hasComponent(((AbilityComponentKey)(ModAbilityComponents.DAMAGE_SOURCE.get())));
		};
			Iterator var5 = var3.getEquippedAndPassiveAbilities(var4).iterator();
			do{
				if (var5.hasNext()){
					((DamageSourceComponent)(((IAbility)(var5.next())).getComponent(((AbilityComponentKey)(ModAbilityComponents.DAMAGE_SOURCE.get()))).get())).updateDamageSource(var0,var1,var2);
					continue;
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	public static void triggerHitComponents(LivingEntity var0, LivingEntity var1, DamageSource var2, @Nullable() Set<HitTriggerComponent> var3) {
		if ((var3) == (null)){
			var3 = ((Set)(CombatData.get(var0).map((Function)(((Function<CombatData, Set>)(CombatData::getHitTriggers)))).orElse(new HashSet())));
			var3.removeIf((Predicate)(((Predicate<HitTriggerComponent>)((l0) -> {
			return l0.getAbility().getCore().hasType(SourceType.FRIENDLY);
		}))));
		}
		if (!(var3.isEmpty())){
			Iterator<HitTriggerComponent> var4 = var3.iterator();
			do{
				if (var4.hasNext()){
					((HitTriggerComponent)(var4.next())).onHit(var0,var1,var2);
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
	public static float handleDamageTakenComponents(LivingEntity var0, DamageSource var1, float var2, DamageTakenComponent$DamageState var3) {
		AbilityData var4 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
		if ((var4) == (null)){
			return var2;
		}else{
			Predicate<IAbility> var5 = (l0) -> {
			return l0.hasComponent(((AbilityComponentKey)(ModAbilityComponents.DAMAGE_TAKEN.get())));
		};
			Iterator var6 = var4.getEquippedAndPassiveAbilities(var5).iterator();
			do{
				if (var6.hasNext()){
					IAbility var7 = ((IAbility)(var6.next()));
					if ((((var7 instanceof OutOfBodyAbility) && (((java.lang.Object)(var1)).equals((Object)(ModDamageSources.getInstance((LevelAccessor)(var0.level())).outOfBody())))) ? (1) : (0)) != (0)){
						return var2;
					}else{
						var2 = ((DamageTakenComponent)(var7.getComponent(((AbilityComponentKey)(ModAbilityComponents.DAMAGE_TAKEN.get()))).get())).checkDamageTaken(var0,var1,var2,var3);
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return var2;
		}
	}
	public static void triggerSwingComponents(Player var0) {
		Player var4 = null;
		AbilityData var1 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if ((var1) != (null)){
			Iterator var2 = var1.getEquippedAndPassiveAbilities().iterator();
			do{
				if (var2.hasNext()){
					IAbility var3 = ((IAbility)(var2.next()));
					var4 = var0;
					final Player var4_f1 = var4;
					Consumer<SwingTriggerComponent> var5 = (l0) -> {
			l0.swing((LivingEntity)(var4_f1));
		};
					((java.util.Optional)(var3.getComponent(((AbilityComponentKey)(ModAbilityComponents.SWING_TRIGGER.get()))))).ifPresent((Consumer)(var5));
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
}
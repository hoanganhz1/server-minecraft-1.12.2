package xyz.pixelatedw.mineminenomi.handlers.ability;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.abilities.horo.MiniHollowAbility;
import xyz.pixelatedw.mineminenomi.abilities.horo.NegativeHollowAbility;
import xyz.pixelatedw.mineminenomi.abilities.horo.TokuHollowAbility;
import xyz.pixelatedw.mineminenomi.abilities.horo.YutaiRidatsuAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.OutOfBodyAbility;
import xyz.pixelatedw.mineminenomi.api.extensions.IEntityExtension;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;

public class OutOfBodyHandler {
	public static void blockPortalUsage(Player var0) {
		if ((isOutOfBody((LivingEntity)(var0),true)) && (var0 instanceof IEntityExtension)){
			IEntityExtension var1 = ((IEntityExtension)(var0));
			var0.portalProcess.setAsInsidePortalThisTick(false);
		}
	}
	public static boolean canUseAbility(LivingEntity var0, IAbility var1) {
		Optional var2 = getOOBAbility(var0);
		if (var2.isPresent()){
			if (var1.equals(var2.get())){
				return true;
			}else{
				if ((var2.get() instanceof YutaiRidatsuAbility) && (!((!(var1 instanceof MiniHollowAbility)) && ((!(var1 instanceof NegativeHollowAbility)) && (!(var1 instanceof TokuHollowAbility)))))){
					return true;
				}else{
					return false;
				}
			}
		}else{
			return false;
		}
	}
	public static boolean isOutOfBody(LivingEntity var0) {
		return isOutOfBody(var0,false);
	}
	public static boolean isOutOfBody(LivingEntity var0, boolean var1) {
		if ((var0 instanceof Player) && (!((!(((Player)(var0)).isCreative())) && (!(var0.isSpectator()))))){
			return false;
		}else{
			Optional var2 = getOOBAbility(var0);
			if (((var2) != (null)) && (var2.isPresent())){
				if ((!(var1)) && (((OutOfBodyAbility)(var2.get())).isPhysical())){
					return false;
				}else{
					return true;
				}
			}else{
				return false;
			}
		}
	}
	public static Optional<OutOfBodyAbility> getOOBAbility(LivingEntity var0) {
		AbilityData var1 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
		if ((var1) == (null)){
			return (Optional<OutOfBodyAbility>) (Optional) (Optional.empty());
		}else{
			Predicate<IAbility> var2 = (l0) -> {
			int lv1_1 = 0;
			if (l0 instanceof OutOfBodyAbility){
				OutOfBodyAbility lv1_2 = ((OutOfBodyAbility)(l0));
				if (lv1_2.isContinuous()){
					lv1_1 = 1;
				}else{
					lv1_1 = 0;
				}
			}else{
				lv1_1 = 0;
			}
			return (((lv1_1) & 1) != 0);
		};
			Function<IAbility, OutOfBodyAbility> var3 = (l0) -> {
			return ((OutOfBodyAbility)(l0));
		};
			return (Optional<OutOfBodyAbility>) (Optional) (((java.util.stream.Stream)(((xyz.pixelatedw.mineminenomi.data.entity.AbilityData)(var1)).getEquippedAbilities((Predicate)(var2)).stream())).map((Function)(var3)).findFirst());
		}
	}
}
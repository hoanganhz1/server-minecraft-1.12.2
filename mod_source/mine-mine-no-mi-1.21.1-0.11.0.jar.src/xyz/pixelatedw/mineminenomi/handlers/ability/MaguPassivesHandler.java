package xyz.pixelatedw.mineminenomi.handlers.ability;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import xyz.pixelatedw.mineminenomi.abilities.magu.LavaFlowAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;

public class MaguPassivesHandler {
	public static void lavaMovementBoost(Player var0) {
		AbilityData var1 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if ((var1) == (null)){
			return;
		}else{
			if (!(var1.hasUnlockedAbility(((AbilityCore)(LavaFlowAbility.INSTANCE.get()))))){
				return;
			}else{
				if ((var0.isInLava()) && (!(var0.getAbilities().flying))){
					float var2 = ((var0.zza) == (0F)) ? (((var0.xxa) != (0F)) ? (2F) : (0.5F)) : (2F);
					float var3 = (var0.isCrouching()) ? ((!(((var0.getY()) - (var0.yo)) > (0D))) ? (2F) : (0F)) : (0F);
					Vec3 var4 = var0.getDeltaMovement().multiply((double)(var2),(double)(var3),(double)(var2));
					if (((Boolean)(EntityStatsData.get((LivingEntity)(var0)).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isJumping)))).orElse(Boolean.valueOf(false)))).booleanValue()){
						AbilityHelper.setDeltaMovement((Entity)(var0),var4.add(0D,0.4D,0D));
					}else{
						AbilityHelper.setDeltaMovement((Entity)(var0),var4);
					}
				}
				return;
			}
		}
	}
	public static boolean canSeeThroughFire(Player var0, RenderBlockScreenEffectEvent.OverlayType var1) {
		if (!(var1.equals(RenderBlockScreenEffectEvent.OverlayType.FIRE))){
			return false;
		}else{
			AbilityData var2 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var0)))).orElse((Object)(null))));
			if ((var2) == (null)){
				return false;
			}else{
				if (!(var2.hasUnlockedAbility(((AbilityCore)(LavaFlowAbility.INSTANCE.get()))))){
					return false;
				}else{
					return true;
				}
			}
		}
	}
	public static boolean canSeeInsideLava(Player var0) {
		if (!(var0.isEyeInFluidType(((FluidType)(NeoForgeMod.LAVA_TYPE.value()))))){
			return false;
		}else{
			AbilityData var1 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var0)))).orElse((Object)(null))));
			if ((var1) == (null)){
				return false;
			}else{
				if (!(var1.hasUnlockedAbility(((AbilityCore)(LavaFlowAbility.INSTANCE.get()))))){
					return false;
				}else{
					return true;
				}
			}
		}
	}
}
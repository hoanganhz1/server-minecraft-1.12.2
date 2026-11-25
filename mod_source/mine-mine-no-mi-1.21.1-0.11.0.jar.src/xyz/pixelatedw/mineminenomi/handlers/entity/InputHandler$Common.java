package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.extensions.ILivingEntityExtension;
import xyz.pixelatedw.mineminenomi.api.extensions.IPlayerAbilitiesExtension;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;

public class InputHandler$Common {
	public static void handleFreeFlyingLogic(Player var0, float var1, float var2, float var3) {
		EntityStatsData var4 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if ((var4) == (null)){
			return;
		}else{
			if (!(((IPlayerAbilitiesExtension)(var0.getAbilities())).hasCustomFlight())){
				var4.setFlyingSpeed(Vec3.ZERO);
				return;
			}else{
				boolean var5 = ((ILivingEntityExtension)(var0)).isJumping();
				float var6 = 0F;
				if (var5){
					var6 = var2;
				}else{
					if (var0.isCrouching()){
						var6 = -(var2);
					}
				}
				if (((var0.getDeltaMovement().length()) == (0D)) && (((double)(var6)) == (0D))){
					var4.setFlyingSpeed(Vec3.ZERO);
					return;
				}else{
					var0.fallDistance = 0F;
					Vec3 var7 = var4.getFlyingSpeed();
					Vec2 var8 = new Vec2(var4.getLeftImpulse(),var4.getForwardImpulse());
					float var9 = (var8.x) * (var1);
					float var10 = (var8.y) * (var1);
					float var11 = Mth.sin((var0.getYRot()) * (0.017453292F));
					float var12 = Mth.cos((var0.getYRot()) * (0.017453292F));
					Vec3 var13 = var7.add((double)(((var9) * (var12)) - ((var10) * (var11))),(double)(var6),(double)(((var10) * (var12)) + ((var9) * (var11)))).scale((double)(var3));
					var4.setFlyingSpeed(var13);
					AbilityHelper.setDeltaMovement((Entity)(var0),var13);
					return;
				}
			}
		}
	}
	public static boolean canBlockHandSwing(LivingEntity var0, InteractionHand var1) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}
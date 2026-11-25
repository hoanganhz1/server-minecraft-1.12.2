package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.Optional;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.abilities.haki.HaoshokuHakiInfusionAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.ProjectileData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncProjectileExtrasPacket;

public class ProjectilesHandler {
	public static void setProjectileHakiData(Projectile var0, Entity var1) {
		if (((var1) != (null)) && (var1 instanceof LivingEntity)){
			LivingEntity var2 = ((LivingEntity)(var1));
			ProjectileData var3 = ((ProjectileData)(((java.util.Optional)(ProjectileData.get(var0))).orElse((Object)(null))));
			AbilityData var4 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var2))).orElse((Object)(null))));
			if (((var3) != (null)) && ((var4) != (null))){
				HaoshokuHakiInfusionAbility var5 = ((HaoshokuHakiInfusionAbility)(var4.getEquippedAbility(((AbilityCore)(HaoshokuHakiInfusionAbility.INSTANCE.get())))));
				boolean var6 = HakiHelper.hasImbuingActive(var2,false,false);
				boolean var7 = HakiHelper.hasAdvancedBusoActive(var2);
				int var8 = (((var5) != (null)) && (var5.isContinuous())) ? (1) : (0);
				var3.setProjectileBusoshokuImbued(var6);
				var3.setProjectileBusoshokuShrouded(var7);
				var3.setProjectileHaoshokuInfused((var8) != (0));
				if ((!(var0.level().isClientSide)) && (var1 instanceof Player)){
					Player var9 = ((Player)(var1));
					ModNetwork.sendToAllTrackingAndSelf(new SSyncProjectileExtrasPacket(var0),(LivingEntity)(var9));
				}
				return;
			}else{
				return;
			}
		}else{
			return;
		}
	}
}
package xyz.pixelatedw.mineminenomi.handlers.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.entities.vehicles.CannonEntity;
import xyz.pixelatedw.mineminenomi.entities.vehicles.StrikerEntity;
import xyz.pixelatedw.mineminenomi.entities.vehicles.UnicycleEntity;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SToggleAnimationPacket;

public class AnimationsHandler {
	public static void toggleMountingAnimations(LivingEntity var0, Entity var1, boolean var2) {
		if (var2){
			if (var1 instanceof StrikerEntity){
				ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.playAnimation(var0,ModAnimations.RIDE_STRIKER,-1,true),var0);
			}else{
				if (var1 instanceof CannonEntity){
					ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.playAnimation(var0,ModAnimations.CANNON_HANDLING,-1,true),var0);
				}else{
					if (var1 instanceof UnicycleEntity){
						ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.playAnimation(var0,ModAnimations.RIDE_UNICYCLE,-1,true),var0);
					}else{
						return;
					}
				}
			}
		}else{
			if (var1 instanceof StrikerEntity){
				ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.stopAnimation(var0,ModAnimations.RIDE_STRIKER),var0);
			}else{
				if (var1 instanceof CannonEntity){
					ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.stopAnimation(var0,ModAnimations.CANNON_HANDLING),var0);
				}else{
					if (var1 instanceof UnicycleEntity){
						ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.stopAnimation(var0,ModAnimations.RIDE_UNICYCLE),var0);
					}else{
						return;
					}
				}
			}
		}
	}
}
package xyz.pixelatedw.mineminenomi.handlers;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.common.NeoForge;
import xyz.pixelatedw.mineminenomi.api.events.entity.InteractionTriggeredEvent;
import xyz.pixelatedw.mineminenomi.api.events.entity.SetCameraOffsetEvent;
import xyz.pixelatedw.mineminenomi.api.events.entity.SetCameraOffsetEvent$Result;
import xyz.pixelatedw.mineminenomi.api.events.entity.SetCameraZoomEvent;
import xyz.pixelatedw.mineminenomi.api.events.entity.SetCameraZoomEvent$Result;
import xyz.pixelatedw.mineminenomi.handlers.ability.MorphsHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.EffectsHandler$Client;
import xyz.pixelatedw.mineminenomi.handlers.entity.InputHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.VehicleHandler;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.CInteractionTriggeredPacket;

@EventBusSubscriber(modid="mineminenomi", value={Dist.CLIENT})
public class InputEventHandler {
	@SubscribeEvent()
	public static void onMouseInput(InputEvent.MouseButton.Post var0) {
		LocalPlayer var1 = Minecraft.getInstance().player;
		if (((var1) != (null)) && ((var0.getAction()) != (0))){
			InputHandler.checkKeybindings((AbstractClientPlayer)(var1),InputConstants.Type.MOUSE.getOrCreate(var0.getButton()),var0.getAction(),var0.getButton(),0);
			return;
		}else{
			return;
		}
	}
	@SubscribeEvent()
	public static void onKeyInput(InputEvent.Key var0) {
		LocalPlayer var1 = Minecraft.getInstance().player;
		if (((var1) != (null)) && ((var0.getAction()) != (0))){
			InputHandler.checkKeybindings((AbstractClientPlayer)(var1),InputConstants.getKey(var0.getKey(),var0.getScanCode()),var0.getAction(),var0.getKey(),1);
			return;
		}else{
			return;
		}
	}
	@SubscribeEvent()
	public static void onInteraction(InputEvent.InteractionKeyMappingTriggered var0) {
		Minecraft var1 = Minecraft.getInstance();
		LocalPlayer var2 = var1.player;
		if ((var2) == (null)){
			return;
		}else{
			int var3 = 0;
			int var4 = 0;
			if (var0.isAttack()){
				var4 = 0;
				var3 = (((var1.hitResult) != (null)) && ((var1.hitResult.getType()) == (HitResult.Type.ENTITY))) ? (1) : (0);
			}else{
				if (var0.isUseItem()){
					var4 = 1;
				}else{
					if (var0.isPickBlock()){
						var4 = 2;
					}else{
						return;
					}
				}
			}
			InteractionHand var5 = var0.getHand();
			if (((InteractionTriggeredEvent)(NeoForge.EVENT_BUS.post((Event)(new InteractionTriggeredEvent((LivingEntity)(var2),var4,var5,(var3) != (0)))))).isCanceled()){
				var0.setSwingHand(false);
				var0.setCanceled(true);
				return;
			}else{
				ModNetwork.sendToServer(new CInteractionTriggeredPacket(var4,var5,(var3) != (0)));
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onCameraAnglesChanged(ViewportEvent.ComputeCameraAngles var0) {
		Minecraft var1 = Minecraft.getInstance();
		LocalPlayer var2 = var1.player;
		int var3 = ((var1.options.getCameraType()) == (CameraType.FIRST_PERSON)) ? (1) : (0);
		float var4 = EffectsHandler$Client.getDizzyCameraRoll(var2);
		if (((var3) != (0)) && (((double)(var4)) != (0D))){
			var0.setRoll((float)(Math.sin((double)((var2.tickCount) % (100)))));
		}
	}
	@SubscribeEvent()
	public static void onCameraZoom(SetCameraZoomEvent var0) {
		Player var1 = var0.getEntity();
		if ((var1) == (null)){
			return;
		}else{
			float var2 = 0F;
			var2 = (float)(((double)(var2)) + (MorphsHandler.getMorphCameraZoom(var1)));
			var2 = (float)(((double)(var2)) + (VehicleHandler.getStrikerCameraZoom(var1)));
			if (((double)(var2)) != (0D)){
				var0.setZoom(var2);
				var0.setResult(SetCameraZoomEvent$Result.ALLOW);
				return;
			}else{
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onCameraOffset(SetCameraOffsetEvent var0) {
		Player var1 = var0.getEntity();
		if ((var1) == (null)){
			return;
		}else{
			Vec3 var2 = Vec3.ZERO;
			MorphsHandler.updateMorphCameraOffset(var1,var2);
			if ((var2.length()) != (0D)){
				var0.setCameraPosition(var0.getCameraPosition().add(var2));
				var0.setResult(SetCameraOffsetEvent$Result.ALLOW);
				return;
			}else{
				return;
			}
		}
	}
}
package xyz.pixelatedw.mineminenomi.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.events.RenderOverlayEvent;
import xyz.pixelatedw.mineminenomi.handlers.entity.EffectsHandler$Client;

@Mixin(value={GameRenderer.class}, priority=990)
public abstract class GameRendererMixin {
	// Fields
	@Shadow() @Final() private Minecraft minecraft;

	@Inject(method={"renderItemInHand"}, at={@At(value="INVOKE", target="Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V", shift=At.Shift.AFTER)})
	private void renderItemInHand(Camera var1, float var2, Matrix4f var3, CallbackInfo var4) {
		LocalPlayer var5 = this.minecraft.player;
		if ((var5) == (null)){
			return;
		}else{
			PoseStack var6 = new PoseStack();
			var6.pushPose();
			var6.mulPose(var3.invert(new Matrix4f()));
			RenderOverlayEvent var7 = ((RenderOverlayEvent)(NeoForge.EVENT_BUS.post((Event)(new RenderOverlayEvent((Player)(var5),var6,var1,var2)))));
			EffectsHandler$Client.renderScreenEffects((Player)(var5),var6,var1,var2);
			var6.popPose();
			return;
		}
	}
}
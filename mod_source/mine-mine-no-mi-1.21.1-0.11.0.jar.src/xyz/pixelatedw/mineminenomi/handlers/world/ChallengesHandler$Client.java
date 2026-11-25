package xyz.pixelatedw.mineminenomi.handlers.world;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.renderers.ArenaSkybox;

public class ChallengesHandler$Client {
	// Fields
	public static final ArenaSkybox SKYSPHERE;
	public static final ArenaSkybox SKYSPHERE_OVERLAY;

	public static void renderMainSkysphere(PoseStack var0, Camera var1) {
		Minecraft var2 = Minecraft.getInstance();
		LocalPlayer var3 = var2.player;
		if ((var3) == (null)){
			return;
		}else{
			if (NuWorld.isChallengeDimension(var3.level())){
				var0 = new PoseStack();
				var0.setIdentity();
				var0.translate(-(var1.getPosition().x),0D,-(var1.getPosition().z));
				int var4 = (((Integer)(var2.options.renderDistance().get())).intValue()) * (25);
				SKYSPHERE.setRadius((float)(var4));
				SKYSPHERE_OVERLAY.setRadius((float)((var4) - (5)));
				var0.pushPose();
				SKYSPHERE.renderSphereInWorld(var0,var1,0D,0D,0D);
				SKYSPHERE_OVERLAY.renderSphereInWorld(var0,var1,0D,0D,0D);
				var0.popPose();
			}
			return;
		}
	}
	static  {
		ResourceLocation[] var0 = new ResourceLocation[]{ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/skyboxes/darkness.png")};
		SKYSPHERE = new ArenaSkybox().setTexture(true,var0).setAnimationSpeed(110000).setDetailLevel(25);
		ResourceLocation[] var1 = new ResourceLocation[]{ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/skyboxes/clouds.png")};
		SKYSPHERE_OVERLAY = new ArenaSkybox().setTexture(true,var1).setAnimationSpeed(80000).setDetailLevel(16).setAlpha(0.5F);
	}
}
package xyz.pixelatedw.mineminenomi.api.events;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;

public class RenderOverlayEvent extends Event {
	// Fields
	private Player player;
	private PoseStack matrixStack;
	private Camera activeRenderInfo;
	private float partialTicks;

	public RenderOverlayEvent(Player var1, PoseStack var2, Camera var3, float var4) {
		this.player = var1;
		this.matrixStack = var2;
		this.activeRenderInfo = var3;
		this.partialTicks = var4;
	}
	public Player getPlayer() {
		return this.player;
	}
	public PoseStack getMatrixStack() {
		return this.matrixStack;
	}
	public Camera getActiveRenderInfo() {
		return this.activeRenderInfo;
	}
	public float getPartialTicks() {
		return this.partialTicks;
	}
}
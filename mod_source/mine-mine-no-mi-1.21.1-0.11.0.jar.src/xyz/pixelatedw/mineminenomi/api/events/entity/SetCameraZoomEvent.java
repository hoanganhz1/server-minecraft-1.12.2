package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class SetCameraZoomEvent extends PlayerEvent {
	// Fields
	private float zoom;
	private SetCameraZoomEvent$Result result;

	public SetCameraZoomEvent(Player var1, float var2) {
		super(var1);
		this.result = SetCameraZoomEvent$Result.ALLOW;
		this.zoom = var2;
	}
	public float getZoom() {
		return this.zoom;
	}
	public void setZoom(float var1) {
		this.zoom = var1;
	}
	public void setResult(SetCameraZoomEvent$Result var1) {
		this.result = var1;
	}
	public SetCameraZoomEvent$Result getResult() {
		return this.result;
	}
}
package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class SetCameraOffsetEvent extends PlayerEvent {
	// Fields
	private Vec3 cameraPos;
	private SetCameraOffsetEvent$Result result;

	public SetCameraOffsetEvent(Player var1, Vec3 var2) {
		super(var1);
		this.result = SetCameraOffsetEvent$Result.ALLOW;
		this.cameraPos = var2;
	}
	public Vec3 getCameraPosition() {
		return this.cameraPos;
	}
	public void setCameraPosition(Vec3 var1) {
		this.cameraPos = var1;
	}
	public void setResult(SetCameraOffsetEvent$Result var1) {
		this.result = var1;
	}
	public SetCameraOffsetEvent$Result getResult() {
		return this.result;
	}
}
package xyz.pixelatedw.mineminenomi.api.poi;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class POIEventTarget {
	// Fields
	private ServerLevel level;
	private Vec3 pos;
	private long startTime;
	private long openTime;
	private POIEventTarget$EventTriggerAction triggerAction;

	public POIEventTarget() {
	}
	public POIEventTarget(ServerLevel var1, Vec3 var2, long var3) {
		this.level = var1;
		this.pos = var2;
		this.startTime = var1.getGameTime();
		this.openTime = var3;
	}
	public ServerLevel getLevel() {
		return this.level;
	}
	public Vec3 getPosition() {
		return this.pos;
	}
	public long getStartTime() {
		return this.startTime;
	}
	public boolean shouldTrigger(ServerPlayer var1) {
		return ((this.getTriggerAction()) != (null)) && ((var1.distanceToSqr(this.getPosition())) < (10000D));
	}
	@Nullable()
	public POIEventTarget$EventTriggerAction getTriggerAction() {
		return this.triggerAction;
	}
	public void setTriggerAction(POIEventTarget$EventTriggerAction var1) {
		this.triggerAction = var1;
	}
	public boolean hasExpired(ServerLevel var1) {
		return (var1.getGameTime()) > ((this.startTime) + (this.openTime));
	}
	public void tick() {
	}
	public CompoundTag save() {
		CompoundTag var1 = new CompoundTag();
		var1.putDouble("x",this.pos.x);
		var1.putDouble("y",this.pos.y);
		var1.putDouble("z",this.pos.z);
		var1.putLong("startTime",this.startTime);
		var1.putLong("openTime",this.openTime);
		return var1;
	}
	public void load(CompoundTag var1) {
		double var2 = var1.getDouble("x");
		double var3 = var1.getDouble("y");
		double var4 = var1.getDouble("z");
		Vec3 var5 = new Vec3(var2,var3,var4);
		this.pos = var5;
		this.startTime = var1.getLong("startTime");
		this.openTime = var1.getLong("openTime");
	}
}
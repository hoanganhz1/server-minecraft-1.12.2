package xyz.pixelatedw.mineminenomi.data.entity;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import xyz.pixelatedw.mineminenomi.init.ModAttachments;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SSyncCameraLockPacket;

public class CameraLockData implements INBTSerializable<CompoundTag> {
	// Fields
	private LivingEntity owner;
	private boolean hasYawClamped;
	private boolean hasPitchClamped;
	private float initialYaw;
	private float initialPitch;
	private float maxYaw;
	private float maxPitch;
	private long lockTicks;

	public static CameraLockData createCapability(IAttachmentHolder var0) {
		return new CameraLockData(((LivingEntity)(var0)));
	}
	public static Optional<CameraLockData> get(LivingEntity var0) {
		if (!(var0.hasData(ModAttachments.CAMERA_LOCK))){
			var0.setData(ModAttachments.CAMERA_LOCK,new CameraLockData(var0));
		}
		return (Optional<CameraLockData>) (Optional) (var0.getExistingData(ModAttachments.CAMERA_LOCK));
	}
	public static CameraLockData getOrCreate(LivingEntity var0) {
		if (!(var0.hasData(ModAttachments.CAMERA_LOCK))){
			var0.setData(ModAttachments.CAMERA_LOCK,new CameraLockData(var0));
		}
		return ((CameraLockData)(var0.getData(ModAttachments.CAMERA_LOCK)));
	}
	public static void pinFixed(Player var0) {
		Player var1 = var0;
		final Player var1_f1 = var1;
		Consumer<CameraLockData> var2 = (l0) -> {
			l0.setYawClamp(var1_f1.getYRot(),0F);
			l0.setPitchClamp(var1_f1.getXRot(),0F);
		};
		changeServerState(var0,var2);
	}
	public static void pinFixed(Player var0, long var1) {
		Player var2 = var0;
		long var3 = var1;
		final Player var2_f1 = var2;
		final long var3_f2 = var3;
		Consumer<CameraLockData> var4 = (l0) -> {
			l0.setYawClamp(var2_f1.getYRot(),0F);
			l0.setPitchClamp(var2_f1.getXRot(),0F);
			l0.setLockTimer(var3_f2);
		};
		changeServerState(var0,var4);
	}
	public static void pinClampedYaw(Player var0, float var1, float var2) {
		float var3 = var1;
		float var4 = var2;
		final float var3_f1 = var3;
		final float var4_f2 = var4;
		Consumer<CameraLockData> var5 = (l0) -> {
			l0.setYawClamp(var3_f1,var4_f2);
		};
		changeServerState(var0,var5);
	}
	public static void pinClampedYaw(Player var0, float var1, float var2, long var3) {
		float var4 = var1;
		float var5 = var2;
		long var6 = var3;
		final float var4_f1 = var4;
		final float var5_f2 = var5;
		final long var6_f3 = var6;
		Consumer<CameraLockData> var7 = (l0) -> {
			l0.setYawClamp(var4_f1,var5_f2);
			l0.setLockTimer(var6_f3);
		};
		changeServerState(var0,var7);
	}
	public static void pinClampedPitch(Player var0, float var1, float var2) {
		float var3 = var1;
		float var4 = var2;
		final float var3_f1 = var3;
		final float var4_f2 = var4;
		Consumer<CameraLockData> var5 = (l0) -> {
			l0.setPitchClamp(var3_f1,var4_f2);
		};
		changeServerState(var0,var5);
	}
	public static void pinClampedPitch(Player var0, float var1, float var2, long var3) {
		float var4 = var1;
		float var5 = var2;
		long var6 = var3;
		final float var4_f1 = var4;
		final float var5_f2 = var5;
		final long var6_f3 = var6;
		Consumer<CameraLockData> var7 = (l0) -> {
			l0.setPitchClamp(var4_f1,var5_f2);
			l0.setLockTimer(var6_f3);
		};
		changeServerState(var0,var7);
	}
	public static void pinClampedYawAndPitch(Player var0, float var1, float var2, float var3, float var4) {
		float var5 = var1;
		float var6 = var2;
		float var7 = var3;
		float var8 = var4;
		final float var5_f1 = var5;
		final float var6_f2 = var6;
		final float var7_f3 = var7;
		final float var8_f4 = var8;
		Consumer<CameraLockData> var9 = (l0) -> {
			l0.setYawClamp(var5_f1,var6_f2);
			l0.setPitchClamp(var7_f3,var8_f4);
		};
		changeServerState(var0,var9);
	}
	public static void pinClampedYawAndPitch(Player var0, float var1, float var2, float var3, float var4, long var5) {
		float var6 = var1;
		float var7 = var2;
		float var8 = var3;
		float var9 = var4;
		long var10 = var5;
		final float var6_f1 = var6;
		final float var7_f2 = var7;
		final float var8_f3 = var8;
		final float var9_f4 = var9;
		final long var10_f5 = var10;
		Consumer<CameraLockData> var11 = (l0) -> {
			l0.setYawClamp(var6_f1,var7_f2);
			l0.setPitchClamp(var8_f3,var9_f4);
			l0.setLockTimer(var10_f5);
		};
		changeServerState(var0,var11);
	}
	public static void resetLock(Player var0) {
		changeServerState(var0,(Consumer)(((Consumer<CameraLockData>)(CameraLockData::resetLock))));
	}
	public static void serverTick(ServerPlayer var0) {
		ServerPlayer var1 = var0;
		final ServerPlayer var1_f1 = var1;
		Consumer<CameraLockData> var2 = (l0) -> {
			l0.enforce();
			if (l0.tickTimer()){
				syncCameraLock(var1_f1);
			}
		};
		((java.util.Optional)(get((LivingEntity)(var0)))).ifPresent((Consumer)(var2));
	}
	public static void clientTick(Player var0) {
		Consumer<CameraLockData> var1 = (l0) -> {
			l0.enforce();
			l0.tickTimer();
		};
		((java.util.Optional)(get((LivingEntity)(var0)))).ifPresent((Consumer)(var1));
	}
	public static boolean hasCameraLocked(Player var0) {
		Function<CameraLockData, Boolean> var1 = (l0) -> {
			return Boolean.valueOf((l0.hasYawClamped()) || (l0.hasPitchClamped()));
		};
		return ((Boolean)(((java.util.Optional)(get((LivingEntity)(var0)))).map((Function)(var1)).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	private static void changeServerState(Player var0, Consumer<CameraLockData> var1) {
		if (var0 instanceof ServerPlayer){
			ServerPlayer var2 = ((ServerPlayer)(var0));
			Consumer<CameraLockData> var3 = var1;
			ServerPlayer var4 = var2;
			final Consumer<CameraLockData> var3_f1 = var3;
			final ServerPlayer var4_f2 = var4;
			Consumer<CameraLockData> var5 = (l0) -> {
			var3_f1.accept(l0);
			syncCameraLock(var4_f2);
		};
			((java.util.Optional)(get((LivingEntity)(var2)))).ifPresent((Consumer)(var5));
			return;
		}else{
			return;
		}
	}
	private static void syncCameraLock(ServerPlayer var0) {
		ModNetwork.sendTo(new SSyncCameraLockPacket((Player)(var0)),(Player)(var0));
	}
	private CameraLockData(LivingEntity var1) {
		this.hasYawClamped = false;
		this.hasPitchClamped = false;
		this.initialYaw = 0F;
		this.initialPitch = 0F;
		this.maxYaw = 0F;
		this.maxPitch = 0F;
		this.lockTicks = 0L;
		this.owner = var1;
	}
	public boolean hasYawClamped() {
		return this.hasYawClamped;
	}
	public float getYaw() {
		if (this.hasYawClamped){
			float var1 = Mth.clamp(Mth.wrapDegrees((this.owner.getYRot()) - (this.initialYaw)),-(this.maxYaw),this.maxYaw);
			return Mth.wrapDegrees((this.initialYaw) + (var1));
		}else{
			return this.owner.getYRot();
		}
	}
	public void setYawClamp(float var1, float var2) {
		this.hasYawClamped = true;
		this.initialYaw = Mth.wrapDegrees(var1);
		this.maxYaw = Mth.clamp(Math.abs(var2),0F,180F);
	}
	public boolean hasPitchClamped() {
		return this.hasPitchClamped;
	}
	public float getPitch() {
		if (this.hasPitchClamped){
			float var1 = Math.max(-90F,(this.initialPitch) - (this.maxPitch));
			float var2 = Math.min(90F,(this.initialPitch) + (this.maxPitch));
			return Mth.clamp(this.owner.getXRot(),var1,var2);
		}else{
			return this.owner.getXRot();
		}
	}
	public void setPitchClamp(float var1, float var2) {
		this.hasPitchClamped = true;
		this.initialPitch = Mth.clamp(var1,-90F,90F);
		this.maxPitch = Mth.clamp(Math.abs(var2),0F,180F);
	}
	public void setLockTimer(long var1) {
		this.lockTicks = Math.max(0L,var1);
	}
	public void resetLock() {
		this.hasYawClamped = false;
		this.hasPitchClamped = false;
		this.initialYaw = 0F;
		this.initialPitch = 0F;
		this.maxYaw = 0F;
		this.maxPitch = 0F;
		this.lockTicks = 0L;
	}
	public void enforce() {
		float var1 = 0.0F;
		if (this.hasYawClamped()){
			var1 = this.getYaw();
			this.owner.setYRot(var1);
			this.owner.yRotO = var1;
		}
		if (this.hasPitchClamped()){
			var1 = this.getPitch();
			this.owner.setXRot(var1);
			this.owner.xRotO = var1;
		}
	}
	public boolean tickTimer() {
		if ((this.lockTicks) <= (0L)){
			return false;
		}else{
			this.lockTicks = (this.lockTicks) - (1L);
			if ((this.lockTicks) > (0L)){
				return false;
			}else{
				this.resetLock();
				return true;
			}
		}
	}
	public CompoundTag serializeNBT(HolderLookup.Provider var1) {
		CompoundTag var2 = new CompoundTag();
		var2.putBoolean("hasYawClamped",this.hasYawClamped);
		var2.putBoolean("hasPitchClamped",this.hasPitchClamped);
		var2.putFloat("initialYaw",this.initialYaw);
		var2.putFloat("initialPitch",this.initialPitch);
		var2.putFloat("maxYaw",this.maxYaw);
		var2.putFloat("maxPitch",this.maxPitch);
		var2.putLong("lockTicks",this.lockTicks);
		return var2;
	}
	public void deserializeNBT(HolderLookup.Provider var1, CompoundTag var2) {
		if (var2.contains("hasYawClamped")){
			this.hasYawClamped = var2.getBoolean("hasYawClamped");
		}
		if (var2.contains("hasPitchClamped")){
			this.hasPitchClamped = var2.getBoolean("hasPitchClamped");
		}
		if (var2.contains("initialYaw")){
			this.initialYaw = var2.getFloat("initialYaw");
		}
		if (var2.contains("initialPitch")){
			this.initialPitch = Mth.clamp(var2.getFloat("initialPitch"),-90F,90F);
		}
		if (var2.contains("maxYaw")){
			this.maxYaw = Mth.clamp(Math.abs(var2.getFloat("maxYaw")),0F,180F);
		}
		if (var2.contains("maxPitch")){
			this.maxPitch = Mth.clamp(Math.abs(var2.getFloat("maxPitch")),0F,180F);
		}
		if (var2.contains("lockTicks")){
			this.lockTicks = Math.max(0L,var2.getLong("lockTicks"));
		}
	}
}
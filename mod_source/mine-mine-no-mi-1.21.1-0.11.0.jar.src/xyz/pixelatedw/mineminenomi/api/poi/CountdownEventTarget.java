package xyz.pixelatedw.mineminenomi.api.poi;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.phys.Vec3;

public class CountdownEventTarget extends POIEventTarget {
	// Fields
	private int countdown;
	private UUID targetId;

	public CountdownEventTarget() {
	}
	public CountdownEventTarget(ServerLevel var1, LivingEntity var2, long var3, int var4) {
		super(var1,var2.position(),var3);
		this.countdown = var4;
		this.targetId = var2.getUUID();
	}
	public boolean shouldTrigger(ServerPlayer var1) {
		return (this.countdown) <= (0);
	}
	public void tick() {
		if ((this.countdown) > (0)){
			this.countdown = (this.countdown) - (1);
		}
	}
	public CompoundTag save() {
		CompoundTag var1 = super.save();
		var1.putInt("countdown",this.countdown);
		var1.putUUID("targetId",this.targetId);
		return var1;
	}
	public void load(CompoundTag var1) {
		super.load(var1);
		this.countdown = var1.getInt("countdown");
		this.targetId = var1.getUUID("targetId");
	}
	public UUID getTargetId() {
		return this.targetId;
	}
	@Nullable()
	public Optional<LivingEntity> getTarget(ServerLevel var1) {
		Player var2 = var1.getPlayerByUUID(this.targetId);
		if ((var2) != (null)){
			return (Optional<LivingEntity>) (Optional) (Optional.ofNullable(var2));
		}else{
			Entity var3 = ((Entity)(var1.getEntities().get(this.targetId)));
			if (((var3) != (null)) && (var3 instanceof LivingEntity)){
				return (Optional<LivingEntity>) (Optional) (Optional.ofNullable(((LivingEntity)(var3))));
			}else{
				return (Optional<LivingEntity>) (Optional) (Optional.empty());
			}
		}
	}
}
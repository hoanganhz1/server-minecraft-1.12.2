package xyz.pixelatedw.mineminenomi.api.poi;

import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.data.world.NPCWorldData;
import xyz.pixelatedw.mineminenomi.entities.mobs.NotoriousEntity;

public class NTEventTarget extends POIEventTarget {
	// Fields
	private TrackedNPC tracked;

	public NTEventTarget() {
	}
	public NTEventTarget(ServerLevel var1, Vec3 var2, long var3, TrackedNPC var4) {
		super(var1,var2,var3);
		this.setTracked(var4);
	}
	private void setTracked(TrackedNPC var1) {
		this.tracked = var1;
		NTEventTarget var2 = this;
		POIEventTarget$EventTriggerAction var3 = (l0, l1) -> {
			this.tracked.spawnTrackedMob(l0,l1.getPosition());
		};
		this.setTriggerAction(var3);
	}
	public CompoundTag save() {
		CompoundTag var1 = super.save();
		var1.putLong("trackedId",this.tracked.getId());
		return var1;
	}
	public void load(CompoundTag var1) {
		super.load(var1);
		long var2 = var1.getLong("trackedId");
		Optional var3 = NPCWorldData.get().getTrackedMob(var2);
		if (var3.isPresent()){
			this.setTracked(((TrackedNPC)(var3.get())));
		}
	}
	public TrackedNPC getTrackedNPC() {
		return this.tracked;
	}
}
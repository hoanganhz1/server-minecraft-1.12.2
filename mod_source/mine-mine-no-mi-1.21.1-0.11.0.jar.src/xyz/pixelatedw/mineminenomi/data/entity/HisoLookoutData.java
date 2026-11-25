package xyz.pixelatedw.mineminenomi.data.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.core.Direction8;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttachments;

public class HisoLookoutData implements INBTSerializable<CompoundTag> {
	// Fields
	private Animal owner;
	private Map<UUID, HisoLookoutData$HisoLookoutTarget> lookoutSet;

	public static HisoLookoutData createCapability(IAttachmentHolder var0) {
		return new HisoLookoutData(((Animal)(var0)));
	}
	public static Optional<HisoLookoutData> get(Animal var0) {
		if (!(var0.hasData(ModAttachments.HISO_LOOKOUT))){
			var0.setData(ModAttachments.HISO_LOOKOUT,new HisoLookoutData(var0));
		}
		return (Optional<HisoLookoutData>) (Optional) (var0.getExistingData(ModAttachments.HISO_LOOKOUT));
	}
	public static HisoLookoutData getOrCreate(Animal var0) {
		if (!(var0.hasData(ModAttachments.HISO_LOOKOUT))){
			var0.setData(ModAttachments.HISO_LOOKOUT,new HisoLookoutData(var0));
		}
		return ((HisoLookoutData)(var0.getData(ModAttachments.HISO_LOOKOUT)));
	}
	private HisoLookoutData(Animal var1) {
		this.lookoutSet = new HashMap();
		this.owner = var1;
	}
	public Map<UUID, HisoLookoutData$HisoLookoutTarget> getSeenTargets() {
		return this.lookoutSet;
	}
	public void registerSeenTarget(LivingEntity var1) {
		Direction8 var2 = WyHelper.getLookDirection(var1);
		HisoLookoutData$HisoLookoutTarget var3 = new HisoLookoutData$HisoLookoutTarget(var1.getName().getString(),var1.level().getGameTime(),var2);
		this.lookoutSet.put(var1.getUUID(),var3);
	}
	public void removeSeenTarget(UUID var1) {
		this.lookoutSet.remove(var1);
	}
	public void clearSeenTargets() {
		this.lookoutSet.clear();
	}
	public CompoundTag serializeNBT(HolderLookup.Provider var1) {
		return null;
	}
	public void deserializeNBT(HolderLookup.Provider var1, CompoundTag var2) {
	}
}
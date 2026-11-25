package xyz.pixelatedw.mineminenomi.data.entity;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import xyz.pixelatedw.mineminenomi.init.ModAttachments;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SKairosekiCoatingPacket;

public class KairosekiCoatingData implements INBTSerializable<CompoundTag> {
	// Fields
	public static final int MAX_COATING_AMOUNT = 5;
	private Entity owner;
	private int coatingLevel;

	public static KairosekiCoatingData createCapability(IAttachmentHolder var0) {
		return new KairosekiCoatingData(((Entity)(var0)));
	}
	public static Optional<KairosekiCoatingData> get(Entity var0) {
		if (!(var0.hasData(ModAttachments.KAIROSEKI_COATING))){
			var0.setData(ModAttachments.KAIROSEKI_COATING,new KairosekiCoatingData(var0));
		}
		return (Optional<KairosekiCoatingData>) (Optional) (var0.getExistingData(ModAttachments.KAIROSEKI_COATING));
	}
	public static KairosekiCoatingData getOrCreate(Entity var0) {
		if (!(var0.hasData(ModAttachments.KAIROSEKI_COATING))){
			var0.setData(ModAttachments.KAIROSEKI_COATING,new KairosekiCoatingData(var0));
		}
		return ((KairosekiCoatingData)(var0.getData(ModAttachments.KAIROSEKI_COATING)));
	}
	private KairosekiCoatingData(Entity var1) {
		this.owner = var1;
	}
	public boolean isFullyCoated() {
		return (this.coatingLevel) >= (5);
	}
	public int getCoatingLevel() {
		return this.coatingLevel;
	}
	public boolean addCoatingLevel(int var1) {
		if (((var1) > (0)) && (this.isFullyCoated())){
			return false;
		}else{
			if (((var1) < (0)) && ((this.getCoatingLevel()) == (0))){
				return false;
			}else{
				this.setCoatingLevel((this.coatingLevel) + (var1));
				return true;
			}
		}
	}
	public void setCoatingLevel(int var1) {
		this.coatingLevel = Mth.clamp(var1,0,5);
		if (((this.owner) != (null)) && (!(this.owner.level().isClientSide))){
			ModNetwork.sendToAllTracking(new SKairosekiCoatingPacket(this.owner.getId(),this.coatingLevel),this.owner);
		}
	}
	public CompoundTag serializeNBT(HolderLookup.Provider var1) {
		CompoundTag var2 = new CompoundTag();
		var2.putInt("coatingLevel",this.getCoatingLevel());
		return var2;
	}
	public void deserializeNBT(HolderLookup.Provider var1, CompoundTag var2) {
		this.setCoatingLevel(var2.getInt("coatingLevel"));
	}
}
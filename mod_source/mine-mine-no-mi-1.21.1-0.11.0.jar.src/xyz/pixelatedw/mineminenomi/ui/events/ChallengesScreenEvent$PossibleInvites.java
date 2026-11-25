package xyz.pixelatedw.mineminenomi.ui.events;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class ChallengesScreenEvent$PossibleInvites implements INBTSerializable<CompoundTag> {
	// Fields
	private int slot;
	private int[] ids;

	ChallengesScreenEvent$PossibleInvites(final ChallengesScreenEvent var1) {
	}
	public ChallengesScreenEvent$PossibleInvites(final ChallengesScreenEvent var1, int var2, int[] var3) {
		this.slot = var2;
		this.ids = var3;
	}
	public int getSlot() {
		return this.slot;
	}
	public int[] getIds() {
		return this.ids;
	}
	public CompoundTag serializeNBT(HolderLookup.Provider var1) {
		CompoundTag var2 = new CompoundTag();
		var2.putInt("slot",this.slot);
		var2.putIntArray("ids",this.ids);
		return var2;
	}
	public void deserializeNBT(HolderLookup.Provider var1, CompoundTag var2) {
		this.slot = var2.getInt("slot");
		this.ids = var2.getIntArray("ids");
	}
}
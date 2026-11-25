package xyz.pixelatedw.mineminenomi.ui.events;

import com.google.common.primitives.Ints;
import java.util.Collection;
import java.util.List;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.INBTSerializable;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.acks.SScreenDataEventPacket;

public class ChallengesScreenEvent implements INBTSerializable<CompoundTag> {
	// Fields
	private ChallengesScreenEvent$PossibleInvites possibleInvites;

	public ChallengesScreenEvent() {
		this.possibleInvites = null;
	}
	public ChallengesScreenEvent$PossibleInvites getPossibleInvites() {
		return this.possibleInvites;
	}
	public void setPossibleInvites(int var1, List<Integer> var2) {
		this.possibleInvites = new ChallengesScreenEvent$PossibleInvites(this,var1,Ints.toArray(var2));
	}
	public void sendEvent(ServerPlayer var1) {
		ModNetwork.sendTo(new SScreenDataEventPacket((Player)(var1),(INBTSerializable)(this)),(Player)(var1));
	}
	public CompoundTag serializeNBT(HolderLookup.Provider var1) {
		CompoundTag var2 = new CompoundTag();
		var2.put("possibleInvites",(Tag)(this.possibleInvites.serializeNBT(var1)));
		return var2;
	}
	public void deserializeNBT(HolderLookup.Provider var1, CompoundTag var2) {
		this.possibleInvites = new ChallengesScreenEvent$PossibleInvites(this);
		this.possibleInvites.deserializeNBT(var1,var2.getCompound("possibleInvites"));
	}
}
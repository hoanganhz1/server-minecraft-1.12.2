package xyz.pixelatedw.mineminenomi.ui.events;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.INBTSerializable;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.acks.SScreenDataEventPacket;

public class TrialsScreenEvent implements INBTSerializable<CompoundTag> {
	// Fields
	private QuestId<?> questId;

	public TrialsScreenEvent() {
		this.questId = null;
	}
	public QuestId<?> getQuestId() {
		return (QuestId<?>) (QuestId) (this.questId);
	}
	public void setQuestId(QuestId<?> var1) {
		this.questId = var1;
	}
	public void sendEvent(ServerPlayer var1) {
		ModNetwork.sendTo(new SScreenDataEventPacket((Player)(var1),(INBTSerializable)(this)),(Player)(var1));
	}
	public CompoundTag serializeNBT(HolderLookup.Provider var1) {
		CompoundTag var2 = new CompoundTag();
		var2.putString("questId",this.questId.getRegistryKey().toString());
		return var2;
	}
	public void deserializeNBT(HolderLookup.Provider var1, CompoundTag var2) {
		this.questId = (QuestId) (QuestId.get(ResourceLocation.parse(var2.getString("questId"))));
	}
}
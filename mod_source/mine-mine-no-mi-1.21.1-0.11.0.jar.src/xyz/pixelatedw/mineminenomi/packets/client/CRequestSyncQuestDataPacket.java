package xyz.pixelatedw.mineminenomi.packets.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.ICollectItemObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IEquipItemObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.QuestData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenQuestTrackerScreenPacket;

public final record CRequestSyncQuestDataPacket(boolean openScreen) {
	// Fields
	public static final CustomPacketPayload.Type<CRequestSyncQuestDataPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_request_sync_quest_data"));
	public static final StreamCodec<FriendlyByteBuf, CRequestSyncQuestDataPacket> STREAM_CODEC;

	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		QuestData var3 = ((QuestData)(((java.util.Optional)(QuestData.get((Player)(var2)))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			Quest[] var4 = var3.getInProgressQuests();
			int var5 = var4.length;
			int var6 = 0;
			do{
				if ((var6) < (var5)){
					Quest var7 = var4[var6];
					if ((var7) == (null)){

					}else{
						Iterator var8 = var7.getObjectives().iterator();
						do{
							if (var8.hasNext()){
								int var12 = 0;
								Objective var9 = ((Objective)(var8.next()));
								if (var9 instanceof IReachDorikiObjective){
									var9.setProgress((Player)(var2),(float)(((Double)(EntityStatsData.get((LivingEntity)(var2)).map((Function)(((Function<EntityStatsData, Double>)(EntityStatsData::getDoriki)))).orElse(Double.valueOf(0D)))).doubleValue()),false);
								}
								if (var9 instanceof IEquipItemObjective){
									IEquipItemObjective var10 = ((IEquipItemObjective)(var9));
									EquipmentSlot[] var11 = EquipmentSlot.values();
									var12 = var11.length;
									int var13 = 0;
									do{
										if ((var13) < (var12)){
											EquipmentSlot var14 = var11[var13];
											if (!(((IEquipItemObjective)(var9)).checkSlot(var14))){

											}else{
												if (var10.checkEquippedItem((Player)(var2),var2.getItemBySlot(var14))){
													var9.alterProgress((Player)(var2),1F);
												}else{
													var9.alterProgress((Player)(var2),-1F);
												}
											}
											var13++;
											continue;
										}else{
											break;
										}
									} while (true);
								}
								if (var9 instanceof ICollectItemObjective){
									ICollectItemObjective var10_1 = ((ICollectItemObjective)(var9));
									ArrayList var11_1 = new ArrayList();
									var11_1.addAll((Collection)(var2.getInventory().items));
									var11_1.addAll((Collection)(var2.getInventory().offhand));
									var12 = var10_1.checkItems(var11_1);
									if ((var12) > (0)){
										var9.setProgress((Player)(var2),(float)(var12),false);
										continue;
									}else{
										continue;
									}
								}else{
									continue;
								}
							}else{
								break;
							}
						} while (true);
					}
					var6++;
					continue;
				}else{
					break;
				}
			} while (true);
			if (this.openScreen){
				ModNetwork.sendTo(new SOpenQuestTrackerScreenPacket(var3.getInProgressQuests()),(Player)(var2));
			}
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<CRequestSyncQuestDataPacket, Boolean> var0 = CRequestSyncQuestDataPacket::openScreen;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.BOOL,var0,(Function)(((Function<Boolean, CRequestSyncQuestDataPacket>)(CRequestSyncQuestDataPacket::new)))));
	}
}
package xyz.pixelatedw.mineminenomi.packets.client.crew;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.events.entity.CrewEvent$Create;
import xyz.pixelatedw.mineminenomi.api.ui.SimpleMessageScreenEvent;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;

public final record CCreateCrewPacket(String name) {
	// Fields
	public static final CustomPacketPayload.Type<CCreateCrewPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_create_crew"));
	public static final StreamCodec<FriendlyByteBuf, CCreateCrewPacket> STREAM_CODEC;

	public static void encode(FriendlyByteBuf var0, CCreateCrewPacket var1) {
		var0.writeInt(var1.name.length());
		var0.writeUtf(var1.name);
	}
	public static CCreateCrewPacket decode(FriendlyByteBuf var0) {
		String var1 = var0.readUtf(var0.readInt());
		return new CCreateCrewPacket(var1);
	}
	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var2)))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			FactionsWorldData var4 = FactionsWorldData.get();
			int var5 = ((!(var2.getMainHandItem().isEmpty())) && (((java.lang.Object)(var2.getMainHandItem().getItem())).equals(ModItems.SAKE_BOTTLE.get()))) ? (1) : (0);
			int var6 = ((var4.getCrewWithMember(var2.getUUID())) != (null)) ? (1) : (0);
			SimpleMessageScreenEvent var7 = new SimpleMessageScreenEvent();
			var7.setTimeVisible(40);
			if (((var5) != (0)) && (var3.isPirate())){
				if ((var6) != (0)){
					var7.setMessage(ModI18n.CREW_MESSAGE_ALREADY_IN_CREW);
					var7.sendEvent(var2);
					return;
				}else{
					if ((var4.getCrewByName(this.name)) != (null)){
						var7.setMessage(ModI18n.CREW_MESSAGE_NAME_ALREADY_EXISTS);
						var7.sendEvent(var2);
						return;
					}else{
						Crew var8 = new Crew(this.name,(LivingEntity)(var2));
						if (!(((CrewEvent$Create)(NeoForge.EVENT_BUS.post((Event)(new CrewEvent$Create((Player)(var2),var8))))).isCanceled())){
							var4.addCrew(var8);
							var8.create(var2.level());
							var7.sendEvent(var2);
							if (ServerConfig.isCrewWorldMessageEnabled()){
								Object[] var9 = new Object[]{this.name};
								MutableComponent var10 = Component.translatable(ModI18n.CREW_MESSAGE_NEW_CREW,var9);
								Iterator var11 = var2.level().players().iterator();
								do{
									if (var11.hasNext()){
										((Player)(var11.next())).sendSystemMessage((Component)(var10));
										continue;
									}else{
										break;
									}
								} while (true);
							}else{
								return;
							}
						}
						return;
					}
				}
			}else{
				var7.setMessage(ModI18n.INFO_GENERIC_ERROR);
				var7.sendEvent(var2);
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = CCreateCrewPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(CCreateCrewPacket::decode)))));
	}
}
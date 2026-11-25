package xyz.pixelatedw.mineminenomi.packets.client.ability.node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUnlockAbilityNodePacket;

public final record CUnlockAbilityNodePacket(AbilityCore<? extends IAbility> core, int index) {
	// Fields
	public static final CustomPacketPayload.Type<CUnlockAbilityNodePacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_unlock_ability_node"));
	public static final StreamCodec<FriendlyByteBuf, CUnlockAbilityNodePacket> STREAM_CODEC;

	public static void encode(FriendlyByteBuf var0, CUnlockAbilityNodePacket var1) {
		var0.writeResourceLocation(var1.core.getRegistryKey());
		var0.writeInt(var1.index);
	}
	public static CUnlockAbilityNodePacket decode(FriendlyByteBuf var0) {
		AbilityCore var1 = ((AbilityCore)(WyRegistry.ABILITIES.get(var0.readResourceLocation())));
		int var2 = var0.readInt();
		return new CUnlockAbilityNodePacket(var1,var2);
	}
	public void handle(ServerPayloadContext var1) {
		if ((this.core) == (null)){
			return;
		}else{
			try{
				ServerPlayer var2 = var1.player();
				AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var2)))).orElse((Object)(null))));
				if ((var3) == (null)){
					return;
				}else{
					AbilityNode var4 = var3.getNode(this.core,this.index);
					if ((var4) == (null)){
						return;
					}else{
						if (var4.canUnlock((LivingEntity)(var2))){
							var4.unlockNode((LivingEntity)(var2));
							ArrayList var5 = new ArrayList();
							Iterator var6 = var3.getNodes().iterator();
							do{
								if (var6.hasNext()){
									AbilityNode var7 = ((AbilityNode)(var6.next()));
									if (var7.canUnlock((LivingEntity)(var2))){
										var5.add(Integer.valueOf(var7.hashCode()));
										continue;
									}else{
										continue;
									}
								}else{
									break;
								}
							} while (true);
							ModNetwork.sendTo(new SUnlockAbilityNodePacket(this.core,this.index,(List)(var5)),(Player)(var2));
						}else{
							return;
						}
					}
				}
			}catch(Exception var2_1){
				var2_1.printStackTrace();
			}
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = CUnlockAbilityNodePacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(CUnlockAbilityNodePacket::decode)))));
	}
}
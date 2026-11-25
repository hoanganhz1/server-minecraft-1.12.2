package xyz.pixelatedw.mineminenomi.packets.server.ability;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.AbilityTreeScreen;

public final record SUnlockAbilityNodePacket(AbilityCore<? extends IAbility> core, int index, List<Integer> nodes) {
	// Fields
	public static final CustomPacketPayload.Type<SUnlockAbilityNodePacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_unlock_ability_node"));
	public static final StreamCodec<FriendlyByteBuf, SUnlockAbilityNodePacket> STREAM_CODEC;

	public static void encode(FriendlyByteBuf var0, SUnlockAbilityNodePacket var1) {
		var0.writeResourceLocation(var1.core.getRegistryKey());
		var0.writeInt(var1.index);
		var0.writeInt(var1.nodes.size());
		Iterator var2 = var1.nodes.iterator();
		do{
			if (var2.hasNext()){
				var0.writeInt(((Integer)(var2.next())).intValue());
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public static SUnlockAbilityNodePacket decode(FriendlyByteBuf var0) {
		AbilityCore var1 = ((AbilityCore)(WyRegistry.ABILITIES.get(var0.readResourceLocation())));
		int var2 = var0.readInt();
		ArrayList var3 = new ArrayList();
		int var4 = var0.readInt();
		int var5 = 0;
		do{
			if ((var5) < (var4)){
				var3.add(Integer.valueOf(var0.readInt()));
				var5++;
				continue;
			}else{
				break;
			}
		} while (true);
		return new SUnlockAbilityNodePacket(var1,var2,(List)(var3));
	}
	public void handle(ClientPayloadContext var1) {
		if ((this.core) == (null)){
			return;
		}else{
			Player var2 = var1.player();
			AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var2)))).orElse((Object)(null))));
			if ((var3) == (null)){
				return;
			}else{
				AbilityNode var4 = var3.getNode(this.core,this.index);
				if ((var4) == (null)){
					return;
				}else{
					var4.unlockNode((LivingEntity)(var2));
					Screen var5 = Minecraft.getInstance().screen;
					if (var5 instanceof AbilityTreeScreen){
						AbilityTreeScreen var6 = ((AbilityTreeScreen)(var5));
						var6.unlockableNodes.clear();
						Iterator var7 = var3.getNodes().iterator();
						do{
							if (var7.hasNext()){
								AbilityNode var8 = ((AbilityNode)(var7.next()));
								if (this.nodes.contains(Integer.valueOf(var8.hashCode()))){
									var6.unlockableNodes.add(var8);
									continue;
								}else{
									continue;
								}
							}else{
								break;
							}
						} while (true);
					}
					return;
				}
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = SUnlockAbilityNodePacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SUnlockAbilityNodePacket::decode)))));
	}
}
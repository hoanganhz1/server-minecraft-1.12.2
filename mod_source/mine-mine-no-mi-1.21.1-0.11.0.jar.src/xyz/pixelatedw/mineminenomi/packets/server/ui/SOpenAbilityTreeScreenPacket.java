package xyz.pixelatedw.mineminenomi.packets.server.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.AbilityTreeScreen;

public final record SOpenAbilityTreeScreenPacket(int tpLimit, List<Integer> nodes) {
	// Fields
	public static final CustomPacketPayload.Type<SOpenAbilityTreeScreenPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_open_ability_tree_screen"));
	public static final StreamCodec<FriendlyByteBuf, SOpenAbilityTreeScreenPacket> STREAM_CODEC;

	public static void encode(FriendlyByteBuf var0, SOpenAbilityTreeScreenPacket var1) {
		var0.writeInt(var1.tpLimit);
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
	public static SOpenAbilityTreeScreenPacket decode(FriendlyByteBuf var0) {
		int var1 = var0.readInt();
		ArrayList var2 = new ArrayList();
		int var3 = var0.readInt();
		int var4 = 0;
		do{
			if ((var4) < (var3)){
				var2.add(Integer.valueOf(var0.readInt()));
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
		return new SOpenAbilityTreeScreenPacket(var1,(List)(var2));
	}
	public void handle(ClientPayloadContext var1) {
		LocalPlayer var2 = Minecraft.getInstance().player;
		if ((var2) == (null)){
			return;
		}else{
			AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var2)))).orElse((Object)(null))));
			if ((var3) == (null)){
				return;
			}else{
				ArrayList var4 = new ArrayList();
				Iterator var5 = var3.getNodes().iterator();
				do{
					if (var5.hasNext()){
						AbilityNode var6 = ((AbilityNode)(var5.next()));
						if (this.nodes.contains(Integer.valueOf(var6.hashCode()))){
							var4.add(var6);
							continue;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
				Minecraft.getInstance().setScreen((Screen)(new AbilityTreeScreen(this.tpLimit,(List)(var4))));
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = SOpenAbilityTreeScreenPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SOpenAbilityTreeScreenPacket::decode)))));
	}
}
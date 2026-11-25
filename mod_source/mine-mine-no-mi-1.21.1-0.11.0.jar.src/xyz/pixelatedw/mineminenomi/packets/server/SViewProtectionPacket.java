package xyz.pixelatedw.mineminenomi.packets.server;

import com.mojang.datafixers.util.Function3;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.handlers.protection.AbilityProtectionHandler$Client;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SViewProtectionPacket(boolean state, boolean update, Map<String, ProtectedArea> areas) {
	// Fields
	public static final CustomPacketPayload.Type<SViewProtectionPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_view_protections"));
	public static final StreamCodec<FriendlyByteBuf, SViewProtectionPacket> STREAM_CODEC;

	public SViewProtectionPacket(ServerLevel var1, boolean var2) {
		this(var2,false,ProtectedAreasData.get(var1).getAllRestrictions());
	}
	public static SViewProtectionPacket update(ServerLevel var0) {
		return new SViewProtectionPacket(false,true,ProtectedAreasData.get(var0).getAllRestrictions());
	}
	public void handle(ClientPayloadContext var1) {
		int var2 = ((this.update) && ((AbilityProtectionHandler$Client.CLIENT_AREAS.size()) > (0))) ? (1) : (0);
		AbilityProtectionHandler$Client.CLIENT_AREAS.clear();
		if ((!(this.state)) && ((var2) == (0))){

		}else{
			AbilityProtectionHandler$Client.CLIENT_AREAS.putAll(this.areas);
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SViewProtectionPacket, Boolean> var0 = SViewProtectionPacket::state;
		Function<SViewProtectionPacket, Boolean> var1 = SViewProtectionPacket::update;
		IntFunction<Map> var2 = HashMap::new;
		Function<SViewProtectionPacket, Map> var3 = SViewProtectionPacket::areas;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.BOOL,var0,ByteBufCodecs.BOOL,var1,ByteBufCodecs.map(var2,ByteBufCodecs.STRING_UTF8,ProtectedArea.STREAM_CODEC),var3,(Function3)(((com.mojang.datafixers.util.Function3)(SViewProtectionPacket::new)))));
	}
}
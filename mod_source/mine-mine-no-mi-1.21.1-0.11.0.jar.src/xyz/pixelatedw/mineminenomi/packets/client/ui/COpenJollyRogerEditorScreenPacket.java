package xyz.pixelatedw.mineminenomi.packets.client.ui;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenJollyRogerEditorScreenPacket;

public class COpenJollyRogerEditorScreenPacket implements IServerPacket {
	// Fields
	public static final CustomPacketPayload.Type<COpenJollyRogerEditorScreenPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_open_jolly_roger_editor_screen"));
	public static final COpenJollyRogerEditorScreenPacket INSTANCE = new COpenJollyRogerEditorScreenPacket();
	public static final StreamCodec<FriendlyByteBuf, COpenJollyRogerEditorScreenPacket> STREAM_CODEC = IPacket.defaultStreamCodec(INSTANCE);

	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		Crew var3 = FactionsWorldData.get().getCrewWithMember(var2.getUUID());
		if ((var3) == (null)){
			return;
		}else{
			ServerPlayer var4 = var2;
			Crew var5 = var3;
			final ServerPlayer var4_f1 = var4;
			final Crew var5_f2 = var5;
			Predicate<JollyRogerElement> var6 = (l0) -> {
			return ((l0) != (null)) && (l0.canUse((Player)(var4_f1),var5_f2));
		};
			List var7 = WyRegistry.JOLLY_ROGER_ELEMENTS.stream().filter(var6).toList();
			ModNetwork.sendTo(new SOpenJollyRogerEditorScreenPacket(false,var3,var7),(Player)(var2));
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
}
package xyz.pixelatedw.mineminenomi.packets.client.entity;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.entities.IVehicleAltMode;
import xyz.pixelatedw.mineminenomi.packets.IPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;

public class CSwitchVehicleModePacket implements IServerPacket {
	// Fields
	public static final CustomPacketPayload.Type<CSwitchVehicleModePacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_switch_vehicle_mode"));
	private static final CSwitchVehicleModePacket INSTANCE = new CSwitchVehicleModePacket();
	public static final StreamCodec<FriendlyByteBuf, CSwitchVehicleModePacket> STREAM_CODEC = IPacket.defaultStreamCodec(INSTANCE);

	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		if (((var2) != (null)) && ((var2.isAlive()) && (var2.isPassenger()))){
			LivingEntity var3 = var2.getVehicle().getControllingPassenger();
			if (((var3) != (null)) && (var3.equals(var2))){
				Entity var4 = var2.getVehicle();
				if (var4 instanceof IVehicleAltMode){
					((IVehicleAltMode)(var4)).changeVehicleMode();
				}else{
					return;
				}
			}else{
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
}
package xyz.pixelatedw.mineminenomi.packets.server.ability;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record STriggerAbilityUseEventsPacket(AbilityCore<?> ability) {
	// Fields
	public static final CustomPacketPayload.Type<STriggerAbilityUseEventsPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_trigger_ability_use_events"));
	public static final StreamCodec<FriendlyByteBuf, STriggerAbilityUseEventsPacket> STREAM_CODEC;

	public static void encode(FriendlyByteBuf var0, STriggerAbilityUseEventsPacket var1) {
		var0.writeResourceLocation(var1.ability.getRegistryKey());
	}
	public static STriggerAbilityUseEventsPacket decode(FriendlyByteBuf var0) {
		AbilityCore var1 = ((AbilityCore)(WyRegistry.ABILITIES.get(var0.readResourceLocation())));
		return new STriggerAbilityUseEventsPacket(var1);
	}
	public void handle(ClientPayloadContext var1) {
		LocalPlayer var2 = Minecraft.getInstance().player;
		if ((var2) == (null)){
			return;
		}else{
			STriggerAbilityUseEventsPacket var3 = this;
			Function<AbilityData, IAbility> var4 = (l0) -> {
			return l0.getEquippedAbility(this.ability);
		};
			LocalPlayer var5 = var2;
			final LocalPlayer var5_f1 = var5;
			Consumer<IAbility> var6 = (l0) -> {
			if (l0 instanceof Ability){
				((Ability)(l0)).dispatchUseEvents((LivingEntity)(var5_f1));
			}
		};
			((java.util.Optional)(((java.util.Optional)(AbilityData.get((LivingEntity)(var2)))).map((Function)(var4)))).ifPresent((Consumer)(var6));
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = STriggerAbilityUseEventsPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(STriggerAbilityUseEventsPacket::decode)))));
	}
}
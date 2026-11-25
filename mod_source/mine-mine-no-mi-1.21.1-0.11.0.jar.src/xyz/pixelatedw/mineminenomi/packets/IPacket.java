package xyz.pixelatedw.mineminenomi.packets;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;

public interface IPacket<C extends IPayloadContext> extends CustomPacketPayload {
	public abstract void handle(C var0);
	public default IPacket<C> reply(C var1) {
		return (IPacket<C>) (IPacket) (null);
	}
	public static int abilitySlot(LivingEntity var0, IAbility var1) {
		IAbility var2 = var1;
		final IAbility var2_f1 = var2;
		Function<AbilityData, Integer> var3 = (l0) -> {
			return Integer.valueOf(l0.getEquippedAbilitySlot(var2_f1));
		};
		return ((Integer)(((java.util.Optional)(AbilityData.get(var0))).map((Function)(var3)).orElse(Integer.valueOf(-1)))).intValue();
	}
	public static CompoundTag abilityNBT(LivingEntity var0, IAbility var1) {
		CompoundTag var2 = new CompoundTag();
		var1.save((HolderLookup.Provider)(var0.registryAccess()),var2);
		return var2;
	}
	public static <P extends IPacket<?>> StreamCodec<FriendlyByteBuf, P> defaultStreamCodec(P var0) {
		StreamEncoder var1 = (l0$sam, l1$sam) -> {
			FriendlyByteBuf l0 = ((FriendlyByteBuf)(l0$sam));
			IPacket l1 = ((IPacket)(l1$sam));

		};
		P var2 = var0;
		final P var2_f1 = var2;
		StreamDecoder var3 = (l0$sam) -> {
			FriendlyByteBuf l0 = ((FriendlyByteBuf)(l0$sam));

			return var2_f1;
		};
		return StreamCodec.of(var1,var3);
	}
}
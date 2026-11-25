package xyz.pixelatedw.mineminenomi.packets.server;

import com.mojang.datafixers.util.Function5;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$State;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.data.entity.AnimationData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SToggleAnimationPacket(int entityId, ResourceLocation animId, int stateId, int duration, boolean forced) {
	// Fields
	public static final CustomPacketPayload.Type<SToggleAnimationPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_toggle_animation"));
	public static final StreamCodec<FriendlyByteBuf, SToggleAnimationPacket> STREAM_CODEC;

	private SToggleAnimationPacket(LivingEntity var1, @Nullable() AnimationId<?> var2, Animation$State var3, int var4, boolean var5) {
		this(var1.getId(),(ResourceLocation)(((var2) != (null)) ? (var2.getId()) : (null)),var3.ordinal(),var4,var5);
	}
	public static SToggleAnimationPacket playAnimation(LivingEntity var0, AnimationId<?> var1, int var2, boolean var3) {
		return new SToggleAnimationPacket(var0,var1,Animation$State.PLAY,var2,var3);
	}
	public static SToggleAnimationPacket stopAnimation(LivingEntity var0, AnimationId<?> var1) {
		return new SToggleAnimationPacket(var0,var1,Animation$State.STOP,0,false);
	}
	public void handle(ClientPayloadContext var1) {
		Entity var2 = Minecraft.getInstance().level.getEntity(this.entityId);
		if (((var2) != (null)) && (var2 instanceof LivingEntity)){
			LivingEntity var3 = ((LivingEntity)(var2));
			AnimationData var4 = ((AnimationData)(((java.util.Optional)(AnimationData.get(var3))).orElse((Object)(null))));
			if ((var4) == (null)){
				return;
			}else{
				Animation$State var5 = Animation$State.values()[this.stateId];
				AnimationId var6 = AnimationId.getRegisteredId(this.animId);
				if ((var6) == (null)){
					return;
				}else{
					if ((var5) == (Animation$State.PLAY)){
						var4.startAnimation(var6,this.duration,this.forced);
					}else{
						if ((var5) == (Animation$State.STOP)){
							var4.stopAnimation(var6);
						}else{
							return;
						}
					}
					return;
				}
			}
		}else{
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SToggleAnimationPacket, Integer> var0 = SToggleAnimationPacket::entityId;
		Function<SToggleAnimationPacket, ResourceLocation> var1 = SToggleAnimationPacket::animId;
		Function<SToggleAnimationPacket, Integer> var2 = SToggleAnimationPacket::stateId;
		Function<SToggleAnimationPacket, Integer> var3 = SToggleAnimationPacket::duration;
		Function<SToggleAnimationPacket, Boolean> var4 = SToggleAnimationPacket::forced;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ResourceLocation.STREAM_CODEC,var1,ByteBufCodecs.INT,var2,ByteBufCodecs.INT,var3,ByteBufCodecs.BOOL,var4,(Function5)(((com.mojang.datafixers.util.Function5)(SToggleAnimationPacket::new)))));
	}
}
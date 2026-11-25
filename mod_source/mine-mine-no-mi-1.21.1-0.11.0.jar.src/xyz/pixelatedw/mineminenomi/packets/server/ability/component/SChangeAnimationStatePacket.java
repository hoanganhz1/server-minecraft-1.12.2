package xyz.pixelatedw.mineminenomi.packets.server.ability.component;

import com.mojang.datafixers.util.Function5;
import java.util.Optional;
import java.util.function.Consumer;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$State;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.AnimationData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SChangeAnimationStatePacket(int entityId, ResourceLocation abilityId, ResourceLocation animId, int stateId, int animDuration) {
	// Fields
	private static final ResourceLocation EMPTY = ResourceLocation.fromNamespaceAndPath("mineminenomi","");
	public static final CustomPacketPayload.Type<SChangeAnimationStatePacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_change_animation_state"));
	public static final StreamCodec<FriendlyByteBuf, SChangeAnimationStatePacket> STREAM_CODEC;

	public SChangeAnimationStatePacket(LivingEntity var1, IAbility var2, AnimationId<?> var3, Animation$State var4, int var5) {
		this(var1.getId(),(ResourceLocation)(var2.getCore().getRegistryKey()),(ResourceLocation)(var3.getId()),var4.ordinal(),var5);
	}
	public void handle(ClientPayloadContext var1) {
		Animation$State var10 = null;
		Entity var2 = Minecraft.getInstance().level.getEntity(this.entityId);
		if (((var2) != (null)) && (var2 instanceof LivingEntity)){
			AbilityCore var3 = AbilityCore.get(this.abilityId);
			if ((var3) == (null)){
				return;
			}else{
				LivingEntity var4 = ((LivingEntity)(var2));
				AnimationData var5 = ((AnimationData)(((java.util.Optional)(AnimationData.get(var4))).orElse((Object)(null))));
				AbilityData var6 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var4))).orElse((Object)(null))));
				if (((var5) != (null)) && ((var6) != (null))){
					IAbility var7 = var6.getEquippedAbility(var3);
					if ((var7) == (null)){
						var7 = var6.getPassiveAbility(var3);
						if ((var7) == (null)){
							return;
						}
					}
					Animation$State var8 = Animation$State.values()[this.stateId];
					SChangeAnimationStatePacket var9 = this;
					var10 = var8;
					LivingEntity var11 = var4;
					AnimationData var12 = var5;
					final Animation$State var10_f1 = var10;
					final LivingEntity var11_f1 = var11;
					final AnimationData var12_f2 = var12;
					Consumer<AnimationComponent> var13 = (l0) -> {
			AnimationId lv1_5 = AnimationId.getRegisteredId(this.animId);
			if ((lv1_5) == (null)){
				String lv1_6 = this.animId.toString();
				String lv1_7 = "Missing animation with id " + lv1_6;
				throw new RuntimeException((String)(lv1_7));
			}else{
				if (!((var10_f1.equals(Animation$State.PLAY)) && (((this.animId) != (null)) && (!(this.animId.equals(EMPTY)))))){
					if (!(var10_f1.equals(Animation$State.STOP))){
						if (var10_f1.equals(Animation$State.PAUSE)){
							if (l0.isPaused()){
								l0.resume(var11_f1);
								var12_f2.startAnimation(lv1_5,this.animDuration,false);
							}else{
								l0.pause(var11_f1);
								var12_f2.stopAnimation(lv1_5);
							}
						}else{
							return;
						}
					}else{
						l0.stop(var11_f1);
						var12_f2.stopAnimation(lv1_5);
					}
				}else{
					l0.start(var11_f1,lv1_5,this.animDuration);
					var12_f2.startAnimation(lv1_5,this.animDuration,false);
				}
				return;
			}
		};
					((java.util.Optional)(var7.getComponent(((AbilityComponentKey)(ModAbilityComponents.ANIMATION.get()))))).ifPresent((Consumer)(var13));
					return;
				}else{
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
		Function<SChangeAnimationStatePacket, Integer> var0 = SChangeAnimationStatePacket::entityId;
		Function<SChangeAnimationStatePacket, ResourceLocation> var1 = SChangeAnimationStatePacket::abilityId;
		Function<SChangeAnimationStatePacket, ResourceLocation> var2 = SChangeAnimationStatePacket::animId;
		Function<SChangeAnimationStatePacket, Integer> var3 = SChangeAnimationStatePacket::stateId;
		Function<SChangeAnimationStatePacket, Integer> var4 = SChangeAnimationStatePacket::animDuration;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ResourceLocation.STREAM_CODEC,var1,ResourceLocation.STREAM_CODEC,var2,ByteBufCodecs.INT,var3,ByteBufCodecs.INT,var4,(Function5)(((com.mojang.datafixers.util.Function5)(SChangeAnimationStatePacket::new)))));
	}
}
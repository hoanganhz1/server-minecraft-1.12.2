package xyz.pixelatedw.mineminenomi.packets.server;

import com.mojang.datafixers.util.Function6;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public final record SSpawnParticleEffectPacket(int spawnerId, ResourceLocation id, double posX, double posY, double posZ, CompoundTag nbt) {
	// Fields
	public static final CustomPacketPayload.Type<SSpawnParticleEffectPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_spawn_particle_effect"));
	public static final StreamCodec<FriendlyByteBuf, SSpawnParticleEffectPacket> STREAM_CODEC;

	public SSpawnParticleEffectPacket(ParticleEffect<?> var1, Entity var2, double var3, double var4, double var5, @Nullable() ParticleEffect$Details var6) {
		this(var2.getId(),(ResourceLocation)(((net.minecraft.core.Registry)(WyRegistry.PARTICLE_EFFECTS)).getKey((Object)(var1))),var3,var4,var5,(CompoundTag)(setTag(var6)));
	}
	private static CompoundTag setTag(@Nullable() ParticleEffect$Details var0) {
		CompoundTag var1 = new CompoundTag();
		if ((var0) != (null)){
			var0.save(var1);
		}
		return var1;
	}
	public void handle(ClientPayloadContext var1) {
		LocalPlayer var2 = Minecraft.getInstance().player;
		if ((var2) == (null)){
			return;
		}else{
			Entity var3 = var2.level().getEntity(this.spawnerId);
			ParticleEffect var4 = ((ParticleEffect)(WyRegistry.PARTICLE_EFFECTS.get(this.id)));
			if (((var4) != (null)) && ((var3) != (null))){
				ParticleEffect$Details var5 = var4.createDetails();
				if ((this.nbt) != (null)){
					var5.load(this.nbt);
				}
				if (var4.canParticlesSpawn(var2.level())){
					var4.spawn(var3,var3.level(),this.posX,this.posY,this.posZ,var5);
				}
				return;
			}else{
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SSpawnParticleEffectPacket, Integer> var0 = SSpawnParticleEffectPacket::spawnerId;
		Function<SSpawnParticleEffectPacket, ResourceLocation> var1 = SSpawnParticleEffectPacket::id;
		Function<SSpawnParticleEffectPacket, Double> var2 = SSpawnParticleEffectPacket::posX;
		Function<SSpawnParticleEffectPacket, Double> var3 = SSpawnParticleEffectPacket::posY;
		Function<SSpawnParticleEffectPacket, Double> var4 = SSpawnParticleEffectPacket::posZ;
		Function<SSpawnParticleEffectPacket, CompoundTag> var5 = SSpawnParticleEffectPacket::nbt;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ResourceLocation.STREAM_CODEC,var1,ByteBufCodecs.DOUBLE,var2,ByteBufCodecs.DOUBLE,var3,ByteBufCodecs.DOUBLE,var4,ByteBufCodecs.COMPOUND_TAG,var5,(Function6)(((com.mojang.datafixers.util.Function6)(SSpawnParticleEffectPacket::new)))));
	}
}
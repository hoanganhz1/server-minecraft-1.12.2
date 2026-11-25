package xyz.pixelatedw.mineminenomi.api.items.components;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

public final record Soulbound(UUID id, String displayName) {
	// Fields
	public static final Codec<Soulbound> CODEC;

	public Soulbound(LivingEntity var1) {
		this(var1.getUUID(),var1.getDisplayName().getString());
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			return l0.group((App)(UUIDUtil.CODEC.fieldOf("id").forGetter((Function)(((Function<Soulbound, UUID>)(Soulbound::id))))),(App)(Codec.STRING.fieldOf("name").forGetter((Function)(((Function<Soulbound, String>)(Soulbound::displayName)))))).apply((Applicative)(l0),(BiFunction)(((BiFunction<UUID, String, Soulbound>)(Soulbound::new))));
		};
		CODEC = (Codec) (RecordCodecBuilder.create(var0));
	}
}
package xyz.pixelatedw.mineminenomi.api.items.components;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.core.UUIDUtil;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;

public final record FlagData(UUID owner, Faction faction, Optional<JollyRoger> jollyRoger) {
	// Fields
	public static final Codec<FlagData> CODEC;

	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			return l0.group((App)(UUIDUtil.CODEC.fieldOf("owner").forGetter((Function)(((Function<FlagData, UUID>)(FlagData::owner))))),(App)(Faction.CODEC.fieldOf("faction").forGetter((Function)(((Function<FlagData, Faction>)(FlagData::faction))))),(App)(JollyRoger.CODEC.optionalFieldOf("jollyRoger").forGetter((Function)(((Function<FlagData, Optional>)(FlagData::jollyRoger)))))).apply((Applicative)(l0),(Function3)(((com.mojang.datafixers.util.Function3)(FlagData::new))));
		};
		CODEC = (Codec) (RecordCodecBuilder.create(var0));
	}
}
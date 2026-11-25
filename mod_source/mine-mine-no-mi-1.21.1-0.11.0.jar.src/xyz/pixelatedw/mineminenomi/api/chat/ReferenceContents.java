package xyz.pixelatedw.mineminenomi.api.chat;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

@Deprecated()
public final record ReferenceContents(ResourceLocation registry, ResourceLocation id) {
	// Fields
	@Deprecated() public static final MapCodec<ReferenceContents> CODEC;
	@Deprecated() public static final ComponentContents.Type<ReferenceContents> TYPE;

	@Deprecated()
	public MutableComponent resolve(@Nullable() CommandSourceStack var1, @Nullable() Entity var2, int var3) throws CommandSyntaxException {
		if ((var1) != (null)){

		}
		return Component.empty();
	}
	@Deprecated()
	public String toString() {
		String var1 = String.valueOf((Object)(this.registry));
		String var2 = String.valueOf((Object)(this.id));
		String var3 = "reference{" + var1 + "/" + var2 + "}";
		return var3;
	}
	@Deprecated()
	public ComponentContents.Type<?> type() {
		return (ComponentContents.Type<?>) (ComponentContents.Type) (TYPE);
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			return l0.group((App)(ResourceLocation.CODEC.fieldOf("registry").forGetter((Function)(((Function<ReferenceContents, ResourceLocation>)(ReferenceContents::registry))))),(App)(ResourceLocation.CODEC.fieldOf("id").forGetter((Function)(((Function<ReferenceContents, ResourceLocation>)(ReferenceContents::id)))))).apply((Applicative)(l0),(BiFunction)(((BiFunction<ResourceLocation, ResourceLocation, ReferenceContents>)(ReferenceContents::new))));
		};
		CODEC = (MapCodec) (RecordCodecBuilder.mapCodec(var0));
		TYPE = (ComponentContents.Type) (new ComponentContents.Type(CODEC,"references"));
	}
}
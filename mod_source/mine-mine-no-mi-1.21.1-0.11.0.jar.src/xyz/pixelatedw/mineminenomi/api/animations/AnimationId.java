package xyz.pixelatedw.mineminenomi.api.animations;

import java.util.HashMap;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class AnimationId<A extends Animation<?, ?>> {
	// Fields
	public static final HashMap<ResourceLocation, AnimationId<?>> REGISTERED_ANIMATIONS = new HashMap();
	private ResourceLocation id;
	private AnimationId$IAnimationFactory<A> factory;

	public AnimationId(ResourceLocation var1, AnimationId$IAnimationFactory<A> var2) {
		this.id = var1;
		this.factory = var2;
		REGISTERED_ANIMATIONS.put(var1,this);
	}
	@Nullable()
	public static <A extends Animation<?, ?>> @Nullable() A getRegisteredAnimation(AnimationId<A> var0) {
		return (A) (((xyz.pixelatedw.mineminenomi.api.animations.AnimationId$IAnimationFactory)(var0.factory)).createAnimation((AnimationId)(var0)));
	}
	@Nullable()
	public static <A extends Animation<?, ?>> @Nullable() AnimationId<A> getRegisteredId(ResourceLocation var0) {
		return ((AnimationId)(REGISTERED_ANIMATIONS.get(var0)));
	}
	public ResourceLocation getId() {
		return this.id;
	}
}
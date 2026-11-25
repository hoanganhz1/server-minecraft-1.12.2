package xyz.pixelatedw.mineminenomi.api.animations;

@FunctionalInterface()
public interface AnimationId$IAnimationFactory<A extends Animation<?, ?>> {
	public abstract A createAnimation(AnimationId<A> var0);
}
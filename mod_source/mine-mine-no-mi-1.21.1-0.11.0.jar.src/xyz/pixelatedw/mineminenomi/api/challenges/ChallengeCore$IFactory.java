package xyz.pixelatedw.mineminenomi.api.challenges;

@FunctionalInterface()
public interface ChallengeCore$IFactory<A extends Challenge> {
	public abstract A create(ChallengeCore<A> var0);
}
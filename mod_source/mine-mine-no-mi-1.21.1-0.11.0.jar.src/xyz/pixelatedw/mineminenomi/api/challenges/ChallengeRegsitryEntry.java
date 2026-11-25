package xyz.pixelatedw.mineminenomi.api.challenges;

@FunctionalInterface()
public interface ChallengeRegsitryEntry<T extends Object> {
	public abstract T get(String var0);
}
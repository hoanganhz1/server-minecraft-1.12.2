package xyz.pixelatedw.mineminenomi.api.effects;

public interface IWeakenedByHaoshokuEffect {
	public abstract boolean isWeakenedByHaoshoku();
	public default int haoshokuReductionTicks() {
		return 100;
	}
}
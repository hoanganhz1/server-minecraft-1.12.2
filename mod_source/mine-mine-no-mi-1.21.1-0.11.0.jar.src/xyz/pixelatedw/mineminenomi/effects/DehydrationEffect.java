package xyz.pixelatedw.mineminenomi.effects;

public class DehydrationEffect extends HungerOverTimeEffect {
	public DehydrationEffect() {
		super(1F,30);
	}
	public boolean isRemoveable() {
		return false;
	}
}
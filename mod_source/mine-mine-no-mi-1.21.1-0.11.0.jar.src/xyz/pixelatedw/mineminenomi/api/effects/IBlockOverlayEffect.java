package xyz.pixelatedw.mineminenomi.api.effects;

import net.minecraft.world.level.block.Block;

public interface IBlockOverlayEffect {
	public abstract Block getBlockOverlay(int var0, int var1);
	public default float[] getOverlayColor() {
		float[] var1 = new float[]{1F,1F,1F,1F};
		return var1;
	}
}
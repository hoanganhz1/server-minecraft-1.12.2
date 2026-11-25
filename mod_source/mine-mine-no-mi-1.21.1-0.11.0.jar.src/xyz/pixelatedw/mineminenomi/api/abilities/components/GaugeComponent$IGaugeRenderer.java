package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

@FunctionalInterface()
public interface GaugeComponent$IGaugeRenderer<A extends IAbility> {
	@OnlyIn(value=Dist.CLIENT)
	public abstract void renderGauge(Player var0, GuiGraphics var1, int var2, int var3, A var4);
}
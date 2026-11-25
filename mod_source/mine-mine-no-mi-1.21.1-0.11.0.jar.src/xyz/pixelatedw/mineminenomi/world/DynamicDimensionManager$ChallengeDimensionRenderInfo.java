package xyz.pixelatedw.mineminenomi.world;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class DynamicDimensionManager$ChallengeDimensionRenderInfo extends DimensionSpecialEffects {
	public DynamicDimensionManager$ChallengeDimensionRenderInfo() {
		super((0.0F/0.0F),false,DimensionSpecialEffects.SkyType.NORMAL,false,false);
	}
	public Vec3 getBrightnessDependentFogColor(Vec3 var1, float var2) {
		return var1.multiply((double)(((var2) * (0.94F)) + (0.06F)),(double)(((var2) * (0.94F)) + (0.06F)),(double)(((var2) * (0.91F)) + (0.09F)));
	}
	public boolean isFoggyAt(int var1, int var2) {
		return false;
	}
}
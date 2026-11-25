package xyz.pixelatedw.mineminenomi.morphs.saraaxolotl;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.saraaxolotl.AxolotlHeavyPointAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class AxolotlHeavyMorphInfo extends MorphInfo {
	// Fields
	private static final EntityDimensions STANDING_SIZE = EntityDimensions.scalable(2.5F,4F);
	private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.scalable(2.5F,3.8F);
	private static final ResourceLocation[] TEXTURES;

	@Nullable()
	public ResourceLocation getTexture(LivingEntity var1) {
		long var2 = var1.getUUID().getMostSignificantBits();
		String var3 = "" + var2;
		String[] var4 = var3.split("");
		int var5 = 0;
		String[] var6 = var4;
		int var7 = var6.length;
		int var8 = 0;
		do{
			if ((var8) < (var7)){
				String var9 = var6[var8];
				if (var9.equalsIgnoreCase("-")){

				}else{
					var5 = (var5) + (Integer.parseInt(var9));
				}
				var8++;
				continue;
			}else{
				break;
			}
		} while (true);
		int var9_1 = 4;
		var5 = Mth.clamp((var5) & (var9_1),0,var9_1);
		return TEXTURES[var5];
	}
	@OnlyIn(value=Dist.CLIENT)
	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
		var3.scale(1.6F,1.6F,1.6F);
	}
	public Component getDisplayName() {
		return ((AbilityCore)(AxolotlHeavyPointAbility.INSTANCE.get())).getLocalizedName();
	}
	public float getEyeHeight(LivingEntity var1) {
		return 3.7F;
	}
	public float getShadowSize() {
		return 1.8F;
	}
	public boolean canMount() {
		return false;
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean hasEqualDepthTest() {
		return true;
	}
	public Map<Pose, EntityDimensions> getSizes() {
		return ImmutableMap.builder().put(Pose.STANDING,STANDING_SIZE).put(Pose.CROUCHING,CROUCHING_SIZE).build();
	}
	static  {
		ResourceLocation[] var0 = new ResourceLocation[]{ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/axolotl_heavy_0.png"),ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/axolotl_heavy_1.png"),ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/axolotl_heavy_2.png"),ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/axolotl_heavy_3.png"),ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/axolotl_heavy_4.png")};
		TEXTURES = var0;
	}
}
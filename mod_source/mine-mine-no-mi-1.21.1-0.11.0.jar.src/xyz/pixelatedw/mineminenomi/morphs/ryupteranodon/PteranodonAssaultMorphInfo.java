package xyz.pixelatedw.mineminenomi.morphs.ryupteranodon;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.ryupteranodon.PteranodonAssaultPointAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class PteranodonAssaultMorphInfo extends MorphInfo {
	// Fields
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/pteranodon_assault.png");

	@OnlyIn(value=Dist.CLIENT)
	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
		Iterator var5 = RendererHelper.getArmPartsFrom(var2.getModel(),HumanoidArm.RIGHT).iterator();
		do{
			if (var5.hasNext()){
				ModelPart var6 = ((ModelPart)(var5.next()));
				var6.visible = false;
				continue;
			}else{
				break;
			}
		} while (true);
		Iterator var6_1 = RendererHelper.getArmPartsFrom(var2.getModel(),HumanoidArm.LEFT).iterator();
		do{
			if (var6_1.hasNext()){
				ModelPart var7 = ((ModelPart)(var6_1.next()));
				var7.visible = false;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public ResourceLocation getTexture(LivingEntity var1) {
		return TEXTURE;
	}
	public Component getDisplayName() {
		return ((AbilityCore)(PteranodonAssaultPointAbility.INSTANCE.get())).getLocalizedName();
	}
	public boolean isPartial() {
		return true;
	}
}
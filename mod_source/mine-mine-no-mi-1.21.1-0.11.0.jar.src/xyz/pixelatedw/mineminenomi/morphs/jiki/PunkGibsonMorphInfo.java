package xyz.pixelatedw.mineminenomi.morphs.jiki;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.jiki.DamnedPunkAbility;
import xyz.pixelatedw.mineminenomi.abilities.jiki.PunkGibsonAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class PunkGibsonMorphInfo extends MorphInfo {
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
	}
	public boolean canRender(LivingEntity var1) {
		Function<AbilityData, DamnedPunkAbility> var2 = (l0) -> {
			return ((DamnedPunkAbility)(l0.getEquippedAbility(((AbilityCore)(DamnedPunkAbility.INSTANCE.get())))));
		};
		Function<DamnedPunkAbility, Boolean> var3 = (l0) -> {
			return Boolean.valueOf(l0.isContinuous());
		};
		return !(((Boolean)(((java.util.Optional)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var2)))).map((Function)(var3)).orElse(Boolean.valueOf(false)))).booleanValue());
	}
	public Component getDisplayName() {
		return ((AbilityCore)(PunkGibsonAbility.INSTANCE.get())).getLocalizedName();
	}
	@Nullable()
	public ResourceLocation getTexture(LivingEntity var1) {
		return ModResources.PUNK_TEXTURE;
	}
	public boolean isPartial() {
		return true;
	}
}
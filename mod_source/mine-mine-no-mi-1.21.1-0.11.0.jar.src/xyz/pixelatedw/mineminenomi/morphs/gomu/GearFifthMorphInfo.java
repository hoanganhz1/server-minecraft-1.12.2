package xyz.pixelatedw.mineminenomi.morphs.gomu;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.gomu.GearFifthAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class GearFifthMorphInfo extends MorphInfo {
	@OnlyIn(value=Dist.CLIENT)
	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
		if ((((var1.isSprinting()) && ((var1.getDeltaMovement().length()) > (0.05000000074505806D))) ? (1) : (0)) != (0)){
			Iterator var5 = RendererHelper.getLegPartsFrom(var2.getModel(),HumanoidArm.RIGHT).iterator();
			do{
				if (var5.hasNext()){
					ModelPart var6 = ((ModelPart)(var5.next()));
					var6.visible = false;
					continue;
				}else{
					break;
				}
			} while (true);
			Iterator var6_1 = RendererHelper.getLegPartsFrom(var2.getModel(),HumanoidArm.LEFT).iterator();
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
	}
	@Nullable()
	public @Nullable() ResourceLocation getTexture(LivingEntity var1) {
		if (var1 instanceof AbstractClientPlayer){
			return ((AbstractClientPlayer)(var1)).getSkin().texture();
		}else{
			return null;
		}
	}
	public Component getDisplayName() {
		return ((AbilityCore)(GearFifthAbility.INSTANCE.get())).getLocalizedName();
	}
	public boolean isPartial() {
		return true;
	}
}
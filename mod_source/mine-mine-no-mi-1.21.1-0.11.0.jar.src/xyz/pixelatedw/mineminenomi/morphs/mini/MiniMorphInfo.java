package xyz.pixelatedw.mineminenomi.morphs.mini;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.mini.MiniMiniAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.PaperFloatLayer;

public class MiniMorphInfo extends MorphInfo {
	// Fields
	private static final EntityDimensions STANDING_SIZE = EntityDimensions.scalable(0.2F,0.5F);
	private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.scalable(0.2F,0.39F);

	@OnlyIn(value=Dist.CLIENT)
	public <T extends LivingEntity, M extends EntityModel<T>> void addLayers(EntityRendererProvider.Context var1, LivingEntityRenderer<T, M> var2) {
		var2.addLayer((RenderLayer)(new PaperFloatLayer(var1,var2)));
	}
	public ResourceLocation getTexture(LivingEntity var1) {
		if (var1 instanceof AbstractClientPlayer){
			return ((AbstractClientPlayer)(var1)).getSkin().texture();
		}else{
			return null;
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
		var3.scale(0.25F,0.25F,0.25F);
	}
	@OnlyIn(value=Dist.CLIENT)
	public void preFirstPersonRenderCallback(LivingEntity var1, PoseStack var2) {
		var2.translate(-0F,0.35F,0.2F);
		var2.scale(0.6F,0.6F,0.6F);
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean shouldRenderLayer(RenderLayer<?, ?> var1, LivingEntity var2, PoseStack var3, float var4) {
		if (!(var1 instanceof ItemInHandLayer)){
			return true;
		}else{
			int var5 = (((var2.getMainHandItem().getItem()) == (Items.PAPER)) || ((var2.getOffhandItem().getItem()) == (Items.PAPER))) ? (1) : (0);
			int var6 = ((!(var2.onGround())) && ((var2.getDeltaMovement().y) < (0D))) ? (1) : (0);
			if (((var5) != (0)) && ((var6) != (0))){
				Function<AbilityData, MiniMiniAbility> var7 = (l0) -> {
			return ((MiniMiniAbility)(l0.getEquippedAbility(((AbilityCore)(MiniMiniAbility.INSTANCE.get())))));
		};
				return !(((Boolean)(((java.util.Optional)(AbilityData.get(var2))).map((Function)(var7)).map((Function)(((Function<MiniMiniAbility, Boolean>)(Ability::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue());
			}else{
				return true;
			}
		}
	}
	public Component getDisplayName() {
		return ((AbilityCore)(MiniMiniAbility.INSTANCE.get())).getLocalizedName();
	}
	public float getEyeHeight(LivingEntity var1) {
		return 0.42F;
	}
	public float getShadowSize() {
		return 0.2F;
	}
	@OnlyIn(value=Dist.CLIENT)
	public double getCameraZoom(LivingEntity var1) {
		return 2.5D;
	}
	@OnlyIn(value=Dist.CLIENT)
	public double getCameraHeight(LivingEntity var1) {
		int var2 = ((Minecraft.getInstance().options.getCameraType()) == (CameraType.FIRST_PERSON)) ? (1) : (0);
		int var3 = ((var1.isPassenger()) && (((var1.getVehicle()) != (null)) && (var1.getVehicle().shouldRiderSit()))) ? (1) : (0);
		if (((var2) != (0)) && ((var3) != (0))){
			return 0.5D;
		}else{
			return 0D;
		}
	}
	public Map<Pose, EntityDimensions> getSizes() {
		return ImmutableMap.builder().put(Pose.STANDING,STANDING_SIZE).put(Pose.CROUCHING,CROUCHING_SIZE).build();
	}
}
package xyz.pixelatedw.mineminenomi.morphs.ushigiraffe;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.ushigiraffe.GiraffeWalkPointAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class GiraffeWalkMorphInfo extends MorphInfo {
	// Fields
	private static final EntityDimensions STANDING_SIZE = EntityDimensions.scalable(1.3F,5F);
	private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.scalable(1.3F,4F);
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/giraffe_walk.png");

	@Nullable()
	public @Nullable() ResourceLocation getTexture(LivingEntity var1) {
		return TEXTURE;
	}
	@OnlyIn(value=Dist.CLIENT)
	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
		var3.scale(2F,2F,2F);
	}
	public Component getDisplayName() {
		return ((AbilityCore)(GiraffeWalkPointAbility.INSTANCE.get())).getLocalizedName();
	}
	public float getEyeHeight(LivingEntity var1) {
		if (var1.isCrouching()){
			return 3.5F;
		}else{
			return 4.9F;
		}
	}
	public float getShadowSize() {
		return 1.2F;
	}
	public boolean canMount() {
		return false;
	}
	public void positionRider(LivingEntity var1, Entity var2) {
		double var3 = (var1.getY()) + ((double)((var1.getDimensions(var1.getPose()).height()) * (0.4F)));
		var2.setPos(var1.getX(),var3,var1.getZ());
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean hasCulling() {
		return true;
	}
	public Map<Pose, EntityDimensions> getSizes() {
		return ImmutableMap.builder().put(Pose.STANDING,STANDING_SIZE).put(Pose.CROUCHING,CROUCHING_SIZE).build();
	}
}
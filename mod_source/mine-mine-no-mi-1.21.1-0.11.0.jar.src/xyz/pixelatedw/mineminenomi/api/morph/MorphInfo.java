package xyz.pixelatedw.mineminenomi.api.morph;

import com.mojang.blaze3d.vertex.PoseStack;
import io.netty.buffer.ByteBuf;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityEvent;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;

public abstract class MorphInfo {
	// Fields
	public static final StreamCodec<ByteBuf, MorphInfo> STREAM_CODEC;
	private ResourceLocation key;

	@OnlyIn(value=Dist.CLIENT)
	public <T extends LivingEntity, M extends EntityModel<T>> void addLayers(EntityRendererProvider.Context var1, LivingEntityRenderer<T, M> var2) {
	}
	public abstract Component getDisplayName();
	@Nullable()
	public @Nullable() ResourceLocation getTexture(LivingEntity var1) {
		return null;
	}
	public boolean isPartial() {
		return false;
	}
	public boolean canRender(LivingEntity var1) {
		return true;
	}
	@OnlyIn(value=Dist.CLIENT)
	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
	}
	@OnlyIn(value=Dist.CLIENT)
	public void postRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
	}
	@OnlyIn(value=Dist.CLIENT)
	public void armorVisibilityCallback(LivingEntity var1, Model var2, EquipmentSlot var3) {
	}
	@OnlyIn(value=Dist.CLIENT)
	public void preFirstPersonRenderCallback(LivingEntity var1, PoseStack var2) {
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean shouldRenderLayer(RenderLayer<?, ?> var1, LivingEntity var2, PoseStack var3, float var4) {
		return true;
	}
	@Nullable()
	public @Nullable() Map<Pose, EntityDimensions> getSizes() {
		return (Map<Pose, EntityDimensions>) (Map) (null);
	}
	public EntityDimensions getSize(LivingEntity var1, Pose var2) {
		Map var3 = this.getSizes();
		if ((var3) != (null)){
			return ((EntityDimensions)(var3.get(var2)));
		}else{
			return var1.getDimensions(var2);
		}
	}
	public float getEyeHeight(LivingEntity var1) {
		return -1F;
	}
	public float getShadowSize() {
		return -1F;
	}
	public boolean canMount() {
		return true;
	}
	public void positionRider(LivingEntity var1, Entity var2) {
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean hasCulling() {
		return false;
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean hasEqualDepthTest() {
		return false;
	}
	@OnlyIn(value=Dist.CLIENT)
	public double getCameraZoom(LivingEntity var1) {
		return 0D;
	}
	@OnlyIn(value=Dist.CLIENT)
	public double getCameraHeight(LivingEntity var1) {
		return 0D;
	}
	@Nullable()
	public @Nullable() ResourceLocation getKey() {
		if ((this.key) == (null)){
			this.key = WyRegistry.MORPHS.getKey(this);
		}
		return this.key;
	}
	public void updateMorphSize(LivingEntity var1) {
		NeoForge.EVENT_BUS.post((Event)(new EntityEvent.Size((Entity)(var1),var1.getPose(),var1.getDimensions(var1.getPose()))));
		var1.refreshDimensions();
		var1.ejectPassengers();
	}
	static  {
		Function<ResourceLocation, MorphInfo> var0 = (l0) -> {
			return ((MorphInfo)(WyRegistry.MORPHS.get(l0)));
		};
		STREAM_CODEC = (StreamCodec) (ResourceLocation.STREAM_CODEC.map(var0,(Function)(((Function<MorphInfo, ResourceLocation>)(MorphInfo::getKey)))));
	}
	public boolean isActive(LivingEntity var1) {
		MorphInfo var2 = this;
		Function<DevilFruitData, Boolean> var3 = (l0) -> {
			if ((!(this.isPartial())) && ((l0.getCurrentMorph().isPresent()) && (((java.lang.Object)(((MorphInfo)(l0.getCurrentMorph().get())))).equals((Object)(this))))){
				return Boolean.valueOf(true);
			}else{
				return Boolean.valueOf((this.isPartial()) && (l0.hasMorphInQueue(this)));
			}
		};
		return ((Boolean)(((java.util.Optional)(DevilFruitData.get(var1))).map((Function)(var3)).orElse(Boolean.valueOf(false)))).booleanValue();
	}
}
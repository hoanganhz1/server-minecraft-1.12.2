package xyz.pixelatedw.mineminenomi.api.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AnimationData;

public abstract class Animation<E extends LivingEntity, M extends EntityModel<E>> {
	// Fields
	private long startTime;
	private long runTime;
	private int initialDuration;
	private int animDuration;
	private AnimationId<? extends Animation<E, M>> animId;
	private Animation$State state;
	private Animation$IAnimationPostAngles<E, M> animationPostAngles;
	private Animation$IAnimationAngles<E, M> animationAngles;
	private Animation$IAnimationHeldItem<E> animationHeldItem;
	private @Nullable() M model;

	public <A extends Animation<E, M>> Animation(AnimationId<A> var1) {
		this.state = Animation$State.STOP;
		this.animId = (AnimationId<? extends Animation<E, M>>) (var1);
	}
	public void setAnimationPostAngles(Animation$IAnimationPostAngles<E, M> var1) {
		this.animationPostAngles = var1;
	}
	public void setAnimationAngles(Animation$IAnimationAngles<E, M> var1) {
		this.animationAngles = var1;
	}
	public void setAnimationHeldItem(Animation$IAnimationHeldItem<E> var1) {
		this.animationHeldItem = var1;
	}
	public static <E extends LivingEntity, M extends EntityModel<E>> void animationPostAngles(E var0, M var1, PoseStack var2, MultiBufferSource var3, int var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		M var11 = var1;
		E var12 = var0;
		PoseStack var13 = var2;
		MultiBufferSource var14 = var3;
		int var15 = var4;
		float var16 = var5;
		float var17 = var6;
		float var18 = var7;
		float var19 = var8;
		float var20 = var9;
		float var21 = var10;
		final M var11_f1 = var11;
		final E var12_f2 = var12;
		final PoseStack var13_f3 = var13;
		final MultiBufferSource var14_f4 = var14;
		final int var15_f5 = var15;
		final float var16_f6 = var16;
		final float var17_f7 = var17;
		final float var18_f8 = var18;
		final float var19_f9 = var19;
		final float var20_f10 = var20;
		final float var21_f11 = var21;
		Consumer<AnimationData> var22 = (l0) -> {
			Animation lv1_12 = l0.getAnimation();
			if (((lv1_12) != (null)) && (lv1_12.isPlaying())){
				lv1_12.model = var11_f1;
				if ((lv1_12.animationPostAngles) != (null)){
					try{
						lv1_12.animationPostAngles.setAnimationPostAngles(var12_f2,var11_f1,var13_f3,var14_f4,var15_f5,var16_f6,var17_f7,var18_f8,var19_f9,var20_f10,var21_f11);
					}catch(Exception lv1_13){

					}
				}else{
					return;
				}
			}
		};
		((java.util.Optional)(AnimationData.get(var0))).ifPresent((Consumer)(var22));
	}
	public static <E extends LivingEntity, M extends EntityModel<E>> void animationAngles(E var0, M var1, float var2, float var3, float var4, float var5, float var6) {
		M var7 = var1;
		E var8 = var0;
		float var9 = var2;
		float var10 = var3;
		float var11 = var4;
		float var12 = var5;
		float var13 = var6;
		final M var7_f1 = var7;
		final E var8_f2 = var8;
		final float var9_f3 = var9;
		final float var10_f4 = var10;
		final float var11_f5 = var11;
		final float var12_f6 = var12;
		final float var13_f7 = var13;
		Consumer<AnimationData> var14 = (l0) -> {
			Animation lv2_8 = l0.getAnimation();
			if (((lv2_8) != (null)) && (lv2_8.isPlaying())){
				lv2_8.model = var7_f1;
				if ((lv2_8.animationAngles) != (null)){
					try{
						lv2_8.animationAngles.setAnimationAngles(var8_f2,var7_f1,var9_f3,var10_f4,var11_f5,var12_f6,var13_f7);
					}catch(Exception lv2_9){

					}
				}else{
					return;
				}
			}
		};
		((java.util.Optional)(AnimationData.get(var0))).ifPresent((Consumer)(var14));
		if (var1 instanceof HumanoidModel){
			HumanoidModel var15 = ((HumanoidModel)(var1));
			var15.hat.copyFrom(var15.head);
		}
	}
	public static <E extends LivingEntity> void animationHeldItem(E var0, ItemStack var1, ItemDisplayContext var2, HumanoidArm var3, PoseStack var4, MultiBufferSource var5, int var6) {
		E var7 = var0;
		ItemStack var8 = var1;
		ItemDisplayContext var9 = var2;
		HumanoidArm var10 = var3;
		PoseStack var11 = var4;
		MultiBufferSource var12 = var5;
		int var13 = var6;
		final E var7_f1 = var7;
		final ItemStack var8_f2 = var8;
		final ItemDisplayContext var9_f3 = var9;
		final HumanoidArm var10_f4 = var10;
		final PoseStack var11_f5 = var11;
		final MultiBufferSource var12_f6 = var12;
		final int var13_f7 = var13;
		Consumer<AnimationData> var14 = (l0) -> {
			Animation lv3_8 = l0.getAnimation();
			if (((lv3_8) != (null)) && ((lv3_8.isPlaying()) && ((lv3_8.animationHeldItem) != (null)))){
				try{
					lv3_8.animationHeldItem.setupHeldItem(var7_f1,var8_f2,var9_f3,var10_f4,var11_f5,var12_f6,var13_f7);
				}catch(Exception lv3_9){

				}
			}
		};
		((java.util.Optional)(AnimationData.get(var0))).ifPresent((Consumer)(var14));
	}
	public void start(LivingEntity var1, int var2) {
		if ((this.model) != (null)){
			RendererHelper.resetModelToDefaultPivots(this.model);
		}
		this.startTime = (long)(var1.tickCount);
		this.runTime = 0L;
		this.initialDuration = var2;
		this.animDuration = var2;
		this.state = Animation$State.PLAY;
	}
	public void stop(LivingEntity var1) {
		this.runTime = 0L;
		this.state = Animation$State.STOP;
		if ((this.model) != (null)){
			RendererHelper.resetModelToDefaultPivots(this.model);
		}
	}
	public void tick(LivingEntity var1) {
		if (Minecraft.getInstance().isPaused()){
			return;
		}else{
			if (this.isPlaying()){
				this.runTime = ((long)(var1.tickCount)) - (this.startTime);
				if (((this.animDuration) != (-1)) && ((this.animDuration) <= (0))){
					if (!(var1.level().isClientSide)){
						this.stop(var1);
					}else{
						return;
					}
				}else{
					if ((this.animDuration) != (-1)){
						this.animDuration = (this.animDuration) - (1);
					}else{
						return;
					}
				}
			}
			return;
		}
	}
	public M getModel() {
		return (M) (this.model);
	}
	public long getStartTime() {
		return this.startTime;
	}
	public long getTime() {
		return this.runTime;
	}
	public int getAnimationInitialDuration() {
		return this.initialDuration;
	}
	public int getAnimationDuration() {
		return this.animDuration;
	}
	public float getAnimationCompletion() {
		return Math.min(((float)(this.runTime)) / ((float)(this.initialDuration)),1F);
	}
	public Animation$State getState() {
		return this.state;
	}
	public boolean isPlaying() {
		return (this.state) == (Animation$State.PLAY);
	}
	public boolean isStopped() {
		return (this.state) == (Animation$State.STOP);
	}
	public AnimationId<? extends Animation<E, M>> getId() {
		return (AnimationId<? extends Animation<E, M>>) (AnimationId) (this.animId);
	}
}
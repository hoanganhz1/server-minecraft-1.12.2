package xyz.pixelatedw.mineminenomi.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.util.ObfuscationReflectionHelper;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.helpers.MorphsHelper$Client;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;

@Mixin(value={EntityRenderDispatcher.class})
public class EntityRenderDispatcherMixin {
	// Fields
	@Shadow() public Map<EntityType<?>, EntityRenderer<?>> renderers;
	@Shadow() private Map<PlayerSkin.Model, EntityRenderer<? extends Player>> playerRenderers;
	@Shadow() private boolean shouldRenderShadow;
	@Shadow() private Level level;
	@Shadow() private boolean renderHitBoxes;
	private static final Method RENDER_FLAME;
	private static final Method RENDER_SHADOW;
	private static final Method RENDER_HITBOX;

	@Inject(method={"renderShadow"}, at={@At(value="HEAD")}, cancellable=true)
	private static void mineminenomi$renderShadow(PoseStack var0, MultiBufferSource var1, Entity var2, float var3, float var4, LevelReader var5, float var6, CallbackInfo var7) {
		if ((var2 instanceof LivingEntity) && (!(((Boolean)(EntityStatsData.get(((LivingEntity)(var2))).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::hasShadow)))).orElse(Boolean.valueOf(false)))).booleanValue()))){
			var7.cancel();
			return;
		}else{
			return;
		}
	}
	@Inject(method={"shouldRender"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;getRenderer(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/client/renderer/entity/EntityRenderer;", shift=At.Shift.BEFORE)}, cancellable=true)
	public <E extends Entity> void mineminenomi$shouldRender(E var1, Frustum var2, double var3, double var4, double var5, CallbackInfoReturnable<Boolean> var6) {
		if (var1 instanceof LivingEntity){
			EntityRenderer var7 = MorphsHelper$Client.getMorphRenderer(((LivingEntity)(var1)));
			if (((var7) != (null)) && (var7.shouldRender(var1,var2,var3,var4,var5))){
				var6.setReturnValue(Boolean.valueOf(true));
			}else{
				return;
			}
		}
	}
	@Inject(method={"render"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;getRenderer(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/client/renderer/entity/EntityRenderer;", shift=At.Shift.BEFORE)}, cancellable=true)
	public <E extends Entity> void mineminenomi$render(E var1, double var2, double var3, double var4, float var5, float var6, PoseStack var7, MultiBufferSource var8, int var9, CallbackInfo var10) {
try {
		if (var1 instanceof LivingEntity){
			LivingEntity var11 = ((LivingEntity)(var1));
			EntityRenderDispatcher var12 = ((EntityRenderDispatcher)(this));
			EntityRenderer var13 = MorphsHelper$Client.getMorphRenderer(var11);
			if ((var13) != (null)){
				try{
					Vec3 var14 = var13.getRenderOffset(var1,var6);
					double var15 = (var2) + (var14.x());
					double var16 = (var3) + (var14.y());
					double var17 = (var4) + (var14.z());
					var7.pushPose();
					var7.translate(var15,var16,var17);
					var13.render(var1,var5,var6,var7,var8,var9);
					if (var1.displayFireAnimation()){
						Object[] var18 = new Object[]{var7,var8,var1};
						RENDER_FLAME.invoke(var12,var18);
					}
					float var18_1 = ((IEntityRendererMixin)(var13)).getShadowRadius();
					float var19 = ((IEntityRendererMixin)(var13)).getShadowStrength();
					var7.translate(-(var14.x()),-(var14.y()),-(var14.z()));
					if ((((Boolean)(var12.options.entityShadows().get())).booleanValue()) && ((this.shouldRenderShadow) && (((var18_1) > (0F)) && (!(var1.isInvisible()))))){
						float var20 = (float)(((1D) - ((var12.distanceToSqr(var1.getX(),var1.getY(),var1.getZ())) / (256D))) * ((double)(var19)));
						if ((var20) > (0F)){
							Object[] var21 = new Object[]{var7,var8,var1,Float.valueOf(var20),Float.valueOf(var6),this.level,Float.valueOf(Math.min(var18_1,32F))};
							RENDER_SHADOW.invoke(var12,var21);
						}
					}
					if ((this.renderHitBoxes) && ((!(var1.isInvisible())) && (!(Minecraft.getInstance().showOnlyReducedInfo())))){
						Object[] var20_1 = new Object[]{var7,var8.getBuffer(RenderType.lines()),var1,Float.valueOf(1F),Float.valueOf(1F),Float.valueOf(1F),Float.valueOf(1F)};
						RENDER_HITBOX.invoke(var12,var20_1);
					}
					var7.popPose();
				}catch(Throwable var14_2){
					CrashReport var14_1 = CrashReport.forThrowable(var14_2,"Rendering entity in world");
					var1.fillCrashReportCategory(var14_1.addCategory("Entity being rendered"));
					CrashReportCategory var15_1 = var14_1.addCategory("Renderer details");
					var15_1.setDetail("Assigned renderer",var13);
					var15_1.setDetail("Location",CrashReportCategory.formatLocation((LevelHeightAccessor)(this.level),var2,var3,var4));
					var15_1.setDetail("Rotation",Float.valueOf(var5));
					var15_1.setDetail("Delta",Float.valueOf(var6));
					throw new ReportedException(var14_1);
				}
				var10.cancel();
			}else{
				return;
			}
		}

} catch (java.lang.Throwable jdec$escape$0) {
throw jdec$rethrow$0(jdec$escape$0);
}
	}
	private <T extends Entity> EntityRenderer<? super T> getOriginalRenderer(T var1) {
		if (var1 instanceof AbstractClientPlayer){
			PlayerSkin.Model var2 = ((AbstractClientPlayer)(var1)).getSkin().model();
			EntityRenderer var3 = ((EntityRenderer)(this.playerRenderers.get(var2)));
			return (EntityRenderer<? super T>) (EntityRenderer) (((var3) != (null)) ? (var3) : (((EntityRenderer)(this.playerRenderers.get(PlayerSkin.Model.WIDE)))));
		}else{
			return (EntityRenderer<? super T>) (EntityRenderer) (((EntityRenderer)(this.renderers.get(var1.getType()))));
		}
	}
	static  {
		Class[] var0 = new Class[]{PoseStack.class,MultiBufferSource.class,Entity.class,Quaternionf.class};
		RENDER_FLAME = ObfuscationReflectionHelper.findMethod(EntityRenderDispatcher.class,"renderFlame",var0);
		Class[] var1 = new Class[]{PoseStack.class,MultiBufferSource.class,Entity.class,Float.TYPE,Float.TYPE,LevelReader.class,Float.TYPE};
		RENDER_SHADOW = ObfuscationReflectionHelper.findMethod(EntityRenderDispatcher.class,"renderShadow",var1);
		Class[] var2 = new Class[]{PoseStack.class,VertexConsumer.class,Entity.class,Float.TYPE,Float.TYPE,Float.TYPE,Float.TYPE};
		RENDER_HITBOX = ObfuscationReflectionHelper.findMethod(EntityRenderDispatcher.class,"renderHitbox",var2);
	}
	private static <E extends java.lang.Throwable> java.lang.RuntimeException jdec$rethrow$0(java.lang.Throwable failure) throws E {throw (E) failure;}
}
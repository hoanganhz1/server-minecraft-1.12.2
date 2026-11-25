package xyz.pixelatedw.mineminenomi.mixins.client;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.abilities.haki.KenbunshokuHakiAuraAbility;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.events.entity.OutlineColorEvent;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.handlers.protection.AbilityProtectionHandler$Client;
import xyz.pixelatedw.mineminenomi.handlers.world.ChallengesHandler$Client;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypeBuffers;
import xyz.pixelatedw.mineminenomi.renderers.buffers.HakiAuraBuffer;

@Mixin(value={LevelRenderer.class})
public abstract class LevelRendererMixin {
	// Fields
	@Shadow() @Final() private Minecraft minecraft;
	@Shadow() private ClientLevel level;
	@Unique() private Entity mineminenomi$currentRenderedEntity;

	@Shadow()
	public abstract boolean isSectionCompiled(BlockPos var0);
	@Inject(method={"initOutline"}, at={@At(value="TAIL")})
	public void mineminenomi$initBuffers(CallbackInfo var1) {
		ModRenderTypeBuffers.getInstance().initHakiAuraShader();
	}
	@Inject(method={"shouldShowEntityOutlines"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$shouldShowEntityOutlines(CallbackInfoReturnable<Boolean> var1) {
		if (((Minecraft.getInstance().levelRenderer.entityTarget()) != (null)) && (((ModRenderTypeBuffers.getInstance().getHakiAuraPostChain()) != (null)) && ((Minecraft.getInstance().player) != (null)))){
			var1.setReturnValue(Boolean.valueOf(true));
			return;
		}else{
			return;
		}
	}
	@Inject(method={"resize"}, at={@At(value="TAIL")})
	public void mineminenomi$resize(int var1, int var2, CallbackInfo var3) {
		if ((ModRenderTypeBuffers.getInstance()) == (null)){
			return;
		}else{
			if ((ModRenderTypeBuffers.getInstance().getHakiAuraPostChain()) != (null)){
				ModRenderTypeBuffers.getInstance().getHakiAuraPostChain().resize(var1,var2);
			}
			return;
		}
	}
	@Inject(method={"renderLevel"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/renderer/OutlineBufferSource;endOutlineBatch()V", shift=At.Shift.BEFORE)})
	public void mineminenomi$processShaders(DeltaTracker var1, boolean var2, Camera var3, GameRenderer var4, LightTexture var5, Matrix4f var6, Matrix4f var7, CallbackInfo var8) {
		LocalPlayer var9 = Minecraft.getInstance().player;
		ModRenderTypeBuffers.getInstance().getHakiAuraBuffer().endBatch();
		Function<AbilityData, KenbunshokuHakiAuraAbility> var10 = (l0) -> {
			return ((KenbunshokuHakiAuraAbility)(l0.getEquippedAbility(((AbilityCore)(KenbunshokuHakiAuraAbility.INSTANCE.get())))));
		};
		if (((((ModRenderTypeBuffers.getInstance().getHakiAuraPostChain()) != (null)) && (((var9) != (null)) && (((Boolean)(((java.util.Optional)(AbilityData.get((LivingEntity)(var9)))).map((Function)(var10)).map((Function)(((Function<KenbunshokuHakiAuraAbility, Boolean>)(Ability::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue()))) ? (1) : (0)) != (0)){
			ModRenderTypeBuffers.getInstance().getHakiAuraPostChain().process(var1.getGameTimeDeltaTicks());
			Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
		}
	}
	@Inject(method={"renderLevel"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/renderer/LevelRenderer;renderWorldBorder(Lnet/minecraft/client/Camera;)V", shift=At.Shift.AFTER)})
	private void mineminenomi$renderBounds(DeltaTracker var1, boolean var2, Camera var3, GameRenderer var4, LightTexture var5, Matrix4f var6, Matrix4f var7, CallbackInfo var8) {
		PoseStack var9 = new PoseStack();
		ChallengesHandler$Client.renderMainSkysphere(var9,var3);
		AbilityProtectionHandler$Client.renderAbilityProtections(var9,var3);
	}
	@Inject(method={"renderEntity"}, at={@At(value="HEAD")})
	public void mineminenomi$renderEntity(Entity var1, double var2, double var3, double var4, float var5, PoseStack var6, MultiBufferSource var7, CallbackInfo var8) {
		if (var7 instanceof OutlineBufferSource){
			OutlineBufferSource var9 = ((OutlineBufferSource)(var7));
			OutlineColorEvent var10 = ((OutlineColorEvent)(NeoForge.EVENT_BUS.post((Event)(new OutlineColorEvent(var1)))));
			var9.setColor(var10.getColor().getRed(),var10.getColor().getGreen(),var10.getColor().getBlue(),var10.getColor().getAlpha());
		}
	}
	@Inject(method={"renderClouds"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$renderClouds(PoseStack var1, Matrix4f var2, Matrix4f var3, float var4, double var5, double var6, double var7, CallbackInfo var8) {
		if (NuWorld.isChallengeDimension((Level)(this.level))){
			var8.cancel();
			return;
		}else{
			return;
		}
	}
	@ModifyVariable(method={"renderSnowAndRain"}, at=@At(value="STORE"), ordinal=1)
	private float mineminenomi$getRainLevel(float var1) {
		return var1;
	}
	@Redirect(method={"renderLevel"}, at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/Entity;blockPosition()Lnet/minecraft/core/BlockPos;"))
	private BlockPos mineminenomi$captureRenderedEntity(Entity var1) {
		this.mineminenomi$currentRenderedEntity = var1;
		return var1.blockPosition();
	}
	@Redirect(method={"renderLevel"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/LevelRenderer;isSectionCompiled(Lnet/minecraft/core/BlockPos;)Z"))
	private boolean mineminenomi$fuckCompiledChunks(LevelRenderer var1, BlockPos var2) {
		Entity var3 = this.mineminenomi$currentRenderedEntity;
		if ((var3 instanceof NuProjectileEntity) && (!(((NuProjectileEntity)(var3)).requiresCompiledRenderChunk()))){
			return true;
		}else{
			return this.isSectionCompiled(var2);
		}
	}
}
package xyz.pixelatedw.mineminenomi.handlers.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.joml.Matrix4f;
import xyz.pixelatedw.mineminenomi.abilities.haki.KenbunshokuHakiAuraAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.effects.IBlockOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IColorOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ITextureOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IVanishEffect;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class EffectsHandler$Client {
	public static void renderScreenEffects(Player var0, PoseStack var1, Camera var2, float var3) {
		Minecraft var4 = Minecraft.getInstance();
		if (!(var4.options.getCameraType().isFirstPerson())){
			return;
		}else{
			Matrix4f var5 = var1.last().pose();
			Iterator var6 = var0.getActiveEffects().iterator();
			do{
				if (var6.hasNext()){
					MobEffectInstance var7 = ((MobEffectInstance)(var6.next()));
					Holder var8 = var7.getEffect();
					if (var8 instanceof IColorOverlayEffect){
						Color var9 = ((IColorOverlayEffect)(var8)).getViewOverlayColor(var7.getDuration(),var7.getAmplifier());
						if ((var9) != (null)){
							var1.pushPose();
							RenderSystem.enableBlend();
							RenderSystem.setShader((Supplier)(((Supplier<ShaderInstance>)(GameRenderer::getPositionColorShader))));
							BufferBuilder var10 = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.POSITION_COLOR);
							var10.addVertex(var5,-1F,-1F,-0.5F).setColor(var9.getRed(),var9.getGreen(),var9.getBlue(),var9.getAlpha());
							var10.addVertex(var5,1F,-1F,-0.5F).setColor(var9.getRed(),var9.getGreen(),var9.getBlue(),var9.getAlpha());
							var10.addVertex(var5,1F,1F,-0.5F).setColor(var9.getRed(),var9.getGreen(),var9.getBlue(),var9.getAlpha());
							var10.addVertex(var5,-1F,1F,-0.5F).setColor(var9.getRed(),var9.getGreen(),var9.getBlue(),var9.getAlpha());
							BufferUploader.drawWithShader(var10.buildOrThrow());
							RenderSystem.disableBlend();
							var1.popPose();
						}
					}
					Holder var9_1 = var7.getEffect();
					if (var9_1 instanceof IBlockOverlayEffect){
						Block var10_1 = ((IBlockOverlayEffect)(var9_1)).getBlockOverlay(var7.getDuration(),var7.getAmplifier());
						if ((var10_1) != (null)){
							float[] var11 = ((IBlockOverlayEffect)(var7.getEffect())).getOverlayColor();
							var1.pushPose();
							TextureAtlasSprite var12 = var4.getBlockRenderer().getBlockModelShaper().getTexture(var10_1.defaultBlockState(),(Level)(var4.level),var0.blockPosition());
							RenderSystem.setShaderTexture(0,var12.atlasLocation());
							RenderSystem.setShader((Supplier)(((Supplier<ShaderInstance>)(GameRenderer::getPositionTexColorShader))));
							BufferBuilder var13 = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.POSITION_TEX_COLOR);
							RenderSystem.enableBlend();
							RenderSystem.defaultBlendFunc();
							float var14 = var12.getU0();
							float var15 = var12.getU1();
							float var16 = var12.getV0();
							float var17 = var12.getV1();
							var13.addVertex(var5,-1F,-1F,-0.5F).setUv(var15,var17).setColor(var11[0],var11[1],var11[2],var11[3]);
							var13.addVertex(var5,1F,-1F,-0.5F).setUv(var14,var17).setColor(var11[0],var11[1],var11[2],var11[3]);
							var13.addVertex(var5,1F,1F,-0.5F).setUv(var14,var16).setColor(var11[0],var11[1],var11[2],var11[3]);
							var13.addVertex(var5,-1F,1F,-0.5F).setUv(var15,var16).setColor(var11[0],var11[1],var11[2],var11[3]);
							BufferUploader.drawWithShader(var13.buildOrThrow());
							RenderSystem.disableBlend();
							var1.popPose();
						}
					}
					Holder var10_2 = var7.getEffect();
					if (var10_2 instanceof ITextureOverlayEffect){
						ITextureOverlayEffect var11_1 = ((ITextureOverlayEffect)(var10_2));
						ResourceLocation var12_1 = var11_1.getViewTexture(var7.getDuration(),var7.getAmplifier());
						if ((var12_1) != (null)){
							Color var13_1 = var11_1.getTextureOverlayColor();
							var1.pushPose();
							RenderSystem.enableBlend();
							RenderSystem.defaultBlendFunc();
							RenderSystem.setShaderTexture(0,var12_1);
							RenderSystem.setShader((Supplier)(((Supplier<ShaderInstance>)(GameRenderer::getPositionTexColorShader))));
							BufferBuilder var14_1 = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.POSITION_TEX_COLOR);
							var14_1.addVertex(var5,-1F,-1F,-0.5F).setUv(2F,2F).setColor(var13_1.getRed(),var13_1.getGreen(),var13_1.getBlue(),var13_1.getAlpha());
							var14_1.addVertex(var5,1F,-1F,-0.5F).setUv(0F,2F).setColor(var13_1.getRed(),var13_1.getGreen(),var13_1.getBlue(),var13_1.getAlpha());
							var14_1.addVertex(var5,1F,1F,-0.5F).setUv(0F,0F).setColor(var13_1.getRed(),var13_1.getGreen(),var13_1.getBlue(),var13_1.getAlpha());
							var14_1.addVertex(var5,-1F,1F,-0.5F).setUv(2F,0F).setColor(var13_1.getRed(),var13_1.getGreen(),var13_1.getBlue(),var13_1.getAlpha());
							BufferUploader.drawWithShader(var14_1.buildOrThrow());
							RenderSystem.disableBlend();
							var1.popPose();
							continue;
						}else{
							continue;
						}
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	public static float getDizzyCameraRoll(LocalPlayer var0) {
		if (var0.hasEffect((Holder)(ModEffects.DIZZY))){
			return (float)(Math.sin((double)((var0.tickCount) % (100))));
		}else{
			return 0F;
		}
	}
	public static boolean isInvisible(LivingEntity var0) {
		LocalPlayer var1 = Minecraft.getInstance().player;
		AbilityData var2 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var1)))).orElse((Object)(null))));
		int var3 = 0;
		if ((var2) != (null)){
			KenbunshokuHakiAuraAbility var4 = ((KenbunshokuHakiAuraAbility)(var2.getEquippedAbility(((AbilityCore)(KenbunshokuHakiAuraAbility.INSTANCE.get())))));
			var3 = (((var4) != (null)) && (var4.isContinuous())) ? (1) : (0);
		}
		Iterator var4_1 = var0.getActiveEffects().iterator();
		do{
			if (var4_1.hasNext()){
				MobEffectInstance var5 = ((MobEffectInstance)(var4_1.next()));
				if ((var5.getDuration()) <= (0)){
					var4_1.remove();
				}
				if ((var5.getEffect() instanceof IVanishEffect) && ((((IVanishEffect)(var5.getEffect())).isVanished(var0,var5.getDuration(),var5.getAmplifier())) && (!(((var3) != (0)) && ((var0) != (var1)))))){
					return true;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return false;
	}
}
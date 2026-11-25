package xyz.pixelatedw.mineminenomi.particles.rendertypes;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;

public class SimpleParticleRenderType implements ParticleRenderType {
	// Fields
	private ResourceLocation texture;

	public SimpleParticleRenderType(ResourceLocation var1) {
		this.texture = var1;
	}
	public BufferBuilder begin(Tesselator var1, TextureManager var2) {
		RenderSystem.depthMask(true);
		if (NuWorld.isChallengeDimension((Level)(Minecraft.getInstance().level))){
			RenderSystem.depthMask(true);
		}
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.disableCull();
		RenderSystem.setShader((Supplier)(((Supplier<ShaderInstance>)(GameRenderer::getParticleShader))));
		RenderSystem.setShaderTexture(0,this.texture);
		return var1.begin(VertexFormat.Mode.QUADS,ModRenderTypes.PARTICLE_POSITION_TEX_COLOR_LMAP);
	}
}
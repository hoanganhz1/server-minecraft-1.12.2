package xyz.pixelatedw.mineminenomi.init;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.renderers.blocks.FlagBlockEntityRenderer;

public class ModRenderTypes extends RenderType {
	// Fields
	public static ShaderInstance transparentColorShaderInstance;
	public static ShaderInstance auraHakiShaderInstance;
	protected static final RenderStateShard.ShaderStateShard TRANSPARENT_COLOR_SHADER;
	protected static final RenderStateShard.ShaderStateShard AURA_HAKI_SHADER;
	public static final RenderStateShard.OutputStateShard AURA_TARGET;
	public static final RenderType ENERGY;
	public static final RenderType TRANSPARENT_COLOR;
	public static final RenderType LIGHTNING;
	public static final RenderType FLAG;
	public static final RenderType FLAG_ON_FIRE;
	public static final VertexFormat PARTICLE_POSITION_TEX_COLOR_LMAP;

	public ModRenderTypes(String var1, VertexFormat var2, VertexFormat.Mode var3, int var4, boolean var5, boolean var6, Runnable var7, Runnable var8) {
		super(var1,var2,var3,var4,var5,var6,var7,var8);
	}
	public static RenderType getPosColorTexLightmap(ResourceLocation var0) {
		RenderStateShard.TextureStateShard var1 = new RenderStateShard.TextureStateShard(var0,false,false);
		RenderType.CompositeState var2 = RenderType.CompositeState.builder().setShaderState(RENDERTYPE_TRANSLUCENT_SHADER).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setTextureState((RenderStateShard.EmptyTextureStateShard)(var1)).setCullState(NO_CULL).setLightmapState(LIGHTMAP).createCompositeState(true);
		return create("mineminenomi:position_color_texture_lightmap",DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,VertexFormat.Mode.QUADS,256,true,true,var2);
	}
	public static RenderType getAuraRenderType() {
		return create("mineminenomi:aura",DefaultVertexFormat.POSITION_TEX_COLOR,VertexFormat.Mode.QUADS,256,true,true,RenderType.CompositeState.builder().setShaderState(AURA_HAKI_SHADER).setCullState(NO_CULL).setDepthTestState(NO_DEPTH_TEST).setOutputState(AURA_TARGET).createCompositeState(true));
	}
	public static RenderType getEyesLayerRenderType(ResourceLocation var0) {
		RenderStateShard.TextureStateShard var1 = new RenderStateShard.TextureStateShard(var0,false,false);
		RenderType.CompositeState var2 = RenderType.CompositeState.builder().setShaderState(RENDERTYPE_EYES_SHADER).setTransparencyState(ADDITIVE_TRANSPARENCY).setWriteMaskState(COLOR_WRITE).setTextureState((RenderStateShard.EmptyTextureStateShard)(var1)).createCompositeState(false);
		return create("mineminenomi:eyes_layer",DefaultVertexFormat.NEW_ENTITY,VertexFormat.Mode.QUADS,256,true,true,var2);
	}
	public static RenderType getAbilityBody(ResourceLocation var0) {
		return getAbilityBody(var0,false,false);
	}
	public static RenderType getAbilityBody(ResourceLocation var0, boolean var1, boolean var2) {
		RenderStateShard.TextureStateShard var3 = new RenderStateShard.TextureStateShard(var0,false,false);
		RenderType.CompositeState var4 = RenderType.CompositeState.builder().setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_SHADER).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setTextureState((RenderStateShard.EmptyTextureStateShard)(var3)).setCullState((var2) ? (CULL) : (NO_CULL)).setDepthTestState((var1) ? (EQUAL_DEPTH_TEST) : (LEQUAL_DEPTH_TEST)).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).createCompositeState(true);
		return create("mineminenomi:ability_body",DefaultVertexFormat.NEW_ENTITY,VertexFormat.Mode.QUADS,256,true,true,var4);
	}
	public static RenderType getAbilityHand(ResourceLocation var0) {
		RenderStateShard.TextureStateShard var1 = new RenderStateShard.TextureStateShard(var0,false,false);
		RenderType.CompositeState var2 = RenderType.CompositeState.builder().setShaderState(POSITION_COLOR_TEX_LIGHTMAP_SHADER).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setTextureState((RenderStateShard.EmptyTextureStateShard)(var1)).setCullState(NO_CULL).setLightmapState(LIGHTMAP).createCompositeState(true);
		return create("mineminenomi:ability_hand",DefaultVertexFormat.NEW_ENTITY,VertexFormat.Mode.QUADS,256,true,true,var2);
	}
	public static RenderType getWantedPoster(ResourceLocation var0) {
		RenderStateShard.TextureStateShard var1 = new RenderStateShard.TextureStateShard(var0,false,true);
		RenderType.CompositeState var2 = RenderType.CompositeState.builder().setShaderState(RENDERTYPE_CUTOUT_SHADER).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setLayeringState(VIEW_OFFSET_Z_LAYERING).setTextureState((RenderStateShard.EmptyTextureStateShard)(var1)).setCullState(NO_CULL).setLightmapState(LIGHTMAP).createCompositeState(true);
		return create("mineminenomi:wanted_poster",DefaultVertexFormat.BLOCK,VertexFormat.Mode.QUADS,256,true,true,var2);
	}
	static  {
		Supplier<ShaderInstance> var0 = () -> {
			return transparentColorShaderInstance;
		};
		TRANSPARENT_COLOR_SHADER = new RenderStateShard.ShaderStateShard(var0);
		Supplier<ShaderInstance> var1 = () -> {
			return auraHakiShaderInstance;
		};
		AURA_HAKI_SHADER = new RenderStateShard.ShaderStateShard(var1);
		Runnable var2 = () -> {
			Minecraft.getInstance().levelRenderer.entityTarget().bindWrite(false);
		};
		Runnable var3 = () -> {
			Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
		};
		AURA_TARGET = new RenderStateShard.OutputStateShard("mineminenomi:aura_target",var2,var3);
		ENERGY = create("mineminenomi:energy",DefaultVertexFormat.POSITION_COLOR_LIGHTMAP,VertexFormat.Mode.QUADS,256,false,true,RenderType.CompositeState.builder().setShaderState(RENDERTYPE_LIGHTNING_SHADER).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setTextureState(NO_TEXTURE).setCullState(NO_CULL).createCompositeState(true));
		TRANSPARENT_COLOR = create("mineminenomi:rendertype_entity_translucent_color_no_texture",DefaultVertexFormat.NEW_ENTITY,VertexFormat.Mode.QUADS,256,true,true,RenderType.CompositeState.builder().setShaderState(TRANSPARENT_COLOR_SHADER).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setTextureState(NO_TEXTURE).setCullState(NO_CULL).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).createCompositeState(true));
		LIGHTNING = create("mineminenomi:lightning",DefaultVertexFormat.POSITION_COLOR_LIGHTMAP,VertexFormat.Mode.QUADS,256,false,true,RenderType.CompositeState.builder().setShaderState(RENDERTYPE_LIGHTNING_SHADER).setTransparencyState(LIGHTNING_TRANSPARENCY).setTextureState(NO_TEXTURE).setCullState(NO_CULL).createCompositeState(true));
		FLAG = create("mineminenomi:flag",DefaultVertexFormat.POSITION_COLOR_LIGHTMAP,VertexFormat.Mode.QUADS,256,false,true,RenderType.CompositeState.builder().setShaderState(RENDERTYPE_LIGHTNING_SHADER).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setTextureState(NO_TEXTURE).setCullState(CULL).setLightmapState(LIGHTMAP).createCompositeState(true));
		FLAG_ON_FIRE = create("mineminenomi:flag_on_fire",DefaultVertexFormat.NEW_ENTITY,VertexFormat.Mode.QUADS,256,false,true,RenderType.CompositeState.builder().setShaderState(RENDERTYPE_LIGHTNING_SHADER).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setTextureState((RenderStateShard.EmptyTextureStateShard)(new RenderStateShard.TextureStateShard(FlagBlockEntityRenderer.ON_FIRE_TEXTURE,false,false))).setCullState(CULL).setLightmapState(LIGHTMAP).setTexturingState(ENTITY_GLINT_TEXTURING).setLayeringState(VIEW_OFFSET_Z_LAYERING).createCompositeState(true));
		PARTICLE_POSITION_TEX_COLOR_LMAP = VertexFormat.builder().add("Position",VertexFormatElement.POSITION).add("UV0",VertexFormatElement.UV0).add("Color",VertexFormatElement.COLOR).add("UV2",VertexFormatElement.UV2).build();
	}
}
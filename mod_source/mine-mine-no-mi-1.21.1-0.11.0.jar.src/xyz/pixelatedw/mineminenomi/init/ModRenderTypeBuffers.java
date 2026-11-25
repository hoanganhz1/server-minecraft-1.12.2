package xyz.pixelatedw.mineminenomi.init;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.Window;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceProvider;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.mixins.client.ILevelRendererMixin;
import xyz.pixelatedw.mineminenomi.renderers.buffers.HakiAuraBuffer;

public class ModRenderTypeBuffers {
	// Fields
	private static final ResourceLocation HAKI_AURA_SHADER = ResourceLocation.fromNamespaceAndPath("mineminenomi","shaders/post/haki_aura.json");
	private static ModRenderTypeBuffers INSTANCE;
	private Minecraft minecraft;
	private PostChain hakiAuraShader;
	private HakiAuraBuffer hakiAuraBufferSource;

	public void initHakiAuraShader() {
		if ((this.hakiAuraShader) != (null)){
			this.hakiAuraShader.close();
		}
		try{
			this.hakiAuraShader = new PostChain(this.minecraft.getTextureManager(),(ResourceProvider)(this.minecraft.getResourceManager()),this.minecraft.getMainRenderTarget(),HAKI_AURA_SHADER);
			this.hakiAuraShader.resize(this.minecraft.getWindow().getWidth(),this.minecraft.getWindow().getHeight());
		}catch(IOException var1){
			var1.printStackTrace();
			String var1_1 = String.valueOf((Object)(HAKI_AURA_SHADER));
			String var2 = "Failed to load haki aura shader: " + var1_1;
			WyDebug.error(var2);
		}
	}
	public void toggleHakiAuraShader(boolean var1) {
		ILevelRendererMixin var2 = ((ILevelRendererMixin)(this.minecraft.levelRenderer));
		if (var1){
			var2.setEntityTarget(this.hakiAuraShader.getTempTarget("final"));
		}else{
			var2.setEntityTarget(var2.getEntityEffect().getTempTarget("final"));
		}
	}
	public static ModRenderTypeBuffers getInstance() {
		if ((INSTANCE) == (null)){
			ModRenderTypeBuffers var0 = new ModRenderTypeBuffers();
			var0.minecraft = Minecraft.getInstance();
			var0.hakiAuraBufferSource = new HakiAuraBuffer(Minecraft.getInstance().renderBuffers().bufferSource());
			INSTANCE = var0;
		}
		return INSTANCE;
	}
	public HakiAuraBuffer getHakiAuraBuffer() {
		return this.hakiAuraBufferSource;
	}
	public PostChain getHakiAuraPostChain() {
		return this.hakiAuraShader;
	}
}
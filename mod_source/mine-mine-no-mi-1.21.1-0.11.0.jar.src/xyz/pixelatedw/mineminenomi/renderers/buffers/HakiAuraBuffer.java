package xyz.pixelatedw.mineminenomi.renderers.buffers;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

public class HakiAuraBuffer implements MultiBufferSource {
	// Fields
	private final MultiBufferSource.BufferSource bufferSource;
	private final MultiBufferSource.BufferSource hakiBufferSource;
	private int red;
	private int green;
	private int blue;
	private int alpha;

	public HakiAuraBuffer(MultiBufferSource.BufferSource var1) {
		this.hakiBufferSource = MultiBufferSource.immediate(new ByteBufferBuilder(256));
		this.red = 255;
		this.green = 255;
		this.blue = 255;
		this.alpha = 255;
		this.bufferSource = var1;
	}
	public VertexConsumer getBuffer(RenderType var1) {
		VertexConsumer var2 = this.bufferSource.getBuffer(var1);
		return new HakiAuraBuffer$ColorVertexBuilder(var2,this.red,this.green,this.blue,this.alpha);
	}
	public void setColor(int var1, int var2, int var3, int var4) {
		this.red = var1;
		this.green = var2;
		this.blue = var3;
		this.alpha = var4;
	}
	public void endBatch() {
		this.hakiBufferSource.endBatch();
	}
}
package xyz.pixelatedw.mineminenomi.renderers.buffers;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.util.FastColor;

public final record HakiAuraBuffer$ColorVertexBuilder(VertexConsumer delegate, int color) {
	public HakiAuraBuffer$ColorVertexBuilder(VertexConsumer var1, int var2, int var3, int var4, int var5) {
		this(var1,FastColor.ARGB32.color(var5,var2,var3,var4));
	}
	public VertexConsumer addVertex(float var1, float var2, float var3) {
		this.delegate.addVertex(var1,var2,var3).setColor(this.color);
		return this;
	}
	public VertexConsumer setColor(int var1, int var2, int var3, int var4) {
		return this;
	}
	public VertexConsumer setUv(float var1, float var2) {
		this.delegate.setUv(var1,var2);
		return this;
	}
	public VertexConsumer setUv1(int var1, int var2) {
		return this;
	}
	public VertexConsumer setUv2(int var1, int var2) {
		return this;
	}
	public VertexConsumer setNormal(float var1, float var2, float var3) {
		return this;
	}
}
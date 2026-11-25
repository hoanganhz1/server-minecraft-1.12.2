package xyz.pixelatedw.mineminenomi.api.abilities;

import java.awt.Color;
import java.util.Random;
import net.minecraft.resources.ResourceLocation;

public class AbilityOverlay {
	// Fields
	private int id;
	 ResourceLocation texture;
	 AbilityOverlay$RenderType renderType;
	 Color color;
	 AbilityOverlay$OverlayPart overlayPart;

	AbilityOverlay() {
		this.texture = null;
		this.renderType = AbilityOverlay$RenderType.NORMAL;
		this.color = new Color(-1,true);
		this.overlayPart = AbilityOverlay$OverlayPart.BODY;
		this.id = new Random().nextInt();
	}
	private AbilityOverlay setTexture(ResourceLocation var1) {
		this.texture = var1;
		return this;
	}
	private AbilityOverlay setColor(Color var1) {
		this.color = var1;
		return this;
	}
	private AbilityOverlay setRenderType(AbilityOverlay$RenderType var1) {
		this.renderType = var1;
		return this;
	}
	public ResourceLocation getTexture() {
		return this.texture;
	}
	public Color getColor() {
		return this.color;
	}
	public AbilityOverlay$RenderType getRenderType() {
		return this.renderType;
	}
	private void setOverlayPart(AbilityOverlay$OverlayPart var1) {
		this.overlayPart = var1;
	}
	public AbilityOverlay$OverlayPart getOverlayPart() {
		return this.overlayPart;
	}
	public int hashCode() {
		int var1 = (this.id) ^ ((this.id) >>> (32));
		var1 = ((31) * (var1)) + (((this.texture) == (null)) ? (0) : (this.texture.hashCode()));
		var1 = ((31) * (var1)) + (this.color.hashCode());
		var1 = ((31) * (var1)) + (this.overlayPart.hashCode());
		var1 = ((31) * (var1)) + (this.renderType.hashCode());
		return var1;
	}
	final AbilityOverlay jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c6974794f7665726c6179$0(Color arg0) { return ((AbilityOverlay)this).setColor(arg0); }

	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c6974794f7665726c6179$1(AbilityOverlay$OverlayPart arg0) { ((AbilityOverlay)this).setOverlayPart(arg0); }

	final AbilityOverlay jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c6974794f7665726c6179$2(AbilityOverlay$RenderType arg0) { return ((AbilityOverlay)this).setRenderType(arg0); }

	final AbilityOverlay jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c6974794f7665726c6179$3(ResourceLocation arg0) { return ((AbilityOverlay)this).setTexture(arg0); }

}
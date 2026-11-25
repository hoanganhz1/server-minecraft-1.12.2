package xyz.pixelatedw.mineminenomi.api.abilities;

import java.awt.Color;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class AbilityOverlay$Builder {
	// Fields
	 ResourceLocation texture;
	 AbilityOverlay$RenderType renderType;
	 Color color;
	 AbilityOverlay$OverlayPart overlayPart;

	public AbilityOverlay$Builder() {
		this.texture = null;
		this.renderType = AbilityOverlay$RenderType.NORMAL;
		this.color = new Color(-1,true);
		this.overlayPart = AbilityOverlay$OverlayPart.BODY;
	}
	public AbilityOverlay$Builder setTexture(ResourceLocation var1) {
		this.texture = var1;
		return this;
	}
	public AbilityOverlay$Builder setColor(int var1) {
		this.color = new Color(var1,true);
		return this;
	}
	public AbilityOverlay$Builder setColor(Color var1) {
		this.color = var1;
		return this;
	}
	public AbilityOverlay$Builder setColor(String var1) {
		this.color = WyHelper.hexToRGB(var1);
		return this;
	}
	public AbilityOverlay$Builder setRenderType(AbilityOverlay$RenderType var1) {
		this.renderType = var1;
		return this;
	}
	public AbilityOverlay$Builder setOverlayPart(AbilityOverlay$OverlayPart var1) {
		this.overlayPart = var1;
		return this;
	}
	public AbilityOverlay build() {
		AbilityOverlay var1 = new AbilityOverlay();
		((AbilityOverlay)(var1)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c6974794f7665726c6179$3(((ResourceLocation)(this.texture)));
		((AbilityOverlay)(var1)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c6974794f7665726c6179$0(((Color)(this.color)));
		((AbilityOverlay)(var1)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c6974794f7665726c6179$2(((AbilityOverlay$RenderType)(this.renderType)));
		((AbilityOverlay)(var1)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c6974794f7665726c6179$1(((AbilityOverlay$OverlayPart)(this.overlayPart)));
		return var1;
	}
}
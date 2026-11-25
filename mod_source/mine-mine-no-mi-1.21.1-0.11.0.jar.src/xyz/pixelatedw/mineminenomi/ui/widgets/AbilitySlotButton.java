package xyz.pixelatedw.mineminenomi.ui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.ui.screens.AbilitiesListScreen;

public class AbilitySlotButton extends Button {
	// Fields
	private static final Component EMPTY = Component.empty();
	private static final float FADE_IN_POWER = 0.15F;
	private static final float FADE_OUT_POWER = 0.1F;
	private final AbstractClientPlayer player;
	private float red;
	private float green;
	private float blue;
	private float[] hoverColor;
	private float[] pressedColor;
	private int slotIndex;
	private IAbility ability;
	private boolean isPressed;
	private TexturedRectUI rect;

	public AbilitySlotButton(IAbility var1, int var2, int var3, int var4, int var5, AbstractClientPlayer var6, Button.OnPress var7) {
		super(var2,var3,var4,var5,EMPTY,var7,Button.DEFAULT_NARRATION);
		this.red = 1F;
		this.green = 1F;
		this.blue = 1F;
		this.rect = new TexturedRectUI(ModResources.WIDGETS);
		this.player = var6;
		this.ability = var1;
		this.setHoverColor(0F,0.5F,1F);
		this.setPressedColor(0F,0.25F,1F);
	}
	public int getSlotIndex() {
		return this.slotIndex;
	}
	public void setSlotIndex(int var1) {
		this.slotIndex = var1;
	}
	public void setHoverColor(float var1, float var2, float var3) {
		float[] var4 = new float[]{var1,var2,var3};
		this.hoverColor = var4;
	}
	public void setPressedColor(float var1, float var2, float var3) {
		float[] var4 = new float[]{var1,var2,var3};
		this.pressedColor = var4;
	}
	public void renderWidget(GuiGraphics var1, int var2, int var3, float var4) {
		var1.pose().pushPose();
		var1.pose().translate(0F,0F,250F);
		if (this.visible){
			RenderSystem.enableBlend();
			if (this.isHovered){
				this.red = (this.red) - ((0.15F) * (var4));
				this.green = (this.green) - ((0.15F) * (var4));
				this.blue = (this.blue) - ((0.15F) * (var4));
				this.red = Mth.clamp(this.red,this.hoverColor[0],1F);
				this.green = Mth.clamp(this.green,this.hoverColor[1],1F);
				this.blue = Mth.clamp(this.blue,this.hoverColor[2],1F);
			}else{
				this.red = (this.red) + ((0.1F) * (var4));
				this.green = (this.green) + ((0.1F) * (var4));
				this.blue = (this.blue) + ((0.1F) * (var4));
				if (this.isPressed){
					this.red = Mth.clamp(this.red,0F,this.pressedColor[0]);
					this.green = Mth.clamp(this.green,0F,this.pressedColor[1]);
					this.blue = Mth.clamp(this.blue,0F,this.pressedColor[2]);
				}else{
					this.red = Mth.clamp(this.red,0F,1F);
					this.green = Mth.clamp(this.green,0F,1F);
					this.blue = Mth.clamp(this.blue,0F,1F);
				}
			}
			this.rect.setColor(this.red,this.green,this.blue,1F);
			this.rect.setSize(23F,23F);
			this.rect.draw(var1.pose(),(float)(this.getX()),(float)(this.getY()));
			RenderSystem.disableBlend();
			if ((this.getAbility()) != (null)){
				RendererHelper.drawIcon(this.getAbility().getIcon((LivingEntity)(this.player)),var1.pose(),(float)((this.getX()) + (4)),(float)((this.getY()) + (4)),0F,16F,16F);
				if (((this.ability) != (null)) && ((this.isHovered) && (!(Screen.hasAltDown())))){
					AbilitiesListScreen.renderAbilityTooltip(var1,this.getX(),this.getY(),this.ability);
				}
			}
		}
		var1.pose().translate(0F,0F,-250F);
		var1.pose().popPose();
	}
	public void setIsPressed(boolean var1) {
		this.isPressed = var1;
	}
	public void setAbility(IAbility var1) {
		this.ability = var1;
	}
	@Nullable()
	public @Nullable() IAbility getAbility() {
		return this.ability;
	}
}
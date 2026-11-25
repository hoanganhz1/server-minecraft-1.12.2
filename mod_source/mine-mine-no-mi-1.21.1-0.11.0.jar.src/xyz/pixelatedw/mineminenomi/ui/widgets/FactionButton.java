package xyz.pixelatedw.mineminenomi.ui.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.util.TextTable;
import xyz.pixelatedw.mineminenomi.api.helpers.FactionHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;

public class FactionButton extends Button {
	// Fields
	private final Minecraft minecraft;
	private EntityStatsData entityData;
	private boolean isSelected;
	private TextTable.Alignment textAlignment;
	private int lineThickness;
	private boolean hasIcons;
	private int blackColor;

	public FactionButton(int var1, int var2, int var3, int var4, Component var5, Button.OnPress var6) {
		this(var1,var2,var3,var4,var5,var6,Button.DEFAULT_NARRATION);
	}
	public FactionButton(int var1, int var2, int var3, int var4, Component var5, Button.OnPress var6, Button.CreateNarration var7) {
		super(var1,var2,var3,var4,var5,var6,var7);
		this.textAlignment = TextTable.Alignment.LEFT;
		this.lineThickness = 1;
		this.hasIcons = true;
		this.blackColor = 0;
		this.entityData = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(Minecraft.getInstance().player)))).orElse((Object)(null))));
		this.minecraft = Minecraft.getInstance();
	}
	public void setTextAlignment(TextTable.Alignment var1) {
		this.textAlignment = var1;
	}
	public void setLineThickness(int var1) {
		this.lineThickness = var1;
	}
	public void disableIcons() {
		this.hasIcons = false;
	}
	public void renderWidget(GuiGraphics var1, int var2, int var3, float var4) {
		var1.pose().pushPose();
		if (this.visible){
			int var7 = 0;
			this.isHovered = ((var2) >= (this.getX())) && (((var3) >= (this.getY())) && (((var2) < ((this.getX()) + (this.width))) && ((var3) < ((this.getY()) + (this.height)))));
			int var5 = 16777215;
			int var6 = 15395562;
			if (!(this.active)){
				var5 = var6 = 7039851;
			}
			if ((this.isHovered) && (this.active)){
				var1.pose().translate(0D,0.5D,0D);
				var7 = FactionHelper.getFactionRGBColor(this.entityData);
				var6 = var7;
				var5 = var7;
			}
			if ((this.textAlignment) == (TextTable.Alignment.CENTER)){
				var7 = ((this.getX()) - ((this.minecraft.font.width((FormattedText)(this.getMessage()))) / (2))) + ((this.width) / (2));
			}else{
				if ((this.textAlignment) == (TextTable.Alignment.RIGHT)){
					var7 = this.getX();
				}else{
					var7 = this.getX();
				}
			}
			var1.fillGradient((this.getX()) - (4),(((this.getY()) + (this.height)) - (this.lineThickness)) + (2),((this.width) + (this.getX())) + (1),(this.getY()) + (this.height),this.blackColor,this.blackColor);
			var1.fillGradient((this.getX()) - (5),((this.getY()) + (this.height)) - (this.lineThickness),(this.width) + (this.getX()),(this.getY()) + (this.height),var6,var6);
			int var7_1 = 0;
			if (this.hasIcons){
				ResourceLocation var8 = FactionHelper.getFactionIcon(this.entityData);
				if ((var8) != (null)){
					RendererHelper.drawIcon(var8,var1.pose(),(float)((this.getX()) - (12)),(float)((this.getY()) - (4)),1F,32F,32F,this.blackColor);
					RendererHelper.drawIcon(var8,var1.pose(),(float)((this.getX()) - (13)),(float)((this.getY()) - (5)),1F,32F,32F,var6);
					var7_1 = 13;
				}
			}
			RendererHelper.drawStringWithBorder(this.minecraft.font,var1,this.getMessage().getString(),(var7) + (var7_1),((this.getY()) + ((this.height) / (2))) - (4),var5);
		}
		var1.pose().popPose();
	}
}
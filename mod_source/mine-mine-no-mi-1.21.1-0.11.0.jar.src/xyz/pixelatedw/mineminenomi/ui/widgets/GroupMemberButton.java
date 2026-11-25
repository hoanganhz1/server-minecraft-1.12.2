package xyz.pixelatedw.mineminenomi.ui.widgets;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class GroupMemberButton extends Button {
	// Fields
	private Font font;
	private LivingEntity entity;
	private LivingEntity dummy;
	private int fontColor;

	public GroupMemberButton(int var1, int var2, int var3, int var4, LivingEntity var5, LivingEntity var6, Button.OnPress var7) {
		super(var1,var2,var3,var4,(Component)(Component.empty()),var7,Button.DEFAULT_NARRATION);
		this.fontColor = Color.WHITE.getRGB();
		this.font = Minecraft.getInstance().font;
		this.entity = var5;
		this.dummy = var6;
	}
	public void renderWidget(GuiGraphics var1, int var2, int var3, float var4) {
		var1.pose().pushPose();
		if (this.visible){
			this.isHovered = (this.active) && (((var2) >= (this.getX())) && (((var3) >= (this.getY())) && (((var2) < ((this.getX()) + (this.width))) && ((var3) < ((this.getY()) + (this.height))))));
			this.fontColor = Color.WHITE.getRGB();
			if (this.isHovered){
				var1.pose().translate(0D,-0.5D,0D);
				this.fontColor = Color.ORANGE.getRGB();
			}
			this.renderEntityBust(this.entity,var1,(this.getX()) + ((this.width) / (2)),((this.getY()) + ((this.height) / (2))) + (42));
		}
		var1.pose().popPose();
	}
	private void renderEntityBust(@Nullable() LivingEntity var1, GuiGraphics var2, int var3, int var4) {
		if ((var1) != (null)){
			RendererHelper.drawLivingBust(var1,var2,var3,var4,30,-30,10,false);
			String var5 = this.getEntityName(var1);
			RendererHelper.drawStringWithBorder(this.font,var2,var5,(var3) - ((this.font.width(var5)) / (2)),(var4) - (30),this.fontColor);
		}else{
			RendererHelper.drawLivingBust(this.dummy,var2,var3,var4,30,-30,10,true);
			String var5_1 = ModI18n.GUI_SELECT_ONE.getString();
			RendererHelper.drawStringWithBorder(this.font,var2,var5_1,(var3) - ((this.font.width(var5_1)) / (2)),(var4) - (30),this.fontColor);
		}
	}
	private String getEntityName(LivingEntity var1) {
		if (var1 instanceof Player){
			return ((Player)(var1)).getGameProfile().getName();
		}else{
			return var1.getDisplayName().getString();
		}
	}
}
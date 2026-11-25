package xyz.pixelatedw.mineminenomi.ui.screens;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.items.components.WantedPosterData;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class WantedPosterScreen extends Screen {
	// Fields
	public static final int TEXT_COLOR = -11455469;
	private WantedPosterData wantedPosterData;
	private final String name;
	private final JollyRoger jollyRoger;
	private final ResourceLocation background;
	private final ResourceLocation skin;
	private final boolean isPirate;
	private final boolean isRevo;
	private final float nameScale;

	public WantedPosterScreen(WantedPosterData var1) {
		super((Component)(Component.empty()));
		this.minecraft = Minecraft.getInstance();
		this.wantedPosterData = var1;
		this.name = this.wantedPosterData.ownerName();
		float var2 = 1F;
		int var3 = this.name.length();
		if ((var3) > (13)){
			var2 = (1F) * ((float)(Math.pow(0.8899999856948853D,(double)(((float)((var3) - (11))) / (2.3F)))));
		}
		this.nameScale = var2;
		String var4 = this.wantedPosterData.background();
		String var5 = var4;
		String var6 = "textures/gui/wantedposters/backgrounds/" + var5 + ".png";
		this.background = ResourceLocation.fromNamespaceAndPath("mineminenomi",var6);
		this.jollyRoger = ((JollyRoger)(((java.util.Optional)(this.wantedPosterData.jollyRoger())).orElse((Object)(null))));
		if (this.wantedPosterData.picture().isEmpty()){
			this.skin = Minecraft.getInstance().getSkinManager().getInsecureSkin(new GameProfile(this.wantedPosterData.owner(),this.wantedPosterData.ownerName())).texture();
		}else{
			if (this.wantedPosterData.picture().isPresent()){
				this.skin = ((ResourceLocation)(this.wantedPosterData.picture().get()));
			}else{
				this.skin = DefaultPlayerSkin.get(this.wantedPosterData.owner()).texture();
			}
		}
		Optional var7 = this.wantedPosterData.faction();
		Function<Faction, Boolean> var8 = (l0) -> {
			return Boolean.valueOf(l0.equals(ModFactions.PIRATE.get()));
		};
		this.isPirate = ((Boolean)(((java.util.Optional)(var7)).map((Function)(var8)).orElse(Boolean.valueOf(false)))).booleanValue();
		Function<Faction, Boolean> var9 = (l0) -> {
			return Boolean.valueOf(l0.equals(ModFactions.REVOLUTIONARY_ARMY.get()));
		};
		this.isRevo = ((Boolean)(((java.util.Optional)(var7)).map((Function)(var9)).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		super.render(var1,var2,var3,var4);
		var1.setColor(1F,1F,1F,1F);
		int var5 = ((this.width) - (256)) / (2);
		int var6 = ((this.height) - (256)) / (2);
		var1.pose().translate((float)((var5) + (60)),(float)((var6) + (10)),0F);
		var1.pose().translate(128F,128F,512F);
		var1.pose().scale(1F,0.9F,0F);
		var1.pose().translate(-128F,-128F,-512F);
		var1.blit(ModResources.BOUNTY_POSTER_LARGE,0,0,0,0,256,256);
		var1.pose().translate(67F,150F,0F);
		var1.pose().translate(128F,128F,512F);
		var1.pose().scale(1.5F,1.6F,0F);
		var1.pose().translate(-128F,-128F,-512F);
		var1.pose().pushPose();
		var1.pose().scale(0.34F,0.245F,0F);
		var1.blit(this.background,23,-57,0,0,256,256);
		var1.pose().scale(4.25F,5.5F,0F);
		var1.blit(this.skin,20,-3,32,32,32,32);
		var1.blit(this.skin,20,-3,160,32,32,32);
		var1.pose().pushPose();
		float var7 = 0.08F;
		var1.pose().scale(var7,var7,var7);
		var1.pose().translate(550F,190F,0F);
		if (this.isPirate){
			if ((this.jollyRoger) != (null)){
				this.jollyRoger.render(var1.pose(),0,0,1F);
			}
		}else{
			if (this.isRevo){
				var1.pose().translate(-120F,-80F,0F);
				var1.pose().scale(2F,2F,2F);
				var1.blit(ModResources.REVOLUTIONARY_ARMY_ICON,0,0,256,256,256,256);
			}
		}
		var1.pose().popPose();
		if (this.wantedPosterData.isExpired()){
			var1.pose().pushPose();
			var7 = 0.2F;
			var1.pose().scale((var7) + (0.022F),var7,var7);
			var1.pose().translate(50F,-47F,0F);
			var1.blit(ModResources.EXPIRED,0,0,16,16,256,256);
			var1.pose().popPose();
		}
		var1.pose().popPose();
		var1.blit(ModResources.CURRENCIES,-2,63,0,0,32,32);
		var1.pose().pushPose();
		var1.pose().translate((45D) - ((((double)(this.minecraft.font.width(this.name))) / (2D)) * ((double)(this.nameScale))),62D,0D);
		var1.pose().scale(this.nameScale,this.nameScale,this.nameScale);
		String var8 = String.valueOf((Object)(ChatFormatting.BOLD));
		String var9 = this.name;
		String var10 = var8 + var9;
		var1.drawString(this.font,var10,0,0,-11455469,false);
		var1.pose().popPose();
		int var11 = ((this.wantedPosterData.getBountyString().length()) > (10)) ? (1) : (0);
		if ((var11) != (0)){
			var1.pose().pushPose();
			var1.pose().translate(-21F,-5F,0F);
			var1.pose().translate(128F,128F,512F);
			var1.pose().scale(0.82F,0.89F,0F);
			var1.pose().translate(-128F,-128F,-512F);
		}
		String var12 = String.valueOf((Object)(ChatFormatting.BOLD));
		String var13 = this.wantedPosterData.getBountyString();
		String var14 = var12 + var13;
		var1.drawString(this.font,var14,22,76,-11455469,false);
		if ((var11) != (0)){
			var1.pose().popPose();
		}
		var1.pose().translate(-24F,-2F,0F);
		var1.pose().translate(128F,128F,512F);
		var1.pose().scale(0.78F,0.92F,0F);
		var1.pose().translate(-128F,-128F,-512F);
		float var15 = (float)((36) - ((this.minecraft.font.width(this.wantedPosterData.date())) / (2)));
		String var16 = String.valueOf((Object)(ChatFormatting.BOLD));
		String var17 = this.wantedPosterData.date();
		String var18 = var16 + var17;
		var1.drawString(this.font,var18,var15,89F,-11455469,false);
	}
	public boolean isPauseScreen() {
		return false;
	}
}
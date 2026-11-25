package xyz.pixelatedw.mineminenomi.ui.panel;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.gui.widget.ScrollPanel;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInvitation;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.challenge.CAcceptChallengeInvitationPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.ChallengesScreen;

public class ChallengeInvitesPanel extends ScrollPanel {
	// Fields
	private static final int ENTRY_HEIGHT = 55;
	private static final Color SELECTION_COLOR = new Color(1F,1F,1F,0.2F);
	private final Player player;
	private final Level world;
	private final ChallengesScreen parent;
	private List<ChallengeInvitation> invites;

	public ChallengeInvitesPanel(ChallengesScreen var1, Player var2, List<ChallengeInvitation> var3) {
		super(var1.getMinecraft(),260,170,((var1.height) / (2)) - (70),((var1.width) / (2)) - (46));
		this.world = var2.level();
		this.player = var2;
		this.parent = var1;
		this.invites = var3;
	}
	public boolean mouseReleased(double var1, double var2, int var3) {
		return true;
	}
	protected int getContentHeight() {
		return ((this.invites.size()) * (55)) - (2);
	}
	protected int getScrollAmount() {
		return 12;
	}
	protected void drawGradientRect(GuiGraphics var1, int var2, int var3, int var4, int var5, int var6, int var7) {
		var1.fillGradient(0,var2,var3,var4,var5,var6,var7);
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		super.render(var1,var2,var3,var4);
	}
	protected void drawPanel(GuiGraphics var1, int var2, int var3, Tesselator var4, int var5, int var6) {
		int var7 = (var3) + (10);
		int var8 = ((this.parent.width) / (2)) + (20);
		ChallengeInvitation var9 = this.findEntry(var5,var6);
		int var10 = 0;
		Iterator var11 = this.invites.iterator();
		do{
			if (var11.hasNext()){
				ChallengeInvitation var12 = ((ChallengeInvitation)(var11.next()));
				int var13 = (((var9) != (null)) && ((((java.lang.Object)(var9)).equals((Object)(var12))) && (this.isMouseOver((double)(var5),(double)(var6))))) ? (1) : (0);
				if ((var13) != (0)){
					var1.fillGradient(0,(var8) - (this.right),(var3) + (var10),(var8) + (this.width),((var3) + (var10)) + (55),SELECTION_COLOR.getRGB(),SELECTION_COLOR.getRGB());
					var1.pose().pushPose();
					var1.pose().translate(0F,-2F,0F);
				}
				Player var14 = this.world.getPlayerByUUID(var12.getSenderId());
				if ((var14) == (null)){
					continue;
				}else{
					RendererHelper.drawPlayerFace(var14,var1,(var8) - (53),((var7) + (var10)) + (3),32,32);
					RendererHelper.drawStringWithBorder(this.parent.getMinecraft().font,var1,var14.getGameProfile().getName(),(var8) - (10),(var7) + (var10),-1);
					RendererHelper.drawStringWithBorder(this.parent.getMinecraft().font,var1,var12.getChallenge().getLocalizedTitle().getString(),(var8) - (10),((var7) + (var10)) + (15),-1);
					RendererHelper.drawStringWithBorder(this.parent.getMinecraft().font,var1,var12.getChallenge().getLocalizedObjective().getString(),(var8) - (10),((var7) + (var10)) + (30),-1);
					if ((var13) != (0)){
						var1.pose().popPose();
					}
					var10 = (var10) + (55);
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	private ChallengeInvitation findEntry(int var1, int var2) {
		double var3 = (double)(((float)(((var2) - (this.top)) - (4))) + (this.scrollDistance));
		if ((var3) <= (0D)){
			return null;
		}else{
			int var4 = (int)((var3) / (55D));
			if (((var4) >= (0)) && ((var4) < (this.invites.size()))){
				ChallengeInvitation var5 = ((ChallengeInvitation)(this.invites.get(var4)));
				if ((var5) != (null)){
					return var5;
				}else{
					return null;
				}
			}else{
				return null;
			}
		}
	}
	public boolean mouseClicked(double var1, double var2, int var3) {
		ChallengeInvitation var4 = this.findEntry((int)(var1),(int)(var2));
		if (((var4) != (null)) && ((this.isMouseOver(var1,var2)) && ((var3) == (0)))){
			ModNetwork.sendToServer(new CAcceptChallengeInvitationPacket(var4));
			this.parent.getMinecraft().setScreen((Screen)(null));
			return true;
		}else{
			return super.mouseClicked(var1,var2,var3);
		}
	}
	public boolean isMouseOver(double var1, double var2) {
		return ((var1) >= ((double)(this.left))) && (((var2) >= ((double)(this.top))) && (((var1) < ((double)((this.left) + (this.width)))) && ((var2) < ((double)((this.top) + (this.height))))));
	}
	public NarratableEntry.NarrationPriority narrationPriority() {
		return NarratableEntry.NarrationPriority.NONE;
	}
	public void updateNarration(NarrationElementOutput var1) {
	}
}
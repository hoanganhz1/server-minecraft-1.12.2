package xyz.pixelatedw.mineminenomi.ui.panel;

import com.google.common.primitives.Ints;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.gui.widget.ScrollPanel;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.helpers.FactionHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.challenge.CSendChallengeInvitationPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.ChallengesScreen;

public class ChallengeGroupSelectorPanel extends ScrollPanel {
	// Fields
	private static final int ENTRY_HEIGHT = 55;
	private static final int ENTRY_WIDTH = 120;
	private static final int SETUP_TIME = 20;
	private final Color topColor;
	private static final Color BOTTOM_COLOR = new Color(0F,0F,0F,0.8F);
	private final Player player;
	private final ChallengesScreen parent;
	private final List<LivingEntity> nearbyGroupMembers;
	private int setupTime;

	public ChallengeGroupSelectorPanel(ChallengesScreen var1, Player var2, int[] var3) {
		super(var1.getMinecraft(),260,170,((var1.height) / (2)) - (70),((var1.width) / (2)) - (46));
		this.player = var2;
		this.parent = var1;
		this.setupTime = 20;
		this.topColor = FactionHelper.getFactionColor(((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var2)))).orElse((Object)(null)))));
		Player var4 = var2;
		final Player var4_f1 = var4;
		Function<Integer, Entity> var5 = (Integer l0) -> {
			return var4_f1.level().getEntity(l0.intValue());
		};
		Class var6 = LivingEntity.class;
		Objects.requireNonNull(var6);
		Function<Entity, LivingEntity> var7 = (l0) -> {
			return ((LivingEntity)(l0));
		};
		this.nearbyGroupMembers = (List) (((List)(((java.util.stream.Stream)(((java.util.stream.Stream)(((java.util.stream.Stream)(Ints.asList(var3).stream())).map((Function)(var5)).filter((Predicate)(((Predicate<Entity>)(var6::isInstance)))))).map((Function)(var7)))).collect((Collector)(Collectors.toList())))));
	}
	public boolean mouseReleased(double var1, double var2, int var3) {
		return true;
	}
	protected int getContentHeight() {
		return (((this.nearbyGroupMembers.size()) * (27)) + (55)) - (25);
	}
	protected int getScrollAmount() {
		return 55;
	}
	protected void drawGradientRect(GuiGraphics var1, int var2, int var3, int var4, int var5, int var6, int var7) {
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		if ((this.setupTime) > (0)){
			this.setupTime = (this.setupTime) - (1);
		}else{
			super.render(var1,var2,var3,var4);
		}
	}
	protected void drawPanel(GuiGraphics var1, int var2, int var3, Tesselator var4, int var5, int var6) {
		int var7 = (var3) + (65);
		int var8 = ((this.parent.width) / (2)) + (20);
		LivingEntity var9 = this.findEntry(var5,var6);
		int var10 = 0;
		int var11 = 0;
		int var12 = 0;
		Iterator var13 = this.nearbyGroupMembers.iterator();
		do{
			if (var13.hasNext()){
				LivingEntity var14 = ((LivingEntity)(var13.next()));
				if ((var10) == (2)){
					var11 = 0;
					var12 = (var12) + (55);
					var10 = 0;
				}
				int var15 = (((var9) != (null)) && ((var9.equals(var14)) && (this.isMouseOver((double)(var5),(double)(var6))))) ? (1) : (0);
				if ((var15) != (0)){
					var1.fillGradient(0,((var8) - (60)) + (var11),(var3) + (var12),((var8) + (60)) + (var11),((var3) + (var12)) + (55),this.topColor.getRGB(),BOTTOM_COLOR.getRGB());
					var1.pose().pushPose();
					var1.pose().translate(0F,-2F,0F);
				}
				if ((((var7) + (var12)) > ((this.top) + (55))) && (((var7) + (var12)) < ((this.bottom) + (55)))){
					this.parent.renderEntityBust(var14,var1,(var8) + (var11),((var7) + (5)) + (var12));
				}
				if ((var15) != (0)){
					var1.pose().popPose();
				}
				var11 = (var11) + (120);
				var10++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	private LivingEntity findEntry(int var1, int var2) {
		double var3 = (double)(((float)(((var2) - (this.top)) - (4))) + (this.scrollDistance));
		double var4 = (double)(((var1) - (this.left)) - (6));
		if ((var3) <= (0D)){
			return null;
		}else{
			if ((var4) <= (0D)){
				return null;
			}else{
				int var5 = (int)((var3) / (55D));
				int var6 = (int)((var4) / (120D));
				if ((var6) > (1)){
					return null;
				}else{
					int var7 = ((var5) * (2)) + (var6);
					if (((var7) >= (0)) && ((var7) < (this.nearbyGroupMembers.size()))){
						LivingEntity var8 = ((LivingEntity)(this.nearbyGroupMembers.get(var7)));
						if ((var8) != (null)){
							return var8;
						}else{
							return null;
						}
					}else{
						return null;
					}
				}
			}
		}
	}
	public boolean mouseClicked(double var1, double var2, int var3) {
		LivingEntity var4 = this.findEntry((int)(var1),(int)(var2));
		if (((var4) != (null)) && ((this.isMouseOver(var1,var2)) && (((var3) == (0)) && ((this.setupTime) <= (0))))){
			if (var4 instanceof Player){
				Player var5 = ((Player)(var4));
				ModNetwork.sendToServer(new CSendChallengeInvitationPacket(var5,this.parent.getSelectedChallenge().getCore(),this.parent.getSelectedMemberSlot()));
				this.parent.showGroupStep();
			}else{
				this.parent.setGroupMember(var4);
			}
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
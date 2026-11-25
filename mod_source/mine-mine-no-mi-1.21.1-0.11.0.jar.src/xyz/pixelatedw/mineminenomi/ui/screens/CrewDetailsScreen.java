package xyz.pixelatedw.mineminenomi.ui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.Crew$Member;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.crew.CLeaveCrewPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenJollyRogerEditorScreenPacket;
import xyz.pixelatedw.mineminenomi.ui.panel.CrewMembersScrollPanel;
import xyz.pixelatedw.mineminenomi.ui.widgets.PlankButton;

public class CrewDetailsScreen extends Screen {
	// Fields
	private Player player;
	private JollyRoger jollyRoger;
	private final Crew crew;
	private CrewMembersScrollPanel membersListPanel;
	private Component crewNameLabel;
	private Component crewMembersLabel;

	public CrewDetailsScreen(Crew var1) {
		super((Component)(Component.empty()));
		this.crew = var1;
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		super.render(var1,var2,var3,var4);
		RenderSystem.setShaderColor(1F,1F,1F,1F);
		int var5 = ((this.width) - (256)) / (2);
		int var6 = ((this.height) - (256)) / (2);
		RendererHelper.drawStringWithBorder(this.font,var1,this.crewNameLabel,(this.width) / (5),(var6) + (140),-1);
		RendererHelper.drawStringWithBorder(this.font,var1,this.crewMembersLabel,(this.width) - ((this.width) / (3)),30,-1);
		if ((this.jollyRoger) != (null)){
			this.jollyRoger.render(var1.pose(),(this.width) / (5),(var6) + (20),0.4F);
		}
		if ((this.membersListPanel) != (null)){
			this.membersListPanel.render(var1,var2,var3,var4);
		}
	}
	public void init() {
		this.clearWidgets();
		if ((this.crew) == (null)){
			return;
		}else{
			this.player = this.minecraft.player;
			this.jollyRoger = this.crew.getJollyRoger();
			String var1 = String.valueOf((Object)(ChatFormatting.BOLD));
			String var2 = ModI18n.GUI_NAME.getString();
			String var3 = String.valueOf((Object)(ChatFormatting.RESET));
			String var4 = this.crew.getName();
			String var5 = var1 + var2 + ": " + var3 + var4;
			this.crewNameLabel = Component.literal(var5);
			String var6 = String.valueOf((Object)(ChatFormatting.BOLD));
			String var7 = ModI18n.GUI_CREW_MEMBERS.getString();
			String var8 = var6 + var7 + ": ";
			this.crewMembersLabel = Component.literal(var8);
			this.membersListPanel = new CrewMembersScrollPanel(this,this.crew.getMembers());
			this.addRenderableWidget((GuiEventListener)(this.membersListPanel));
			this.setFocused((GuiEventListener)(this.membersListPanel));
			int var9 = ((this.width) - (256)) / (2);
			int var10 = ((this.height) - (256)) / (2);
			this.addRenderableWidget((GuiEventListener)(new PlankButton(var9,(this.height) - (40),80,26,ModI18n.GUI_LEAVE,(Button.OnPress)(((net.minecraft.client.gui.components.Button.OnPress)(this::leaveCrew))))));
			if (this.isClientCaptain()){
				this.addRenderableWidget((GuiEventListener)(new PlankButton((var9) + (90),(this.height) - (40),120,26,ModI18n.GUI_CHANGE_JOLLY_ROGER,(Button.OnPress)(((net.minecraft.client.gui.components.Button.OnPress)(this::openJollyRogerEditor))))));
			}
			return;
		}
	}
	public boolean isClientCaptain() {
		if (((this.crew) != (null)) && (((this.player) != (null)) && ((this.crew.getCaptain()) != (null)))){
			UUID var1 = this.crew.getCaptain().getUUID();
			Player var2 = this.minecraft.level.getPlayerByUUID(var1);
			return ((var2) != (null)) && (this.player.equals(var2));
		}else{
			return false;
		}
	}
	private void leaveCrew(Button var1) {
		ModNetwork.sendToServer(new CLeaveCrewPacket());
		this.onClose();
	}
	private void openJollyRogerEditor(Button var1) {
		ModNetwork.sendToServer(new COpenJollyRogerEditorScreenPacket());
	}
}
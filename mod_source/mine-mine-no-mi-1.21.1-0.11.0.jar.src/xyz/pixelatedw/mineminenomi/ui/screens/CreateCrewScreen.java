package xyz.pixelatedw.mineminenomi.ui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Iterator;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.INBTSerializable;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.ui.IEventReceiverScreen;
import xyz.pixelatedw.mineminenomi.api.ui.SimpleMessageScreenEvent;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.crew.CCreateCrewPacket;
import xyz.pixelatedw.mineminenomi.ui.widgets.SimpleButton;

public class CreateCrewScreen extends Screen implements IEventReceiverScreen<SimpleMessageScreenEvent> {
	// Fields
	private static final Style ERROR_STYLE = Style.EMPTY.withColor(ChatFormatting.RED);
	private Player player;
	private EditBox nameEdit;
	private Component errorMessage;
	private int errorMessageVisibleTicks;

	public CreateCrewScreen() {
		super((Component)(Component.empty()));
		this.errorMessage = Component.empty();
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		this.renderBackground(var1,var2,var3,var4);
		RenderSystem.setShaderColor(1F,1F,1F,1F);
		int var5 = (this.width) / (2);
		int var6 = (this.height) / (2);
		this.nameEdit.render(var1,var2,var3,var4);
		if ((this.errorMessageVisibleTicks) > (0)){
			int var7 = ((var5) - ((this.font.width(this.errorMessage.getString())) / (2))) - (1);
			RendererHelper.drawStringWithBorder(this.font,var1,this.errorMessage,var7,(var6) - (35),-1);
		}
		Iterator var7_1 = this.renderables.iterator();
		do{
			if (var7_1.hasNext()){
				((Renderable)(var7_1.next())).render(var1,var2,var3,var4);
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public void init() {
		this.player = this.minecraft.player;
		int var1 = (this.width) / (2);
		int var2 = (this.height) / (2);
		this.nameEdit = new EditBox(this.font,(var1) - (152),(var2) - (10),300,20,(Component)(Component.empty()));
		this.nameEdit.setMaxLength(255);
		String var3 = this.player.getName().getString();
		String var4 = var3 + "'s Crew";
		this.nameEdit.insertText(var4);
		this.addRenderableWidget((GuiEventListener)(this.nameEdit));
		this.setFocused((GuiEventListener)(this.nameEdit));
		this.addRenderableWidget((GuiEventListener)(new SimpleButton((var1) - (30),(var2) + (50),60,16,ModI18n.GUI_CREATE,(Button.OnPress)(((net.minecraft.client.gui.components.Button.OnPress)(this::createCrew))))));
	}
	public void resize(Minecraft var1, int var2, int var3) {
		String var4 = this.nameEdit.getValue();
		this.init(var1,var2,var3);
		this.nameEdit.insertText(var4);
	}
	public void tick() {
		if ((this.errorMessageVisibleTicks) > (0)){
			this.errorMessageVisibleTicks = (this.errorMessageVisibleTicks) - (1);
		}
	}
	public void handleEvent(SimpleMessageScreenEvent var1) {
		if (!(var1.getMessage().equals(Component.empty()))){
			this.errorMessage = var1.getMessage().copy().setStyle(ERROR_STYLE);
			this.errorMessageVisibleTicks = var1.getTimeVisible();
		}else{
			this.onClose();
		}
	}
	public SimpleMessageScreenEvent decode(HolderLookup.Provider var1, CompoundTag var2) {
		SimpleMessageScreenEvent var3 = new SimpleMessageScreenEvent();
		var3.deserializeNBT(var1,var2);
		return var3;
	}
	private void createCrew(Button var1) {
		ModNetwork.sendToServer(new CCreateCrewPacket(this.nameEdit.getValue()));
	}
}
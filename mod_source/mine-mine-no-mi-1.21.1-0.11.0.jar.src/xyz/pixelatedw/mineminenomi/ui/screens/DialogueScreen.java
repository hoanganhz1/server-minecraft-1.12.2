package xyz.pixelatedw.mineminenomi.ui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.ui.SequencedString;
import xyz.pixelatedw.mineminenomi.ui.widgets.FactionButton;

public abstract class DialogueScreen extends Screen {
	// Fields
	protected final LivingEntity entity;
	private Player player;
	private SequencedString message;
	private List<DialogueScreen$InteractionAction> interactions;

	public DialogueScreen(LivingEntity var1) {
		super((Component)(Component.empty()));
		this.message = new SequencedString("",0,0);
		this.interactions = new ArrayList();
		this.entity = var1;
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		super.render(var1,var2,var3,var4);
		int var5 = (this.width) / (2);
		int var6 = (this.height) / (2);
		this.message.render(var1,this.getMinecraft().font,(var5) - (150),(var6) - (105),var4);
		var1.pose().pushPose();
		var1.setColor(1F,1F,1F,1F);
		RenderSystem.enableBlend();
		InventoryScreen.renderEntityInInventoryFollowsAngle(var1,(var5) + (150),(var6) + (150),(var5) + (150),(var6) + (150),100,-2F,(float)(Math.toRadians(40D)),(float)(Math.toRadians(5D)),this.entity);
		RenderSystem.disableBlend();
		var1.pose().popPose();
	}
	public void init() {
		this.player = this.minecraft.player;
		this.interactions.clear();
		this.setupDialogue();
		this.generateButtons();
	}
	private void generateButtons() {
		this.clearWidgets();
		int var1 = (this.width) / (2);
		int var2 = (this.height) / (2);
		int var3 = 0;
		Iterator var4 = this.interactions.iterator();
		do{
			if (var4.hasNext()){
				DialogueScreen$InteractionAction var5 = ((DialogueScreen$InteractionAction)(var4.next()));
				Component var6 = var5.getTitle();
				int var7 = Math.max(100,(24) + (this.font.width((FormattedText)(var6))));
				FactionButton var8 = new FactionButton((var1) - (180),((var2) + (10)) + ((var3) * (20)),var7,20,var6,var5.getEvent());
				var8.setTooltip(var5.getTooltip());
				if (var5.isDisabled()){
					var8.active = false;
				}
				this.addRenderableWidget((GuiEventListener)(var8));
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public abstract void setupDialogue();
	public void addInteraction(DialogueScreen$InteractionAction var1) {
		this.interactions.add(var1);
	}
	public void setMessage(Component var1) {
		this.message.setMessage(var1);
		this.message.setMaxLength(250);
		int var2 = (this.font.width((FormattedText)(var1))) / (2);
		this.message.setMaxTicks(var2);
		this.message.setDelayTicks((var2) + (100));
		this.message.resetCounter();
	}
	public Player getPlayer() {
		return this.player;
	}
	public LivingEntity getTarget() {
		return this.entity;
	}
}
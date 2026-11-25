package xyz.pixelatedw.mineminenomi.ui.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import xyz.pixelatedw.mineminenomi.containers.WhiteWalkieStorageContainer;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WhiteWalkieEntity;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.CUpdateInventoryPagePacket;
import xyz.pixelatedw.mineminenomi.ui.widgets.PlankButton;

public class WhiteWalkieStorageScreen extends AbstractContainerScreen<WhiteWalkieStorageContainer> {
	// Fields
	private static final ResourceLocation CHEST_SLOTS_SPRITE = ResourceLocation.withDefaultNamespace("container/horse/chest_slots");
	private static final ResourceLocation SADDLE_SLOT_SPRITE = ResourceLocation.withDefaultNamespace("container/horse/saddle_slot");
	private static final ResourceLocation INVENTORY_LOCATION = ResourceLocation.parse("textures/gui/container/horse.png");
	private final WhiteWalkieEntity whiteWalkie;

	public WhiteWalkieStorageScreen(WhiteWalkieStorageContainer var1, Inventory var2) {
		super((AbstractContainerMenu)(var1),var2,var1.getWhiteWalkie().getDisplayName());
		this.whiteWalkie = var1.getWhiteWalkie();
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		super.render(var1,var2,var3,var4);
		this.renderTooltip(var1,var2,var3);
	}
	protected void renderBg(GuiGraphics var1, float var2, int var3, int var4) {
		int var5 = ((this.width) - (this.imageWidth)) / (2);
		int var6 = ((this.height) - (this.imageHeight)) / (2);
		var1.blit(INVENTORY_LOCATION,var5,var6,0,0,this.imageWidth,this.imageHeight);
		if (this.whiteWalkie.hasChest()){
			var1.blitSprite(CHEST_SLOTS_SPRITE,90,54,0,0,(var5) + (79),(var6) + (17),(this.whiteWalkie.getInventoryColumns()) * (18),54);
		}
		if (this.whiteWalkie.isTame()){
			var1.blitSprite(SADDLE_SLOT_SPRITE,(var5) + (7),((var6) + (35)) - (18),18,18);
		}
		InventoryScreen.renderEntityInInventoryFollowsAngle(var1,(var5) - (50),(var6) - (60),(var5) + (150),(var6) + (210),13,-2F,(float)(Math.toRadians(80D)),(float)(Math.toRadians(5D)),(LivingEntity)(this.whiteWalkie));
	}
	protected void init() {
		super.init();
		this.clearWidgets();
		int var1 = ((this.width) - (this.imageWidth)) / (2);
		int var2 = ((this.height) - (this.imageHeight)) / (2);
		WhiteWalkieStorageScreen var3 = this;
		Button.OnPress var4 = (l0) -> {
			this.whiteWalkie.setInventoryPage(0);
			ModNetwork.sendToServer(new CUpdateInventoryPagePacket(this.whiteWalkie.getId(),0));
			this.init();
		};
		PlankButton var5 = new PlankButton((var1) + (80),(var2) + (2),14,14,(Component)(Component.literal("1")),var4);
		if (this.whiteWalkie.hasChest()){
			this.addRenderableWidget((GuiEventListener)(var5));
		}
		if ((this.whiteWalkie.getInventoryPage()) == (0)){
			var5.active = false;
		}
		WhiteWalkieStorageScreen var6 = this;
		Button.OnPress var7 = (l0) -> {
			this.whiteWalkie.setInventoryPage(1);
			ModNetwork.sendToServer(new CUpdateInventoryPagePacket(this.whiteWalkie.getId(),1));
			this.init();
		};
		PlankButton var8 = new PlankButton((var1) + (95),(var2) + (2),14,14,(Component)(Component.literal("2")),var7);
		if ((this.whiteWalkie.getChests()) > (1)){
			this.addRenderableWidget((GuiEventListener)(var8));
		}
		if ((this.whiteWalkie.getInventoryPage()) == (1)){
			var8.active = false;
		}
	}
}
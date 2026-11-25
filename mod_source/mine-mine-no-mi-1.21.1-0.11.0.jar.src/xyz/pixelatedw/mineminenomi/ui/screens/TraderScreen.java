package xyz.pixelatedw.mineminenomi.ui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.TradeEntry;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.ui.FlickeringString;
import xyz.pixelatedw.mineminenomi.api.ui.SequencedString;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.entities.mobs.TraderEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.SkypieanTraderEntity;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.trade.CBuyFromTraderPacket;
import xyz.pixelatedw.mineminenomi.packets.client.trade.CSellToTraderPacket;
import xyz.pixelatedw.mineminenomi.ui.panel.TradeItemListScrollPanel;
import xyz.pixelatedw.mineminenomi.ui.widgets.FactionButton;
import xyz.pixelatedw.mineminenomi.ui.widgets.PlankButton;

public class TraderScreen extends Screen {
	// Fields
	private static final Quaternionf TRADER_ANGLE = new Quaternionf().rotationXYZ(0F,-40F,3.1415927F);
	protected static final ResourceLocation RECIPE_BOOK = ResourceLocation.parse("textures/gui/recipe_book.png");
	private TraderScreen$TradeState guiState;
	private LocalPlayer player;
	private EntityStatsData statsData;
	private TraderEntity trader;
	private int wantedAmount;
	private TradeEntry selectedStack;
	private TradeEntry previousStack;
	private TradeEntry hoveredStack;
	private SequencedString startMessage;
	private FlickeringString skipMessage;
	private EditBox quantityEdit;
	private TradeItemListScrollPanel listPanel;
	private StateSwitchingButton vearthAmountUp;
	private StateSwitchingButton vearthAmountDown;
	private int dirtBlocksAvailable;

	public TraderScreen(TraderEntity var1) {
		super((Component)(Component.empty()));
		this.guiState = TraderScreen$TradeState.Welcome;
		this.wantedAmount = 1;
		this.player = Minecraft.getInstance().player;
		this.statsData = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(this.player)))).orElse((Object)(null))));
		this.trader = var1;
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		this.renderBackground(var1,var2,var3,var4);
		RenderSystem.setShaderColor(1F,1F,1F,1F);
		switch (this.guiState.ordinal()){
		default:
			this.renderMenu(var1,var2,var3,var4);
			break;
		case 3:
			this.renderSellShop(var1,var2,var3,var4);
			break;
		case 2:
			this.renderBuyShop(var1,var2,var3,var4);
			break;
		}
		Iterator var5 = this.renderables.iterator();
		do{
			if (var5.hasNext()){
				((Renderable)(var5.next())).render(var1,var2,var3,var4);
				continue;
			}else{
				break;
			}
		} while (true);
	}
	private void renderMenu(GuiGraphics var1, int var2, int var3, float var4) {
		int var5 = (this.width) / (2);
		int var6 = (this.height) / (2);
		if (!(this.trader.canBuyFromPlayers())){
			this.startMessage.render(var1,this.getMinecraft().font,(var5) - (200),(var6) - (50),var4);
			this.skipMessage.render(var1,(var5) - (100),(var6) + (60),var4);
			if ((((float)(this.startMessage.getTickCount())) * (var4)) > ((float)(this.startMessage.getDelayTicks()))){
				this.switchToState(TraderScreen$TradeState.Sell);
			}
		}else{
			this.startMessage.render(var1,this.getMinecraft().font,(var5) - (150),(var6) - (105),var4);
		}
		this.renderTrader(var1,var5,var6);
	}
	private void renderSellShop(GuiGraphics var1, int var2, int var3, float var4) {
		int var5 = (this.width) / (2);
		int var6 = (this.height) / (2);
		var1.blit(ModResources.BLANK2,(var5) - (128),(var6) - (110),0,0,256,256);
		this.renderUpperColumn(var1);
		this.drawSizedString(var1,ModI18n.GUI_NAME,(var5) - (20),(var6) - (63),0.9F,-1);
		this.drawSizedString(var1,ModI18n.GUI_PRICE,(var5) + (50),(var6) - (63),0.9F,-1);
		int var7 = ((this.trader.getCurrency()) == (Currency.BELLY)) ? (0) : (34);
		var1.blit(ModResources.CURRENCIES,(var5) + (53),(var6) - (76),var7,32,32,64);
		this.hover(var2,var3);
	}
	private void renderBuyShop(GuiGraphics var1, int var2, int var3, float var4) {
		int var5 = (this.width) / (2);
		int var6 = (this.height) / (2);
		this.startMessage.render(var1,this.font,(var5) - (150),(var6) - (105),var4);
		var5 = (var5) - (110);
		TraderEntity var7 = this.trader;
		if ((var7 instanceof SkypieanTraderEntity) && ((((SkypieanTraderEntity)(var7)).getTradesLeft()) > (0))){
			var1.renderItem(new ItemStack((ItemLike)(Blocks.DIRT.asItem())),(var5) - (20),(var6) - (11));
			int var8 = this.wantedAmount;
			String var9 = "" + var8;
			String var10 = var9;
			RendererHelper.drawStringWithBorder(this.font,var1,var10,var5,(var6) - (7),-1);
			RendererHelper.drawStringWithBorder(this.font,var1,"=",(var5) + (60),(var6) - (7),-1);
			int var11 = Math.round(((float)(Currency.getExtolFromBelly((long)(this.wantedAmount)))) * (10F));
			String var12 = "" + var11;
			RendererHelper.drawStringWithBorder(this.font,var1,var12,(var5) + (100),(var6) - (7),-1);
			var1.blit(ModResources.CURRENCIES,(var5) + (75),(var6) - (21),34,32,32,64);
		}
		var5 = (var5) + (110);
		this.renderTrader(var1,var5,var6);
	}
	public void renderUpperColumn(GuiGraphics var1) {
		int var2 = (this.width) / (2);
		int var3 = (this.height) / (2);
		String var4 = "";
		TradeEntry var5 = ((this.hoveredStack) != (null)) ? (this.hoveredStack) : ((TradeEntry)(this.getSelectedStack()));
		if ((var5) != (null)){
			this.quantityEdit.render(var1,0,0,0F);
			RendererHelper.drawIcon(ModResources.BLANK,var1.pose(),(float)((var2) - (117)),(float)((var3) - (105)),1F,32F,42F);
			var1.renderItem(var5.itemStack(),(var2) - (110),(var3) - (100));
			int var6 = var5.getCount();
			String var7 = "/" + var6;
			var4 = var7;
			if (var5.hasInfiniteStock()){
				var4 = "/∞";
			}
		}
		long var6_1 = ((this.trader.getCurrency()) == (Currency.BELLY)) ? (this.statsData.getBelly()) : (this.statsData.getExtol());
		this.drawSizedString(var1,(Component)(Component.literal(var4)),(var2) - (45),(var3) - (94),1.2F,-1);
		long var7_1 = var6_1;
		String var8 = "" + var7_1;
		this.drawSizedString(var1,(Component)(Component.literal(var8)),(var2) + (85),(var3) - (95),0.9F,-1);
		int var9 = ((this.trader.getCurrency()) == (Currency.BELLY)) ? (0) : (34);
		var1.blit(ModResources.CURRENCIES,(var2) + (102),(var3) - (108),var9,32,32,64);
	}
	public void init() {
		if ((this.statsData) == (null)){
			this.onClose();
			return;
		}else{
			this.setupScreen();
			return;
		}
	}
	private void setupScreen() {
		int var1 = (this.width) / (2);
		int var2 = (this.height) / (2);
		this.clearWidgets();
		switch (this.guiState.ordinal()){
		case 0:
			Component var3 = null;
			if (this.trader.canTrade((Player)(this.player))){
				var3 = ModI18n.TRADER_WELCOME_MESSAGE;
			}else{
				var3 = this.trader.getTradeFailMessage();
			}
			String var4 = var3.getString();
			this.startMessage = new SequencedString(var4,250,(this.font.width(var4)) / (2));
			if ((this.skipMessage) == (null)){
				this.skipMessage = new FlickeringString(ModI18n.GUI_CLICK_TO_SKIP,20);
			}
			if (this.trader.canBuyFromPlayers()){
				TraderScreen var5 = this;
				Button.OnPress var6 = (l0) -> {
			this.switchToState(TraderScreen$TradeState.Sell);
		};
				FactionButton var7 = new FactionButton((var1) - (180),(var2) - (50),100,20,ModI18n.GUI_BUY,var6);
				this.addRenderableWidget((GuiEventListener)(var7));
				TraderScreen var8 = this;
				Button.OnPress var9 = (l0) -> {
			this.switchToState(TraderScreen$TradeState.Buy);
		};
				this.addRenderableWidget((GuiEventListener)(new FactionButton((var1) - (180),(var2) - (20),100,20,ModI18n.GUI_SELL,var9)));
				break;
			}else{
				break;
			}
		case 3:
			this.listPanel = new TradeItemListScrollPanel(this,this.trader.getTradingItems());
			this.addRenderableWidget((GuiEventListener)(this.listPanel));
			this.quantityEdit = new EditBox(this.font,(var1) - (80),(var2) - (100),20,20,(Component)(Component.empty()));
			this.quantityEdit.setMaxLength(2);
			this.quantityEdit.insertText("1");
			this.addRenderableWidget((GuiEventListener)(this.quantityEdit));
			this.addRenderableWidget((GuiEventListener)(new PlankButton((var1) - (10),(var2) - (100),64,22,ModI18n.GUI_BUY,(Button.OnPress)(((net.minecraft.client.gui.components.Button.OnPress)(this::onBuy))))));
			if (this.trader.canBuyFromPlayers()){
				TraderScreen var5_1 = this;
				Button.OnPress var6_1 = (l0) -> {
			this.switchToState(TraderScreen$TradeState.Welcome);
		};
				this.addRenderableWidget((GuiEventListener)(new FactionButton((var1) - (200),(var2) + (78),70,20,ModI18n.GUI_CANCEL,var6_1)));
				break;
			}else{
				break;
			}
		case 2:
			TraderEntity var5_2 = this.trader;
			if (var5_2 instanceof SkypieanTraderEntity){
				SkypieanTraderEntity var6_2 = ((SkypieanTraderEntity)(var5_2));
				this.wantedAmount = 0;
				Object[] var7_1 = new Object[]{Integer.valueOf(Math.round(100000F)),Long.valueOf(var6_2.getExtolLeftInStock())};
				String var8_1 = Component.translatable(ModI18n.TRADER_SKYPIEAN_VEARTH,var7_1).getString();
				if ((var6_2.getTradesLeft()) <= (0)){
					var8_1 = ModI18n.TRADER_SKYPIEAN_NO_EXTOL.getString();
				}
				this.startMessage = new SequencedString(var8_1,250,(this.font.width(var8_1)) / (3),5000000);
				if ((var6_2.getTradesLeft()) > (0)){
					TraderScreen var9_1 = this;
					Button.OnPress var10 = (l0) -> {
			ModNetwork.sendToServer(new CSellToTraderPacket(this.trader.getId(),this.wantedAmount));
			Minecraft.getInstance().setScreen((Screen)(null));
		};
					FactionButton var11 = new FactionButton((var1) - (80),(var2) + (20),70,20,ModI18n.GUI_SELL,var10);
					this.addRenderableWidget((GuiEventListener)(var11));
				}
				TraderScreen var9_2 = this;
				Button.OnPress var10_1 = (l0) -> {
			this.switchToState(TraderScreen$TradeState.Welcome);
		};
				FactionButton var11_1 = new FactionButton((var1) - (200),(var2) + (78),70,20,ModI18n.GUI_CANCEL,var10_1);
				this.addRenderableWidget((GuiEventListener)(var11_1));
				this.dirtBlocksAvailable = 0;
				int var12 = 0;
				do{
					if ((var12) < (this.player.getInventory().getContainerSize())){
						ItemStack var13 = this.player.getInventory().getItem(var12);
						if (((var13) != (null)) && ((!(var13.isEmpty())) && (var13.is(ItemTags.DIRT)))){
							this.dirtBlocksAvailable = (this.dirtBlocksAvailable) + (var13.getCount());
						}
						var12++;
						continue;
					}else{
						break;
					}
				} while (true);
				this.vearthAmountUp = new StateSwitchingButton((var1) - (80),(var2) - (11),12,17,false);
				this.vearthAmountDown = new StateSwitchingButton((var1) - (145),(var2) - (11),12,17,true);
				if ((var6_2.getTradesLeft()) > (0)){
					this.addRenderableWidget((GuiEventListener)(this.vearthAmountUp));
					this.addRenderableWidget((GuiEventListener)(this.vearthAmountDown));
					break;
				}else{
					break;
				}
			}else{
				break;
			}
		case 1:
			break;
		}
	}
	public boolean mouseClicked(double var1, double var2, int var3) {
		switch (this.guiState.ordinal()){
		case 0:
			if (!(this.trader instanceof SkypieanTraderEntity)){
				this.switchToState(TraderScreen$TradeState.Sell);
				break;
			}else{
				break;
			}
		case 2:
			SkypieanTraderEntity var4 = ((SkypieanTraderEntity)(this.trader));
			if (this.vearthAmountUp.mouseClicked(var1,var2,var3)){
				int var5 = 1;
				if (Screen.hasShiftDown()){
					var5 = 10;
				}
				if (((this.wantedAmount) + (var5)) < (var4.getTradesLeft())){
					if (((this.wantedAmount) + (var5)) <= (this.dirtBlocksAvailable)){
						this.wantedAmount = (this.wantedAmount) + (var5);
						break;
					}else{
						this.wantedAmount = 0;
						break;
					}
				}else{
					this.wantedAmount = var4.getTradesLeft();
					break;
				}
			}else{
				if (this.vearthAmountDown.mouseClicked(var1,var2,var3)){
					int var5_1 = 1;
					if (Screen.hasShiftDown()){
						var5_1 = 10;
					}
					if (((this.wantedAmount) - (var5_1)) >= (0)){
						this.wantedAmount = (this.wantedAmount) - (var5_1);
						break;
					}else{
						this.wantedAmount = var4.getTradesLeft();
						break;
					}
				}else{
					break;
				}
			}
		}
		return super.mouseClicked(var1,var2,var3);
	}
	public void resize(Minecraft var1, int var2, int var3) {
		if ((this.quantityEdit) != (null)){
			String var4 = this.quantityEdit.getValue();
			this.init(var1,var2,var3);
			this.quantityEdit.insertText(var4);
		}
	}
	public void tick() {
		if (((this.quantityEdit) != (null)) && ((this.getSelectedStack()) != (null))){
			try{
				int var1 = Integer.parseInt(this.quantityEdit.getValue());
				boolean var2 = false;
				if (((this.hoveredStack) != (null)) & ((this.hoveredStack) != (this.getSelectedStack()))){
					var1 = 1;
					var2 = true;
				}
				if (((var1) == (this.wantedAmount)) && (((this.getSelectedStack()) == (this.previousStack)) && (!(var2)))){
					return;
				}else{
					if (!(this.getSelectedStack().hasInfiniteStock())){
						var1 = Mth.clamp(var1,0,this.getSelectedStack().getCount());
					}
					int var3 = this.quantityEdit.getCursorPosition();
					int var4 = var1;
					String var5 = "" + var4;
					this.quantityEdit.setValue(var5);
					this.quantityEdit.moveCursorTo(var3,false);
					this.wantedAmount = var1;
					this.previousStack = this.getSelectedStack();
				}
			}catch(Exception var1_1){
				this.quantityEdit.setValue("");
			}
		}
	}
	private void switchToState(TraderScreen$TradeState var1) {
		if (this.trader.canTrade((Player)(this.player))){
			this.guiState = var1;
			this.init(this.getMinecraft(),this.width,this.height);
		}else{
			this.onClose();
		}
	}
	public void onBuy(Button var1) {
		if ((this.getSelectedStack()) == (null)){
			return;
		}else{
			if (((this.getWantedAmount()) > (this.getSelectedStack().getCount())) && (!(this.getSelectedStack().hasInfiniteStock()))){
				return;
			}else{
				if ((this.getEmptySlots()) < (this.calculateSlotsFromCount(this.getWantedAmount()))){
					return;
				}else{
					int var2 = (this.getSelectedStack().getPrice()) * (this.getWantedAmount());
					if ((((this.trader.getCurrency()) == (Currency.BELLY)) ? (this.statsData.getBelly()) : (this.statsData.getExtol())) < ((long)(var2))){
						return;
					}else{
						ModNetwork.sendToServer(new CBuyFromTraderPacket(this.trader.getId(),this.getSelectedStack().itemStack(),this.getWantedAmount()));
						if (!(this.getSelectedStack().hasInfiniteStock())){
							int var3 = (this.getSelectedStack().getCount()) - (this.wantedAmount);
							if ((var3) <= (0)){
								this.trader.getTradingItems().remove(this.getSelectedStack());
								this.setSelectedStack((TradeEntry)(null));
								this.setWantedAmount(1);
								int var4 = this.wantedAmount;
								String var5 = "" + var4;
								this.quantityEdit.setValue(var5);
							}else{
								this.getSelectedStack().setCount(var3);
							}
						}
						return;
					}
				}
			}
		}
	}
	public void onIncreaseQuantity(Button var1) {
		if (((this.getSelectedStack()) != (null)) && (!(((this.getWantedAmount()) >= (this.getSelectedStack().getCount())) && (!(this.getSelectedStack().hasInfiniteStock()))))){
			this.setWantedAmount((this.getWantedAmount()) + (1));
		}
	}
	public void onDecreaseQuantity(Button var1) {
		if (((this.getSelectedStack()) != (null)) && ((this.getWantedAmount()) > (1))){
			this.setWantedAmount((this.getWantedAmount()) - (1));
		}
	}
	public void drawSizedString(GuiGraphics var1, Component var2, int var3, int var4, float var5, int var6) {
		var1.pose().pushPose();
		var1.pose().translate((float)(var3),(float)(var4),0F);
		var1.pose().scale(var5,var5,var5);
		if ((var6) == (-1)){
			var6 = 16777215;
		}
		var1.drawCenteredString(this.font,var2,0,0,var6);
		var1.pose().popPose();
	}
	public void hover(int var1, int var2) {
		TradeEntry var3 = this.listPanel.findStackEntry(var1,var2);
		if ((var3) != (null)){
			this.hoveredStack = var3;
			this.setWantedAmount(1);
		}else{
			this.hoveredStack = null;
		}
	}
	public int getEmptySlots() {
		int var1 = 0;
		Iterator var2 = this.player.getInventory().items.iterator();
		do{
			if (var2.hasNext()){
				if (((ItemStack)(var2.next())).isEmpty()){
					var1++;
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return var1;
	}
	public int calculateSlotsFromCount(int var1) {
		return Mth.ceil(((double)(var1)) / (64D));
	}
	public List<Integer> getStacks(int var1) {
		ArrayList var2 = new ArrayList();
		int var3 = 0;
		int var4 = 0;
		do{
			if ((var4) < (var1)){
				if (((var1) - ((64) * (var3))) < (64)){
					var2.add(Integer.valueOf((var1) - ((64) * (var3))));
				}else{
					var2.add(Integer.valueOf(64));
				}
				var3++;
				var4 = (var4) + (64);
				continue;
			}else{
				break;
			}
		} while (true);
		return var2;
	}
	public void renderTrader(GuiGraphics var1, int var2, int var3) {
		var1.pose().pushPose();
		RenderSystem.setShaderColor(1F,1F,1F,1F);
		RenderSystem.enableBlend();
		InventoryScreen.renderEntityInInventoryFollowsAngle(var1,(var2) + (150),(var3) - (150),(var2) + (300),(var3) + (150),100,0F,(float)(Math.toRadians(40D)),(float)(Math.toRadians(5D)),(LivingEntity)(this.trader));
		RenderSystem.disableBlend();
		var1.pose().popPose();
	}
	public TradeEntry getSelectedStack() {
		return this.selectedStack;
	}
	public void setSelectedStack(TradeEntry var1) {
		this.selectedStack = var1;
	}
	public int getWantedAmount() {
		return this.wantedAmount;
	}
	public void setWantedAmount(int var1) {
		this.wantedAmount = var1;
	}
	public boolean isPauseScreen() {
		return false;
	}
}
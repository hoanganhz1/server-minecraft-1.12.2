package xyz.pixelatedw.mineminenomi.ui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement$LayerType;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.crew.CUpdateJollyRogerPacket;
import xyz.pixelatedw.mineminenomi.ui.components.JollyRogerElementColorComponent;
import xyz.pixelatedw.mineminenomi.ui.panel.JollyRogerElementsScrollPanel;
import xyz.pixelatedw.mineminenomi.ui.widgets.PlankButton;

public class JollyRogerEditorScreen extends Screen {
	// Fields
	private static final Component VERTICAL_MIRROR = Component.literal("|");
	private static final Component HORIZONTAL_MIRROR = Component.literal("―");
	private Player player;
	private final Crew crew;
	private JollyRoger jollyRoger;
	private float animationTime;
	private boolean isSmallScreen;
	private int trueLayerIndex;
	private int selectedLayerIndex;
	private JollyRogerElement$LayerType selectedLayerType;
	private List<Button> layerButtons;
	private JollyRogerElementsScrollPanel elementsPanel;
	private JollyRogerElementColorComponent elementColorComponent;
	private Button flipVertical;
	private Button flipHorizontal;
	private Button moveLayerUp;
	private Button moveLayerDown;
	private final List<JollyRogerElement> availableElements;
	private final List<JollyRogerElement> availableBases;
	private final List<JollyRogerElement> availableDetails;
	private final List<JollyRogerElement> availableBackgrounds;

	public JollyRogerEditorScreen(boolean var1, Crew var2, List<JollyRogerElement> var3) {
		super((Component)(Component.empty()));
		this.animationTime = 0F;
		this.isSmallScreen = false;
		this.selectedLayerType = JollyRogerElement$LayerType.BASE;
		this.layerButtons = new ArrayList();
		this.crew = var2;
		this.availableElements = var3;
		this.availableBases = this.getTotalElementsForType(JollyRogerElement$LayerType.BASE);
		this.availableDetails = this.getTotalElementsForType(JollyRogerElement$LayerType.DETAIL);
		this.availableBackgrounds = this.getTotalElementsForType(JollyRogerElement$LayerType.BACKGROUND);
		this.animationTime = 1F;
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		super.render(var1,var2,var3,var4);
		RenderSystem.setShaderColor(1F,1F,1F,1F);
		int var5 = (this.width) / (2);
		int var6 = (this.height) / (2);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		int var7 = (this.isSmallScreen) ? (-30) : (0);
		if ((this.jollyRoger) != (null)){
			this.jollyRoger.render(var1.pose(),((var5) - (98)) - (var7),(var6) - (64),0.4F);
		}
		var1.pose().pushPose();
		if (((double)(this.animationTime)) < (0.15D)){
			this.animationTime = (float)(((double)(this.animationTime)) + (0.007D));
			float var8 = (0.35F) + (this.animationTime);
			var1.pose().translate((float)(((var5) - (175)) - (var7)),(float)((var6) - (140)),1F);
			var1.pose().translate(128F,128F,0F);
			var1.pose().scale(var8,var8,var8);
			var1.pose().translate(-128F,-128F,0F);
			JollyRogerElement var9 = this.getLayerElement();
			if ((var9) != (null)){
				float var10 = 1F;
				float var11 = 0F;
				float var12 = 0F;
				if (var9.canBeColored()){
					var10 = var9.getInversedRedF();
					var11 = var9.getInversedGreenF();
					var12 = var9.getInversedBlueF();
				}
				RenderSystem.setShaderColor(var10,var11,var12,(0.9F) - ((this.animationTime) * (4F)));
				this.jollyRoger.drawElement(var9,var1.pose(),0,0,1F,false);
				RenderSystem.setShaderColor(1F,1F,1F,1F);
			}
		}
		var1.pose().popPose();
		RenderSystem.disableBlend();
		RenderSystem.setShaderColor(1F,1F,1F,1F);
	}
	public void init() {
		int var10 = 0;
		this.player = this.minecraft.player;
		this.jollyRoger = this.crew.getJollyRoger();
		int var1 = 0;
		int var2 = (this.height) / (2);
		this.layerButtons.clear();
		int var3 = 3;
		if ((this.width) < (640)){
			var3 = 2;
			this.isSmallScreen = true;
		}
		int var4 = (var3) * (72);
		this.elementsPanel = new JollyRogerElementsScrollPanel(this,var4,var3);
		this.addRenderableWidget((GuiEventListener)(this.elementsPanel));
		this.elementsPanel.setFocused(true);
		this.elementColorComponent = new JollyRogerElementColorComponent(this,(this.width) - (200),(this.height) - (100));
		this.elementColorComponent.setVisible(true);
		this.addRenderableWidget((GuiEventListener)(this.elementColorComponent));
		this.updateElementComponents(this.jollyRoger.getBase());
		int var5 = (var2) - (85);
		JollyRogerEditorScreen var6 = this;
		Button.OnPress var7 = (l0) -> {
			this.selectLayer((Button)(l0),0,JollyRogerElement$LayerType.BASE);
		};
		this.addLayerButton((Button)(new PlankButton((var1) + (5),var5,115,16,ModI18n.GUI_BASE,var7)));
		int var8 = this.jollyRoger.getBackgrounds().length;
		int var9 = 0;
		do{
			if ((var9) < (var8)){
				var10 = (var9) + (1);
				String var11 = ModI18n.GUI_BACKGROUND.getString();
				int var12 = (var9) + (1);
				String var13 = var11 + " " + var12;
				JollyRogerEditorScreen var14 = this;
				int var15 = var10;
				final int var15_f1 = var15;
				Button.OnPress var16 = (l0) -> {
			this.selectLayer((Button)(l0),var15_f1,JollyRogerElement$LayerType.BACKGROUND);
		};
				PlankButton var17 = new PlankButton((var1) + (5),((var5) + (20)) + ((var9) * (20)),115,16,(Component)(Component.literal(var13)),var16);
				this.addLayerButton((Button)(var17));
				var9++;
				continue;
			}else{
				break;
			}
		} while (true);
		var9 = 0;
		do{
			if ((var9) < (this.jollyRoger.getDetails().length)){
				var10 = ((var9) + (1)) + (var8);
				String var11_1 = ModI18n.GUI_DETAIL.getString();
				int var12_1 = (var9) + (1);
				String var13_1 = var11_1 + " " + var12_1;
				JollyRogerEditorScreen var14_1 = this;
				int var15_1 = var10;
				final int var15_1_f1 = var15_1;
				Button.OnPress var16_1 = (l0) -> {
			this.selectLayer((Button)(l0),var15_1_f1,JollyRogerElement$LayerType.DETAIL);
		};
				PlankButton var17_1 = new PlankButton((var1) + (5),((var5) + (60)) + ((var9) * (20)),115,16,(Component)(Component.literal(var13_1)),var16_1);
				this.addLayerButton((Button)(var17_1));
				var9++;
				continue;
			}else{
				break;
			}
		} while (true);
		Button var10_1 = ((Button)(this.layerButtons.get(this.trueLayerIndex)));
		if ((var10_1) != (null)){
			var10_1.active = false;
		}
		this.updateLayerMoveButtons();
	}
	public void onClose() {
		ModNetwork.sendToServer(new CUpdateJollyRogerPacket(this.jollyRoger));
		super.onClose();
	}
	public boolean mouseDragged(double var1, double var2, int var3, double var4, double var5) {
		if (this.isDragging()){
			JollyRogerElement var6 = this.getLayerElement();
			if (((var6) != (null)) && (var6.canBeColored())){
				var6.setColor(this.elementColorComponent.getRed(),this.elementColorComponent.getGreen(),this.elementColorComponent.getBlue());
			}
		}
		return super.mouseDragged(var1,var2,var3,var4,var5);
	}
	public void flipLayer(Button var1, boolean var2, boolean var3) {
		JollyRogerElement var4 = this.getLayerElement();
		if ((var4) != (null)){
			if ((var2) && (var4.canBeFlippedVertically())){
				var4.setFlipVertically(!(var4.isFlippedVertically()));
			}
			if ((var3) && (var4.canBeFlippedHorizontally())){
				var4.setFlipHorizontally(!(var4.isFlippedHorizontally()));
			}else{
				return;
			}
		}
	}
	public void selectLayer(Button var1, int var2, JollyRogerElement$LayerType var3) {
		Iterator var4 = this.layerButtons.iterator();
		do{
			if (var4.hasNext()){
				Button var5 = ((Button)(var4.next()));
				var5.active = true;
				continue;
			}else{
				break;
			}
		} while (true);
		Button var5_1 = ((Button)(this.layerButtons.get(var2)));
		var5_1.active = false;
		this.trueLayerIndex = var2;
		int var6 = this.jollyRoger.getBackgrounds().length;
		if (!(var3.equals(this.selectedLayerType))){
			this.elementsPanel.setEntries(var3);
		}
		this.selectedLayerType = var3;
		switch (JollyRogerEditorScreen$1.$SwitchMap$xyz$pixelatedw$mineminenomi$api$crew$JollyRogerElement$LayerType[var3.ordinal()]){
		default:
			throw new MatchException((String)(null),(Throwable)(null));
		case 1:

		case 2:

		case 3:
			this.selectedLayerIndex = ((var2) - (1)) - (var6);
			this.updateElementComponents(this.getLayerElement());
			this.animationTime = 0F;
			this.updateLayerMoveButtons();
			return;
		}
	}
	public void moveLayer(Button var1, boolean var2) {
		int var3 = (this.selectedLayerIndex) + ((var2) ? (-1) : (1));
		int var4 = this.jollyRoger.getBackgrounds().length;
		switch (JollyRogerEditorScreen$1.$SwitchMap$xyz$pixelatedw$mineminenomi$api$crew$JollyRogerElement$LayerType[this.getSelectedLayerType().ordinal()]){
		case 1:
			break;
		case 3:
			JollyRogerElement var5 = this.jollyRoger.getDetails()[this.selectedLayerIndex];
			this.jollyRoger.getDetails()[this.selectedLayerIndex] = this.jollyRoger.getDetails()[var3];
			this.jollyRoger.getDetails()[var3] = var5;
			this.selectLayer(var1,((var3) + (1)) + (var4),this.selectedLayerType);
			break;
		case 2:
			JollyRogerElement var6 = this.jollyRoger.getBackgrounds()[this.selectedLayerIndex];
			this.jollyRoger.getBackgrounds()[this.selectedLayerIndex] = this.jollyRoger.getBackgrounds()[var3];
			this.jollyRoger.getBackgrounds()[var3] = var6;
			this.selectLayer(var1,(var3) + (1),this.selectedLayerType);
			break;
		}
	}
	public void updateJollyRoger(JollyRogerElement var1) {
		switch (JollyRogerEditorScreen$1.$SwitchMap$xyz$pixelatedw$mineminenomi$api$crew$JollyRogerElement$LayerType[this.selectedLayerType.ordinal()]){
		case 1:
			if (this.jollyRoger.getBase().equals(var1)){

			}else{
				this.jollyRoger.setBase(var1);
				int var2 = 0;
				do{
					if ((var2) < (this.jollyRoger.getDetails().length)){
						JollyRogerElement var3 = this.jollyRoger.getDetails()[var2];
						if (((var3) != (null)) && (!(var3.canUse(this.player,this.crew)))){
							this.jollyRoger.setDetail(var2,(JollyRogerElement)(null));
						}
						var2++;
						continue;
					}else{
						break;
					}
				} while (true);
				var2 = 0;
				do{
					if ((var2) < (this.jollyRoger.getBackgrounds().length)){
						JollyRogerElement var3_1 = this.jollyRoger.getBackgrounds()[var2];
						if (((var3_1) != (null)) && (!(var3_1.canUse(this.player,this.crew)))){
							this.jollyRoger.setBackground(var2,(JollyRogerElement)(null));
						}
						var2++;
						continue;
					}else{
						break;
					}
				} while (true);
			}
		case 3:

		case 2:

		default:
			if (0){
				this.updateElementComponents(var1);
			}
			return;
		}
	}
	public void updateLayerMoveButtons() {
		int var1 = 0;
		int var2 = (((this.height) / (2)) - (85)) + ((this.trueLayerIndex) * (20));
		if ((this.moveLayerUp) != (null)){
			this.renderables.remove(this.moveLayerUp);
			this.removeWidget((GuiEventListener)(this.moveLayerUp));
		}
		if ((this.moveLayerDown) != (null)){
			this.renderables.remove(this.moveLayerDown);
			this.removeWidget((GuiEventListener)(this.moveLayerDown));
		}
		int var3 = ((this.getSelectedLayerType()) == (JollyRogerElement$LayerType.BASE)) ? (1) : (0);
		int var4 = ((this.selectedLayerIndex) == (0)) ? (1) : (0);
		int var5 = ((this.selectedLayerIndex) == ((((this.getSelectedLayerType()) == (JollyRogerElement$LayerType.BACKGROUND)) ? (2) : (5)) - (1))) ? (1) : (0);
		if ((var3) != (0)){
			return;
		}else{
			if ((var4) == (0)){
				JollyRogerEditorScreen var6 = this;
				Button.OnPress var7 = (l0) -> {
			this.moveLayer((Button)(l0),true);
		};
				this.moveLayerUp = new PlankButton((var1) + (125),(var2) - (7),16,16,(Component)(Component.literal("+")),var7);
				this.addRenderableWidget((GuiEventListener)(this.moveLayerUp));
			}
			if ((var5) == (0)){
				JollyRogerEditorScreen var6_1 = this;
				Button.OnPress var7_1 = (l0) -> {
			this.moveLayer((Button)(l0),false);
		};
				this.moveLayerDown = new PlankButton((var1) + (125),(var2) + (7),16,16,(Component)(Component.literal("-")),var7_1);
				this.addRenderableWidget((GuiEventListener)(this.moveLayerDown));
			}
			return;
		}
	}
	public void updateElementComponents(@Nullable() JollyRogerElement var1) {
		if ((var1) != (null)){
			this.elementColorComponent.setVisible(var1.canBeColored());
			if ((this.flipVertical) != (null)){
				this.renderables.remove(this.flipVertical);
				this.removeWidget((GuiEventListener)(this.flipVertical));
			}
			if ((this.flipHorizontal) != (null)){
				this.renderables.remove(this.flipHorizontal);
				this.removeWidget((GuiEventListener)(this.flipHorizontal));
			}
			if (var1.canBeFlippedVertically()){
				JollyRogerEditorScreen var2 = this;
				Button.OnPress var3 = (l0) -> {
			this.flipLayer((Button)(l0),true,false);
		};
				this.flipVertical = new PlankButton((this.width) - (210),(this.height) - (100),16,16,VERTICAL_MIRROR,var3);
				this.addRenderableWidget((GuiEventListener)(this.flipVertical));
			}
			if (var1.canBeFlippedHorizontally()){
				JollyRogerEditorScreen var2_1 = this;
				Button.OnPress var3_1 = (l0) -> {
			this.flipLayer((Button)(l0),false,true);
		};
				this.flipHorizontal = new PlankButton((this.width) - (180),(this.height) - (100),16,16,HORIZONTAL_MIRROR,var3_1);
				this.addRenderableWidget((GuiEventListener)(this.flipHorizontal));
			}
		}
		this.elementColorComponent.setRGBValues(var1);
	}
	private JollyRogerElement getLayerElement() {
		switch (JollyRogerEditorScreen$1.$SwitchMap$xyz$pixelatedw$mineminenomi$api$crew$JollyRogerElement$LayerType[this.selectedLayerType.ordinal()]){
		default:
			throw new MatchException((String)(null),(Throwable)(null));
		case 1:

		case 3:
			return this.jollyRoger.getDetails()[this.selectedLayerIndex];
		case 2:

		}
	}
	public List<JollyRogerElement> getListFromType(JollyRogerElement$LayerType var1) {
		switch (JollyRogerEditorScreen$1.$SwitchMap$xyz$pixelatedw$mineminenomi$api$crew$JollyRogerElement$LayerType[var1.ordinal()]){
		default:
			throw new MatchException((String)(null),(Throwable)(null));
		case 1:

		case 3:
			return this.availableDetails;
		case 2:

		}
	}
	public List<JollyRogerElement> getTotalElementsForType(JollyRogerElement$LayerType var1) {
		JollyRogerElement$LayerType var2 = var1;
		final JollyRogerElement$LayerType var2_f1 = var2;
		Predicate<JollyRogerElement> var3 = (l0) -> {
			return (l0.getLayerType()) == (var2_f1);
		};
		return (List<JollyRogerElement>) (List) (((java.util.stream.Stream)(this.availableElements.stream())).filter((Predicate)(var3)).toList());
	}
	private void addLayerButton(Button var1) {
		this.layerButtons.add(var1);
		super.addRenderableWidget((GuiEventListener)(var1));
	}
	public Player getPlayer() {
		return this.player;
	}
	public Crew getCrew() {
		return this.crew;
	}
	public JollyRogerElement$LayerType getSelectedLayerType() {
		return this.selectedLayerType;
	}
	public boolean isSmallScreen() {
		return this.isSmallScreen;
	}
}
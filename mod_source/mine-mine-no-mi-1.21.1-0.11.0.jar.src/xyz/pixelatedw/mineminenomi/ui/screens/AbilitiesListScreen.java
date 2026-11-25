package xyz.pixelatedw.mineminenomi.ui.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCoreUnlockWrapper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IDescriptiveAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.handlers.ui.CombatModeHandler;
import xyz.pixelatedw.mineminenomi.init.ModKeybindings;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nTutorials;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CEquipAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CRemoveAbilityPacket;
import xyz.pixelatedw.mineminenomi.ui.panel.AbilitiesListScrollPanel;
import xyz.pixelatedw.mineminenomi.ui.widgets.AbilitySlotButton;
import xyz.pixelatedw.mineminenomi.ui.widgets.AnimatedTextButton;
import xyz.pixelatedw.mineminenomi.ui.widgets.BookSignSideButton;

public class AbilitiesListScreen extends Screen {
	// Fields
	private static final int TOOLTIP_BACKGROUND_START;
	private static final int TOOLTIP_BACKGROUND_END;
	private static final int TOOLTIP_BORDER_START;
	private static final int TOOLTIP_BORDER_END;
	protected AbstractClientPlayer player;
	private AbilitiesListScrollPanel abilitiesList;
	public int groupSelected;
	public int slotSelected;
	public boolean isDirty;
	private int tickCount;
	private AbilityData abilityDataProps;
	protected final List<AbilitySlotButton> abilitySlots;
	private final List<Renderable> backgroundWidgets;
	private final List<Renderable> foregroundWidgets;
	private static final Map<AbilityCore<? extends IAbility>, AbilityTooltip> TOOLTIPS_CACHE;
	private static boolean hasStatsShown;
	private static boolean clearCache;
	private static long lastModeChange;
	private AbilitiesListScreen$Compactness compactness;
	private AbilitiesListScreen$Selection selection;
	private boolean showTooltips;
	private AbilityCore<?> draggedAbility;

	public AbilitiesListScreen() {
		super((Component)(Component.empty()));
		this.groupSelected = 1;
		this.slotSelected = -1;
		this.abilitySlots = Lists.newArrayList();
		this.backgroundWidgets = new ArrayList();
		this.foregroundWidgets = new ArrayList();
		this.draggedAbility = null;
		this.player = Minecraft.getInstance().player;
		this.minecraft = Minecraft.getInstance();
		this.compactness = ClientConfig.getCompactness();
		this.selection = ClientConfig.getSelectionMode();
		this.showTooltips = ClientConfig.getTooltipsShown();
		this.abilityDataProps = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(this.player)))).orElse((Object)(null))));
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		this.renderBackground(var1,var2,var3,var4);
		RenderSystem.setShaderColor(1F,1F,1F,1F);
		int var5 = this.width;
		int var6 = this.height;
		var1.blit(ModResources.BOARD,((var5) - (250)) / (2),((var6) - (230)) / (2),0,0,256,256);
		var1.blit(ModResources.BOARD,((var5) - (250)) / (2),(var6) - (60),0,0,256,256);
		int var7 = 0;
		do{
			if ((var7) < (this.backgroundWidgets.size())){
				((Renderable)(this.backgroundWidgets.get(var7))).render(var1,var2,var3,var4);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
		var1.blit(ModResources.BLANK_NEW,((var5) - (250)) / (2),((var6) - (230)) / (2),0,0,256,256);
		var1.blit(ModResources.BLANK_NEW,((var5) - (250)) / (2),(var6) - (60),0,0,256,256);
		int var8 = (1) + (this.groupSelected);
		String var9 = "" + var8;
		String var10 = var9;
		RendererHelper.drawStringWithBorder(this.minecraft.font,var1,var10,(((var5) + (8)) / (2)) - ((this.minecraft.font.width(var10)) + (104)),(var6) - (24),Color.WHITE.getRGB());
		if ((this.abilitiesList) != (null)){
			this.abilitiesList.render(var1,var2,var3,var4);
		}
		int var11 = 0;
		do{
			if ((var11) < (this.abilitySlots.size())){
				((AbilitySlotButton)(this.abilitySlots.get(var11))).render(var1,var2,var3,var4);
				var11++;
				continue;
			}else{
				break;
			}
		} while (true);
		var11 = 0;
		do{
			if ((var11) < (this.foregroundWidgets.size())){
				((Renderable)(this.foregroundWidgets.get(var11))).render(var1,var2,var3,var4);
				var11++;
				continue;
			}else{
				break;
			}
		} while (true);
		if (this.hasDraggedAbility()){
			RendererHelper.drawIcon(this.getDraggedAbility().getIcon(),var1.pose(),(float)(var2),(float)(var3),1F,16F,16F);
		}
	}
	public void init() {
		AbilityCategory var17 = null;
		int var1 = (this.width) / (2);
		int var2 = (this.height) / (2);
		TOOLTIPS_CACHE.clear();
		this.groupSelected = this.abilityDataProps.getCombatBarSet();
		this.foregroundWidgets.clear();
		this.backgroundWidgets.clear();
		this.updateSlots();
		this.setupOptionsAndHelp();
		int var3 = 0;
		AbilityCategory[] var4 = AbilityCategory.values();
		int var5 = var4.length;
		int var6 = 0;
		do{
			if ((var6) < (var5)){
				AbilityCategory var7 = var4[var6];
				if ((var7) == (AbilityCategory.ALL)){
					var6++;
					continue;
				}else{
					Predicate var8 = (Predicate) ((Predicate<AbilityCore>) ((l0) -> {
			return !(l0.isHidden());
		}));
					AbilityCore var9 = ((AbilityCore)(((java.util.Optional)(((java.util.stream.Stream)(this.abilityDataProps.getUnlockedAbilities().stream().map((Function)(((Function<AbilityCoreUnlockWrapper, AbilityCore>)(AbilityCoreUnlockWrapper::getAbilityCore)))).filter(var8))).filter((Predicate)(var7.isCorePartofCategory())).findFirst())).orElse((Object)(null))));
					if ((var9) != (null)){
						boolean var10 = false;
						int var11 = 0;
						int var12 = 0;
						if ((var3) == (4)){
							var1 = (var1) + (250);
							var2 = (var2) - (140);
						}
						if ((var3) >= (4)){
							var10 = true;
							var11 = 3;
							var12 = 24;
						}
						int var13 = ((var2) - (100)) + (((var3) * (70)) / (2));
						ResourceLocation var14 = var7.getIcon((Player)(this.player));
						if ((var14) == (null)){
							IAbility var15 = this.abilityDataProps.getEquippedOrPassiveAbility(var9);
							if ((var15) != (null)){
								var14 = var15.getIcon((LivingEntity)(this.player));
							}else{
								var14 = var9.getIcon();
							}
						}
						Tooltip var15_1 = Tooltip.create((Component)(Component.literal(WyHelper.capitalize(var7.name()))));
						AbilitiesListScreen var16 = this;
						var17 = var7;
						final AbilityCategory var17_f1 = var17;
						Button.OnPress var18 = (l0) -> {
			this.updateListScreen(var17_f1);
		};
						BookSignSideButton var19 = new BookSignSideButton(((var1) - (165)) + (var12),var13,60,35,(Component)(Component.empty()),var18);
						var19.setTooltip(var15_1);
						var19.setTooltipDelay(Duration.ofMillis(750L));
						var19.setTextureWidth(60);
						var19.setIcon(var14,((var1) - (136)) + (var11),(var13) + (5),1.45F);
						if (var10){
							var19.setFlipped();
						}
						((java.util.List)(this.backgroundWidgets)).add((Object)(var19));
						this.addRenderableWidget((GuiEventListener)(var19));
						var3++;
						var6++;
						continue;
					}else{
						var6++;
						continue;
					}
				}
			}else{
				break;
			}
		} while (true);
		this.updateListScreen(AbilityCategory.DEVIL_FRUITS);
	}
	public void updateSlots() {
		int var1 = this.width;
		int var2 = this.height;
		this.slotSelected = -1;
		Iterator var3 = this.abilitySlots.iterator();
		do{
			if (var3.hasNext()){
				this.removeWidget((GuiEventListener)(((AbilitySlotButton)(var3.next()))));
				continue;
			}else{
				break;
			}
		} while (true);
		this.abilitySlots.clear();
		var1 = (var1) + (10);
		int var4 = 0;
		do{
			if ((var4) < (8)){
				RenderSystem.enableBlend();
				int var5 = this.getRealSlotId(var4);
				IAbility var6 = this.abilityDataProps.getEquippedAbility(var5);
				AbilitiesListScreen var7 = this;
				Button.OnPress var8 = (l0) -> {
			this.onClickAbilitySlot(((AbilitySlotButton)(l0)));
		};
				AbilitySlotButton var9 = new AbilitySlotButton(var6,(((var1) / (2)) - (101)) + ((var4) * (25)),(var2) - (31),22,21,this.player,var8);
				var9.setSlotIndex(var5);
				this.addWidget((GuiEventListener)(var9));
				this.abilitySlots.add(var9);
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	private Tooltip getHelpTooltips() {
		StringBuilder var1 = new StringBuilder();
		if ((this.selection) == (AbilitiesListScreen$Selection.DRAG_AND_DROP)){
			var1.append(ModI18nTutorials.ABILITY_SELECTOR_DRAG_AND_DROP.getString());
		}else{
			if ((this.selection) == (AbilitiesListScreen$Selection.KEYBIND)){
				var1.append(ModI18nTutorials.ABILITY_SELECTOR_KEYBIND.getString());
			}
		}
		var1.append("\n\n");
		if (this.showTooltips){
			var1.append(ModI18nTutorials.ABILITY_SELECTOR_HIDE_TOOLTIPS.getString());
		}else{
			var1.append(ModI18nTutorials.ABILITY_SELECTOR_SHOW_TOOLTIPS.getString());
		}
		return Tooltip.create((Component)(Component.literal(var1.toString())));
	}
	public void setupOptionsAndHelp() {
		int var1 = this.width;
		Button.OnPress var2 = (l0) -> {
		};
		AnimatedTextButton var3 = new AnimatedTextButton((var1) - (20),(this.height) - (20),20,20,ModI18n.GUI_HELP_SHORT,var2);
		var3.setTooltip(this.getHelpTooltips());
		if (ClientConfig.getHelpButtonShown()){
			((java.util.List)(this.foregroundWidgets)).add((Object)(var3));
			this.addRenderableWidget((GuiEventListener)(var3));
		}
		AbilitiesListScreen var4 = this;
		Button.OnPress var5 = (l0) -> {
			this.compactness = this.compactness.nextElement();
			this.abilitiesList.setCompactness(this.compactness);
			l0.setMessage(this.compactness.getShortNotation());
			l0.setTooltip(Tooltip.create(this.compactness.getLabel()));
			ClientConfig.setCompactness(this.compactness);
			l0.setFocused(false);
		};
		Button.OnPress var6 = var5;
		Button var7 = new Button.Builder(this.compactness.getShortNotation(),var6).pos((var1) - (20),0).size(20,20).build();
		var7.setTooltip(Tooltip.create(this.compactness.getLabel()));
		((java.util.List)(this.foregroundWidgets)).add((Object)(var7));
		this.addRenderableWidget((GuiEventListener)(var7));
		AbilitiesListScreen var8 = this;
		AnimatedTextButton var9 = var3;
		final AnimatedTextButton var9_f1 = var9;
		Button.OnPress var10 = (l0) -> {
			this.slotSelected = -1;
			Iterator lv6_3 = this.abilitySlots.iterator();
			do{
				if (lv6_3.hasNext()){
					((AbilitySlotButton)(lv6_3.next())).setIsPressed(false);
					continue;
				}else{
					break;
				}
			} while (true);
			this.selection = this.selection.nextElement();
			l0.setMessage(this.selection.getShortNotation());
			l0.setTooltip(Tooltip.create(this.selection.getLabel()));
			ClientConfig.setSelectionMode(this.selection);
			var9_f1.setMarked();
			var9_f1.setTooltip(this.getHelpTooltips());
			l0.setFocused(false);
		};
		Button.OnPress var11 = var10;
		Button var12 = new Button.Builder(this.selection.getShortNotation(),var11).pos((var1) - (40),0).size(20,20).build();
		var12.setTooltip(Tooltip.create(this.selection.getLabel()));
		((java.util.List)(this.foregroundWidgets)).add((Object)(var12));
		this.addRenderableWidget((GuiEventListener)(var12));
		AbilitiesListScreen var13 = this;
		AnimatedTextButton var14 = var3;
		final AnimatedTextButton var14_f2 = var14;
		Button.OnPress var15 = (l0) -> {
			ClientConfig.toggleShowingTooltips();
			this.showTooltips = ClientConfig.getTooltipsShown();
			l0.setMessage((this.showTooltips) ? (ModI18n.SHOW_TOOLTIPS_ON_LABEL) : (ModI18n.SHOW_TOOLTIPS_OFF_LABEL));
			var14_f2.setMarked();
			var14_f2.setTooltip(this.getHelpTooltips());
		};
		Button.OnPress var16 = var15;
		Button var17 = new Button.Builder((this.showTooltips) ? (ModI18n.SHOW_TOOLTIPS_ON_LABEL) : (ModI18n.SHOW_TOOLTIPS_OFF_LABEL),var16).pos((var1) - (60),0).size(20,20).build();
		var17.setTooltip(Tooltip.create(ModI18n.SHOW_TOOLTIPS_LABEL));
		((java.util.List)(this.foregroundWidgets)).add((Object)(var17));
		this.addRenderableWidget((GuiEventListener)(var17));
	}
	public void onClickAbilitySlot(AbilitySlotButton var1) {
		int var2 = var1.getSlotIndex();
		if ((this.getSelectionMode()) == (AbilitiesListScreen$Selection.DRAG_AND_DROP)){
			IAbility var3 = this.abilityDataProps.getEquippedAbility(var2);
			if ((var3) != (null)){
				this.setDraggedAbility(var3.getCore());
			}
			this.removeAbilityFromSlot(var2);
			return;
		}else{
			if ((this.slotSelected) != (var2)){
				this.slotSelected = var2;
				Iterator var3_1 = this.abilitySlots.iterator();
				do{
					if (var3_1.hasNext()){
						((AbilitySlotButton)(var3_1.next())).setIsPressed(false);
						continue;
					}else{
						break;
					}
				} while (true);
				var1.setIsPressed(true);
			}else{
				if ((this.abilityDataProps.getEquippedAbility(this.slotSelected)) == (null)){
					return;
				}else{
					this.removeAbilityFromSlot(var2);
				}
			}
			return;
		}
	}
	public void tick() {
		if ((this.isDirty) && ((this.tickCount++) >= (1))){
			int var1 = 0;
			Iterator var2 = this.abilitySlots.iterator();
			do{
				if (var2.hasNext()){
					AbilitySlotButton var3 = ((AbilitySlotButton)(var2.next()));
					int var4 = (var1) + ((this.groupSelected) * (8));
					var3.setAbility(this.abilityDataProps.getEquippedAbility(var4));
					var1++;
					continue;
				}else{
					break;
				}
			} while (true);
			this.isDirty = false;
			this.tickCount = 0;
		}
	}
	public boolean keyPressed(int var1, int var2, int var3) {
		this.checkKeybinding(var1);
		return super.keyPressed(var1,var2,var3);
	}
	public boolean mouseClicked(double var1, double var2, int var3) {
		this.checkKeybinding(var3);
		if ((var3) == (1)){
			AbilitySlotButton var4 = this.getHoverSlotIndex(var1,var2);
			if ((var4) != (null)){
				this.slotSelected = var4.getSlotIndex();
				if ((this.slotSelected) >= (0)){
					if ((this.getSelectionMode()) == (AbilitiesListScreen$Selection.KEYBIND)){
						this.removeAbilityFromSlot(this.slotSelected);
						this.slotSelected = -1;
					}else{
						if ((this.getSelectionMode()) == (AbilitiesListScreen$Selection.DRAG_AND_DROP)){
							this.removeAbilityFromSlot(this.slotSelected);
						}
					}
				}
			}
		}
		return super.mouseClicked(var1,var2,var3);
	}
	public boolean mouseReleased(double var1, double var2, int var3) {
		if (((this.getSelectionMode()) == (AbilitiesListScreen$Selection.DRAG_AND_DROP)) && ((this.hasDraggedAbility()) && ((var3) == (0)))){
			AbilitySlotButton var4 = this.getHoverSlotIndex(var1,var2);
			if ((var4) != (null)){
				this.addAbilityToSlot(var4.getSlotIndex(),this.getDraggedAbility());
			}
			this.setDraggedAbility((AbilityCore)(null));
		}
		return true;
	}
	private void checkKeybinding(int var1) {
		if ((this.getSelectionMode()) == (AbilitiesListScreen$Selection.KEYBIND)){
			int var2 = 0;
			LOOP_1:
			do{
				if ((var2) < (ModKeybindings.COMBAT_BAR_KEYS.size())){
					if ((((KeyMapping)(ModKeybindings.COMBAT_BAR_KEYS.get(var2))).getKey().getValue()) == (var1)){
						this.slotSelected = this.getRealSlotId(var2);
						AbilityCore var3 = this.abilitiesList.getHoveredEntry();
						if ((var3) != (null)){
							if (this.abilityDataProps.hasEquippedAbility(var3)){
								this.removeAbilityFromSlot(this.abilityDataProps.getEquippedAbilitySlot(var3));
							}
							this.addAbilityToSlot(this.slotSelected,var3);
							Iterator var4 = this.abilitySlots.iterator();
							do{
								if (var4.hasNext()){
									((AbilitySlotButton)(var4.next())).setIsPressed(false);
									continue;
								}else{
									break;
								}
							} while (true);
							((AbilitySlotButton)(this.abilitySlots.get(var2))).setIsPressed(true);
							break LOOP_1;
						}else{
							break;
						}
					}else{
						var2++;
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
		int var2 = ServerConfig.getAbilityBars();
		int var3_1 = Math.min(var2,CombatModeHandler.abilityBars);
		if ((var1) == (ModKeybindings.NEXT_COMBAT_BAR.getKey().getValue())){
			if ((this.groupSelected) < ((var3_1) - (1))){
				this.groupSelected = (this.groupSelected) + (1);
			}else{
				this.groupSelected = 0;
			}
			this.updateSlots();
		}else{
			if ((var1) == (ModKeybindings.PREV_COMBAT_BAR.getKey().getValue())){
				if ((this.groupSelected) > (0)){
					this.groupSelected = (this.groupSelected) - (1);
				}else{
					this.groupSelected = (var3_1) - (1);
				}
				this.updateSlots();
			}else{
				return;
			}
		}
	}
	@Nullable()
	protected @Nullable() AbilitySlotButton getHoverSlotIndex(double var1, double var2) {
		int var3 = 0;
		do{
			if ((var3) < (this.abilitySlots.size())){
				AbilitySlotButton var4 = ((AbilitySlotButton)(this.abilitySlots.get(var3)));
				if (((var4) != (null)) && (var4.isMouseOver(var1,var2))){
					return var4;
				}else{
					var3++;
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return null;
	}
	protected int getRealSlotId(int var1) {
		var1 = (var1) % (8);
		return (var1) + ((this.groupSelected) * (8));
	}
	protected void addAbilityToSlot(int var1, AbilityCore<?> var2) {
		ModNetwork.sendToServer(new CEquipAbilityPacket(var1,var2));
		this.isDirty = true;
	}
	protected void removeAbilityFromSlot(int var1) {
		ModNetwork.sendToServer(new CRemoveAbilityPacket(var1));
		this.isDirty = true;
	}
	public void updateListScreen(AbilityCategory var1) {
		this.removeWidget((GuiEventListener)(this.abilitiesList));
		this.abilitiesList = new AbilitiesListScrollPanel(this,this.abilityDataProps,var1);
		this.addRenderableWidget((GuiEventListener)(this.abilitiesList));
		this.setFocused((GuiEventListener)(this.abilitiesList));
	}
	public static boolean canShowTooltips() {
		if (ClientConfig.getTooltipsShown()){
			if (Screen.hasAltDown()){
				return false;
			}else{
				return true;
			}
		}else{
			if (Screen.hasAltDown()){
				return true;
			}else{
				return false;
			}
		}
	}
	private static AbilityTooltip generateAbilityTooltip(IAbility var0, AbilityTooltip var1) {
		var1.clearTooltips();
		boolean var2 = Minecraft.getInstance().options.advancedItemTooltips;
		int var3 = ((!(ClientConfig.hidesAbilityStats())) || (Screen.hasShiftDown())) ? (1) : (0);
		LocalPlayer var4 = Minecraft.getInstance().player;
		var1.add(9999,var0.getCore().getLocalizedName());
		var1.add(9999,AbilityTooltipsHelper.NEW_LINE);
		boolean var5 = false;
		Set var6 = var0.getCore().getDescription();
		if (!(var6.isEmpty())){
			AbilityTooltip var7 = var1;
			LocalPlayer var8 = var4;
			IAbility var9 = var0;
			final AbilityTooltip var7_f1 = var7;
			final LocalPlayer var8_f2 = var8;
			final IAbility var9_f3 = var9;
			Consumer<AbilityDescriptionLine> var10 = (l0) -> {
			var7_f1.add(9998,l0.getTextComponent((LivingEntity)(var8_f2),var9_f3));
		};
			((java.lang.Iterable)(var6)).forEach((Consumer)(var10));
			var5 = true;
		}
		if (var0 instanceof IDescriptiveAbility){
			IDescriptiveAbility var7_1 = ((IDescriptiveAbility)(var0));
			int var8_1 = var1.count();
			var7_1.appendDescription((Player)(var4),var1);
			if (((var1.count()) > (var8_1)) && (var5)){
				var1.add(9998,AbilityTooltipsHelper.NEW_LINE);
			}
		}
		if ((WyDebug.isDebug()) && (var6.stream().anyMatch((Predicate)(((Predicate<AbilityDescriptionLine>)(AbilityDescriptionLine::isAdvanced)))))){
			var1.add(1,(Component)(Component.literal("Using old tooltips system").withStyle(Style.EMPTY.withItalic(Boolean.valueOf(true)).withColor(ChatFormatting.YELLOW))));
		}
		if (((var6.stream().filter((Predicate)(((Predicate<AbilityDescriptionLine>)(AbilityDescriptionLine::isAdvanced)))).count()) > (0L)) && ((ClientConfig.hidesAbilityStats()) && (!(Screen.hasShiftDown())))){
			var1.add(90,AbilityTooltipsHelper.NEW_LINE);
			var1.add(90,(Component)(ModI18n.GUI_SHOW_ABILITY_STATS.copy().setStyle(Style.EMPTY.withItalic(Boolean.valueOf(true)).withColor(ChatFormatting.YELLOW))));
		}
		if (var2){
			ResourceLocation var7_2 = var0.getCore().getRegistryKey();
			if ((var7_2) != (null)){
				String var8_2 = var7_2.toString();
				String var9_1 = "\n§7" + var8_2;
				var1.add(0,(Component)(Component.literal(var9_1)));
			}
		}
		return var1;
	}
	public static void renderAbilityTooltip(GuiGraphics var0, int var1, int var2, IAbility var3) {
		Minecraft var4 = Minecraft.getInstance();
		Window var5 = var4.getWindow();
		if (ClientConfig.hidesAbilityStats()){
			if ((Screen.hasShiftDown()) && (!(hasStatsShown))){
				clearCache = true;
				hasStatsShown = true;
			}else{
				if ((!(Screen.hasShiftDown())) && (hasStatsShown)){
					clearCache = true;
					hasStatsShown = false;
				}
			}
			if (clearCache){
				TOOLTIPS_CACHE.clear();
				clearCache = false;
			}
		}
		AbilityTooltip var6 = null;
		if (TOOLTIPS_CACHE.containsKey(var3.getCore())){
			var6 = ((AbilityTooltip)(TOOLTIPS_CACHE.getOrDefault(var3.getCore(),new AbilityTooltip((Player)(var4.player),var3))));
		}else{
			var6 = generateAbilityTooltip(var3,new AbilityTooltip((Player)(var4.player),var3));
			TOOLTIPS_CACHE.putIfAbsent(var3.getCore(),var6);
		}
		if ((var6.hasPages()) && ((((System.currentTimeMillis()) - (lastModeChange)) > (250L)) && (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(),ModKeybindings.ABILITY_ALT_MODE.getKey().getValue())))){
			var6.cyclePage();
			var6 = generateAbilityTooltip(var3,var6);
			TOOLTIPS_CACHE.put(var3.getCore(),var6);
			lastModeChange = System.currentTimeMillis();
		}
		RendererHelper.drawAbilityTooltip(var3,var0,var6.generateTooltips(),var1,var2,var5.getGuiScaledWidth(),var5.getGuiScaledHeight(),210,TOOLTIP_BACKGROUND_START,TOOLTIP_BACKGROUND_END,TOOLTIP_BORDER_START,TOOLTIP_BORDER_END,var4.font,DefaultTooltipPositioner.INSTANCE);
		RenderSystem.enableBlend();
	}
	public boolean isPauseScreen() {
		return true;
	}
	public AbilitiesListScreen$Compactness getCompactness() {
		return this.compactness;
	}
	public AbilitiesListScreen$Selection getSelectionMode() {
		return this.selection;
	}
	public boolean showTooltips() {
		return this.showTooltips;
	}
	@Nullable()
	public @Nullable() AbilityCore<?> getDraggedAbility() {
		return (AbilityCore<?>) (AbilityCore) (this.draggedAbility);
	}
	public boolean hasDraggedAbility() {
		return (this.draggedAbility) != (null);
	}
	public void setDraggedAbility(AbilityCore<?> var1) {
		this.draggedAbility = var1;
	}
	static  {
		TOOLTIP_BACKGROUND_START = WyHelper.hexToRGB("#A78342").getRGB();
		TOOLTIP_BACKGROUND_END = WyHelper.hexToRGB("#AD8F58").getRGB();
		TOOLTIP_BORDER_START = WyHelper.hexToRGB("#e3b160").getRGB();
		TOOLTIP_BORDER_END = WyHelper.hexToRGB("#cb7e23").getRGB();
		TOOLTIPS_CACHE = new HashMap();
		hasStatsShown = false;
		clearCache = false;
		lastModeChange = 0L;
	}
}
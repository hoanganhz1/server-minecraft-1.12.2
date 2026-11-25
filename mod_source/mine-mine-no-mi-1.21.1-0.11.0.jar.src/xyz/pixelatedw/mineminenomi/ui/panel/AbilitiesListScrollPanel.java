package xyz.pixelatedw.mineminenomi.ui.panel;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.client.gui.widget.ScrollPanel;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCoreUnlockWrapper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DisableComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.ui.screens.AbilitiesListScreen;
import xyz.pixelatedw.mineminenomi.ui.screens.AbilitiesListScreen$Compactness;
import xyz.pixelatedw.mineminenomi.ui.screens.AbilitiesListScreen$Selection;

public class AbilitiesListScrollPanel extends ScrollPanel {
	// Fields
	private static final int ENTRY_HEIGHT = 20;
	private static final Color DEFAULT_ICON_COLOR = WyHelper.hexToRGB("#FFFFFF");
	private static final Color PASSIVE_PAUSED_COLOR = WyHelper.hexToRGB("#FF0000");
	private final AbilitiesListScreen parent;
	private final AbstractClientPlayer player;
	private final AbilityData props;
	private final List<AbilitiesListScrollPanel$Entry<? extends IAbility>> allAbilities;
	private final TreeSet<AbilityCore<? extends IAbility>> unlockedAbilities;
	private final TreeSet<? extends IAbility> passiveAbilities;
	private final AbilityCategory category;
	private final Map<AbilityCore<? extends IAbility>, IAbility> unlockedToEquippedMap;
	private final Minecraft mc;
	private AbilitiesListScreen$Compactness compactness;
	private AbilitiesListScrollPanel$Entry<? extends IAbility> hoveredEntry;

	public AbilitiesListScrollPanel(AbilitiesListScreen var1, AbilityData var2, AbilityCategory var3) {
		super(var1.getMinecraft(),210,130,((var1.height) / (2)) - (92),((var1.width) / (2)) - (102));
		this.unlockedToEquippedMap = new HashMap();
		this.mc = Minecraft.getInstance();
		Minecraft var4 = var1.getMinecraft();
		this.parent = var1;
		this.player = var4.player;
		this.props = var2;
		this.category = var3;
		this.compactness = var1.getCompactness();
		this.allAbilities = new ArrayList();
		AbilityCategory var5 = var3;
		final AbilityCategory var5_f1 = var5;
		Predicate var6 = (Predicate) ((Predicate<AbilityCore>) ((l0) -> {
			return (!(l0.isHidden())) && ((l0.getType().equals(AbilityType.ACTION)) && (var5_f1.isCorePartofCategory().test(l0)));
		}));
		this.unlockedAbilities = (TreeSet) (((TreeSet)(((java.util.stream.Stream)(this.props.getUnlockedAbilitiesStream().map((Function)(((Function<AbilityCoreUnlockWrapper, AbilityCore>)(AbilityCoreUnlockWrapper::getAbilityCore)))).filter(var6))).collect((Collector)(Collectors.toCollection((Supplier)(((Supplier<TreeSet>)(TreeSet::new)))))))));
		AbilityCategory var7 = var3;
		final AbilityCategory var7_f2 = var7;
		Predicate<IAbility> var8 = (l0) -> {
			return (!(l0.getCore().isHidden())) && (((l0.getCore().getType()) == (AbilityType.PASSIVE)) && (var7_f2.isAbilityPartofCategory().test(l0)));
		};
		this.passiveAbilities = (TreeSet) (((TreeSet)(((java.util.stream.Stream)(((xyz.pixelatedw.mineminenomi.data.entity.AbilityData)(this.props)).getPassiveAbilities((Predicate)(var8)).stream())).collect((Collector)(Collectors.toCollection((Supplier)(((Supplier<TreeSet>)(TreeSet::new)))))))));
		Iterator var9 = this.unlockedAbilities.iterator();
		do{
			if (var9.hasNext()){
				AbilityCore var10 = ((AbilityCore)(var9.next()));
				this.allAbilities.add(new AbilitiesListScrollPanel$Entry(this,var10));
				continue;
			}else{
				break;
			}
		} while (true);
		this.allAbilities.add(new AbilitiesListScrollPanel$Entry(this));
		Iterator var10_1 = this.passiveAbilities.iterator();
		do{
			if (var10_1.hasNext()){
				IAbility var11 = ((IAbility)(var10_1.next()));
				this.allAbilities.add(new AbilitiesListScrollPanel$Entry(this,var11));
				continue;
			}else{
				break;
			}
		} while (true);
		this.unlockedToEquippedMap.clear();
		Iterator var11_1 = this.unlockedAbilities.iterator();
		do{
			if (var11_1.hasNext()){
				AbilityCore var12 = ((AbilityCore)(var11_1.next()));
				IAbility var13 = this.props.getEquippedAbility(var12);
				if ((var13) == (null)){
					var13 = var12.createAbility();
				}
				this.unlockedToEquippedMap.put(var12,var13);
				continue;
			}else{
				break;
			}
		} while (true);
	}
	protected void drawGradientRect(GuiGraphics var1, int var2, int var3, int var4, int var5, int var6, int var7) {
	}
	public boolean mouseReleased(double var1, double var2, int var3) {
		return true;
	}
	protected int getContentHeight() {
		return (int)((((float)(((List)(((java.util.stream.Stream)(this.allAbilities.stream())).collect((Collector)(Collectors.toList())))).size())) * (this.getEntrySpacing())) + (2F));
	}
	public float getEntrySpacing() {
		return (20F) * (this.compactness.getSpacing());
	}
	public void setCompactness(AbilitiesListScreen$Compactness var1) {
		this.compactness = var1;
	}
	protected int getScrollAmount() {
		return 15;
	}
	protected void drawPanel(GuiGraphics var1, int var2, int var3, Tesselator var4, int var5, int var6) {
		GuiGraphics var16 = null;
		GuiGraphics var17_1 = null;
		float var14 = 0.0F;
		float var7 = (float)(var3);
		float var8 = (float)((((this.parent.width) / (2)) - (100)) + (40));
		Iterator var9 = this.unlockedAbilities.iterator();
		do{
			if (var9.hasNext()){
				AbilityCore var10 = ((AbilityCore)(var9.next()));
				if ((var10) == (null)){
					continue;
				}else{
					IAbility var11 = ((IAbility)(this.unlockedToEquippedMap.get(var10)));
					int var12 = 16777215;
					int var13 = ((((float)(var5)) >= (var8)) && ((((float)(var6)) >= ((var7) - (2F))) && ((((float)(var5)) < (((var8) + ((float)(this.width))) - (50F))) && (((float)(var6)) < (((var7) + (this.getEntrySpacing())) - (2F)))))) ? (1) : (0);
					if (this.props.hasEquippedAbility(var10)){
						var12 = 16711680;
					}else{
						if ((var13) != (0)){
							var12 = 10014975;
						}
					}
					RenderSystem.enableBlend();
					var1.pose().pushPose();
					var1.pose().translate(((var8) - (34F)) + ((float)(this.compactness.getSlotOffsetX())),(var7) - ((float)(this.compactness.getSlotOffsetY())),0F);
					var1.pose().scale(this.compactness.getSlotScale(),this.compactness.getSlotScale(),1F);
					var1.blit(ModResources.WIDGETS,0,0,0,0,23,23);
					var1.pose().popPose();
					RendererHelper.drawStringWithBorder(this.mc.font,var1,var11.getDisplayName(),(int)(var8),((int)(var7)) + (4),var12);
					RendererHelper.drawIcon(var11.getIcon((LivingEntity)(this.player)),var1.pose(),(float)(((Mth.floor(var8)) - (30)) + (this.compactness.getIconOffsetX())),(float)((Mth.floor(var7)) + (this.compactness.getIconOffsetY())),1F,(float)(this.compactness.getIconSize()),(float)(this.compactness.getIconSize()));
					var14 = var7;
					AbilitiesListScrollPanel var15 = this;
					var16 = var1;
					float var17 = var8;
					float var18 = var14;
					final GuiGraphics var16_f1 = var16;
					final float var17_f1 = var17;
					final float var18_f2 = var18;
					Consumer<DisableComponent> var19 = (l0) -> {
			if (l0.isDisabled()){
				RendererHelper.drawIcon(ModResources.DISABLED_ABILITY,var16_f1.pose(),(float)(((Mth.floor(var17_f1)) - (30)) + (this.compactness.getIconOffsetX())),(float)((Mth.floor(var18_f2)) + (this.compactness.getIconOffsetY())),3F,(float)(this.compactness.getIconSize()),(float)(this.compactness.getIconSize()),1F,1F,1F,1F);
			}
		};
					((java.util.Optional)(var11.getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get()))))).ifPresent((Consumer)(var19));
					var7 = (var7) + (this.getEntrySpacing());
					continue;
				}
			}else{
				break;
			}
		} while (true);
		if ((this.passiveAbilities.size()) > (0)){
			var1.pose().translate(0F,0F,250F);
			RendererHelper.drawStringWithBorder(this.mc.font,var1,(Component)(Component.literal("Passives")),((int)(var8)) - (20),((int)(var7)) + (4),9145227);
			var1.pose().translate(0F,0F,-250F);
			float var10_1 = (var7) + (this.getEntrySpacing());
			Iterator var11_1 = this.passiveAbilities.iterator();
			do{
				if (var11_1.hasNext()){
					IAbility var12_1 = ((IAbility)(var11_1.next()));
					if ((var12_1) == (null)){
						continue;
					}else{
						int var13_1 = 11206519;
						Color var14_1 = DEFAULT_ICON_COLOR;
						Optional var15_1 = var12_1.getComponent(((AbilityComponentKey)(ModAbilityComponents.PAUSE_TICK.get())));
						if ((var15_1.isPresent()) && (((PauseTickComponent)(var15_1.get())).isPaused())){
							var13_1 = PASSIVE_PAUSED_COLOR.getRGB();
							var14_1 = PASSIVE_PAUSED_COLOR;
						}
						RenderSystem.enableBlend();
						var1.pose().pushPose();
						var1.pose().translate(((var8) - (34F)) + ((float)(this.compactness.getSlotOffsetX())),(var10_1) - ((float)(this.compactness.getSlotOffsetY())),0F);
						var1.pose().scale(this.compactness.getSlotScale(),this.compactness.getSlotScale(),1F);
						var1.blit(ModResources.WIDGETS,0,0,0,0,23,23);
						var1.pose().popPose();
						RendererHelper.drawStringWithBorder(this.mc.font,var1,var12_1.getDisplayName(),(int)(var8),((int)(var10_1)) + (4),var13_1);
						RendererHelper.drawIcon(var12_1.getIcon((LivingEntity)(this.player)),var1.pose(),(float)(((Mth.floor(var8)) - (30)) + (this.compactness.getIconOffsetX())),(float)((Mth.floor(var10_1)) + (this.compactness.getIconOffsetY())),1F,(float)(this.compactness.getIconSize()),(float)(this.compactness.getIconSize()),((float)(var14_1.getRed())) / (255F),((float)(var14_1.getGreen())) / (255F),((float)(var14_1.getBlue())) / (255F),1F);
						var14 = var10_1;
						var17_1 = var1;
						float var18_1 = var8;
						float var19_1 = var14;
						final GuiGraphics var17_1_f2 = var17_1;
						final float var18_1_f1 = var18_1;
						final float var19_1_f2 = var19_1;
						Consumer<DisableComponent> var20 = (l0) -> {
			if (l0.isDisabled()){
				RendererHelper.drawIcon(ModResources.DISABLED_ABILITY,var17_1_f2.pose(),(float)((Mth.floor(var18_1_f1)) - (30)),(float)(Mth.floor(var19_1_f2)),3F,16F,16F,1F,1F,1F,1F);
			}
		};
						((java.util.Optional)(var12_1.getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get()))))).ifPresent((Consumer)(var20));
						var10_1 = (var10_1) + (this.getEntrySpacing());
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
		var1.flush();
	}
	public NarratableEntry.NarrationPriority narrationPriority() {
		return NarratableEntry.NarrationPriority.NONE;
	}
	public void updateNarration(NarrationElementOutput var1) {
	}
	private AbilitiesListScrollPanel$Entry<? extends IAbility> findAbilityEntry(int var1, int var2) {
		double var3 = (double)((((float)((var2) - (this.top))) + (this.scrollDistance)) - (2F));
		if ((var3) <= (0D)){
			return (AbilitiesListScrollPanel$Entry<? extends IAbility>) (AbilitiesListScrollPanel$Entry) (null);
		}else{
			int var4 = (int)((var3) / ((double)(this.getEntrySpacing())));
			if ((var4) >= (this.allAbilities.size())){
				return (AbilitiesListScrollPanel$Entry<? extends IAbility>) (AbilitiesListScrollPanel$Entry) (null);
			}else{
				AbilitiesListScrollPanel$Entry var5 = ((AbilitiesListScrollPanel$Entry)(this.allAbilities.get(var4)));
				if ((var5) != (null)){
					return (AbilitiesListScrollPanel$Entry<? extends IAbility>) (AbilitiesListScrollPanel$Entry) (var5);
				}else{
					return (AbilitiesListScrollPanel$Entry<? extends IAbility>) (AbilitiesListScrollPanel$Entry) (null);
				}
			}
		}
	}
	public boolean mouseClicked(double var0, double var1, int var2) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: invalid_input: dump lambda method `xyz.pixelatedw.mineminenomi.ui.panel.AbilitiesListScrollPanel.lambda$mouseClicked$4` error: lambda sou */ }
	public boolean mouseScrolled(double var1, double var2, double var3, double var4) {
		if ((this.getContentHeight()) < (this.height)){
			return false;
		}else{
			return super.mouseScrolled(var1,var2,var3,var4);
		}
	}
	public boolean mouseDragged(double var1, double var2, int var3, double var4, double var5) {
		return super.mouseDragged(var1,var2,var3,var4,var5);
	}
	public boolean isMouseOver(double var1, double var2) {
		return ((var1) >= ((double)(this.left))) && (((var2) >= ((double)(this.top))) && (((var1) < ((double)(((this.left) + (this.width)) - (5)))) && ((var2) < ((double)((this.top) + (this.height))))));
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		super.render(var1,var2,var3,var4);
		this.hoveredEntry = null;
		if (this.isMouseOver((double)(var2),(double)(var3))){
			this.hoveredEntry = this.findAbilityEntry(var2,var3);
			if ((this.hoveredEntry) == (null)){
				return;
			}else{
				IAbility var5 = null;
				if (this.hoveredEntry.isActive()){
					var5 = ((IAbility)(((java.util.Map)(this.unlockedToEquippedMap)).get((Object)(this.hoveredEntry.getActive()))));
				}else{
					if (this.hoveredEntry.isPassive()){
						var5 = this.hoveredEntry.getPassive();
					}
				}
				if (((var5) != (null)) && ((!(this.parent.hasDraggedAbility())) && (AbilitiesListScreen.canShowTooltips()))){
					AbilitiesListScreen.renderAbilityTooltip(var1,var2,var3,var5);
				}else{
					return;
				}
			}
		}
	}
	protected void drawBackground(GuiGraphics var1, Tesselator var2, float var3) {
	}
	@Nullable()
	public @Nullable() AbilityCore<?> getHoveredEntry() {
		if ((this.hoveredEntry) != (null)){
			return this.hoveredEntry.getActive();
		}else{
			return (AbilityCore<?>) (AbilityCore) (null);
		}
	}
}
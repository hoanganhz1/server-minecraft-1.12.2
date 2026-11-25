package xyz.pixelatedw.mineminenomi.api.abilities.components;

import com.google.common.base.Strings;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class SlotDecorationComponent extends AbilityComponent<IAbility> {
	// Fields
	private static final String FLUSH = "";
	private static final float GCD_COLOR = 0.25F;
	private float slotRed;
	private float slotGreen;
	private float slotBlue;
	private float iconRed;
	private float iconGreen;
	private float iconBlue;
	private float iconAlpha;
	private float maxValue;
	private float currentValue;
	private String displayText;
	private final PriorityEventPool<SlotDecorationComponent$IPreRenderEvent> preRenderEvents;
	private final PriorityEventPool<SlotDecorationComponent$IPostRenderEvent> postRenderEvents;

	public SlotDecorationComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.SLOT_DECORATION.get())),var1);
		this.slotRed = 1F;
		this.slotGreen = 1F;
		this.slotBlue = 1F;
		this.iconRed = 1F;
		this.iconGreen = 1F;
		this.iconBlue = 1F;
		this.iconAlpha = 1F;
		this.maxValue = 1F;
		this.currentValue = 0F;
		this.displayText = "";
		this.preRenderEvents = (PriorityEventPool) (new PriorityEventPool());
		this.postRenderEvents = (PriorityEventPool) (new PriorityEventPool());
		this.setClientSide();
	}
	public void postInit(IAbility var1) {
		SlotDecorationComponent var2 = this;
		IAbility var3 = var1;
		final IAbility var3_f1 = var3;
		SlotDecorationComponent$IPreRenderEvent var4 = (l0, l1, l2, l3, l4, l5, l6, l7) -> {
			AbilityData lv1_10 = ((AbilityData)(((java.util.Optional)(AbilityData.get(l0))).orElse((Object)(null))));
			if ((lv1_10) == (null)){
				return;
			}else{
				if ((!(var3_f1.isOGCD())) && (lv1_10.isOnGCD())){
					this.setCurrentValue((float)(lv1_10.getCurrentGCD()));
					this.setMaxValue((float)(lv1_10.getDefaultGCD()));
					this.setSlotColor(0.25F,0.25F,0.25F);
					this.setIconColor(0.25F,0.25F,0.25F);
				}
				return;
			}
		};
		this.addPreRenderEvent(991,var4);
	}
	public SlotDecorationComponent addPreRenderEvent(int var1, SlotDecorationComponent$IPreRenderEvent var2) {
		this.preRenderEvents.addEvent(var1,var2);
		return this;
	}
	public SlotDecorationComponent addPostRenderEvent(int var1, SlotDecorationComponent$IPostRenderEvent var2) {
		this.postRenderEvents.addEvent(var1,var2);
		return this;
	}
	public void setSlotColor(float var1, float var2, float var3) {
		this.slotRed = var1;
		this.slotGreen = var2;
		this.slotBlue = var3;
	}
	public void setIconColor(float var1, float var2, float var3) {
		this.iconRed = var1;
		this.iconGreen = var2;
		this.iconBlue = var3;
	}
	public void setIconAlpha(float var1) {
		this.iconAlpha = var1;
	}
	public void setCurrentValue(float var1) {
		this.currentValue = var1;
		this.displayText = "";
	}
	public void setDisplayText(String var1) {
		this.displayText = var1;
	}
	public void setMaxValue(float var1) {
		this.maxValue = var1;
	}
	public void triggerPreRenderEvents(LivingEntity var1, Minecraft var2, PoseStack var3, MultiBufferSource var4, TexturedRectUI var5, float var6, float var7, float var8) {
		LivingEntity var9 = var1;
		Minecraft var10 = var2;
		PoseStack var11 = var3;
		MultiBufferSource var12 = var4;
		TexturedRectUI var13 = var5;
		float var14 = var6;
		float var15 = var7;
		float var16 = var8;
		final LivingEntity var9_f1 = var9;
		final Minecraft var10_f2 = var10;
		final PoseStack var11_f3 = var11;
		final MultiBufferSource var12_f4 = var12;
		final TexturedRectUI var13_f5 = var13;
		final float var14_f6 = var14;
		final float var15_f7 = var15;
		final float var16_f8 = var16;
		Consumer<SlotDecorationComponent$IPreRenderEvent> var17 = (l0) -> {
			l0.preRender(var9_f1,var10_f2,var11_f3,var12_f4,var13_f5,var14_f6,var15_f7,var16_f8);
		};
		((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.preRenderEvents)).dispatch((Consumer)(var17));
	}
	public void triggerPostRenderEvents(LivingEntity var1, Minecraft var2, PoseStack var3, MultiBufferSource var4, TexturedRectUI var5, float var6, float var7, float var8) {
		LivingEntity var9 = var1;
		Minecraft var10 = var2;
		PoseStack var11 = var3;
		MultiBufferSource var12 = var4;
		TexturedRectUI var13 = var5;
		float var14 = var6;
		float var15 = var7;
		float var16 = var8;
		final LivingEntity var9_f1 = var9;
		final Minecraft var10_f2 = var10;
		final PoseStack var11_f3 = var11;
		final MultiBufferSource var12_f4 = var12;
		final TexturedRectUI var13_f5 = var13;
		final float var14_f6 = var14;
		final float var15_f7 = var15;
		final float var16_f8 = var16;
		Consumer<SlotDecorationComponent$IPostRenderEvent> var17 = (l0) -> {
			l0.postRender(var9_f1,var10_f2,var11_f3,var12_f4,var13_f5,var14_f6,var15_f7,var16_f8);
		};
		((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.postRenderEvents)).dispatch((Consumer)(var17));
	}
	public float getSlotRed() {
		return this.slotRed;
	}
	public float getSlotGreen() {
		return this.slotGreen;
	}
	public float getSlotBlue() {
		return this.slotBlue;
	}
	public float getIconRed() {
		return this.iconRed;
	}
	public float getIconGreen() {
		return this.iconGreen;
	}
	public float getIconBlue() {
		return this.iconBlue;
	}
	public float getIconAlpha() {
		return this.iconAlpha;
	}
	public float getCurrentValue() {
		return this.currentValue;
	}
	public String getDisplayText() {
		return this.displayText;
	}
	public boolean hasDisplayText() {
		return !(Strings.isNullOrEmpty(this.displayText));
	}
	public float getMaxValue() {
		return this.maxValue;
	}
	public void resetDecoration() {
		this.setSlotColor(1F,1F,1F);
		this.setIconColor(1F,1F,1F);
		this.setIconAlpha(1F);
		this.setCurrentValue(0F);
	}
}
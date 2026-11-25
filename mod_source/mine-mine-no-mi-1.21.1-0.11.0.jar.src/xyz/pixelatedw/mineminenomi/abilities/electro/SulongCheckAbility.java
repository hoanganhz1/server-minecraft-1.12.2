package xyz.pixelatedw.mineminenomi.abilities.electro;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent$IGaugeRenderer;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SulongCheckAbility extends PassiveAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SulongCheckAbility>> INSTANCE;

	public SulongCheckAbility(AbilityCore<SulongCheckAbility> var1) {
		super((AbilityCore)(var1));
		if (super.isClientSide()){
			GaugeComponent var2 = new GaugeComponent((IAbility)(this),(GaugeComponent$IGaugeRenderer)(((xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent$IGaugeRenderer)((GaugeComponent$IGaugeRenderer) ((GaugeComponent$IGaugeRenderer<SulongCheckAbility>) (this::renderGauge))))));
			AbilityComponent[] var3 = new AbilityComponent[]{var2};
			super.addComponents(var3);
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	public void renderGauge(Player var1, GuiGraphics var2, int var3, int var4, SulongCheckAbility var5) {
		RenderSystem.enableBlend();
		if (ElectroHelper.canTransform(var1.level())){
			RendererHelper.drawIcon(((AbilityCore)(SulongAbility.INSTANCE.get())).getIcon(),var2.pose(),(float)(var3),(float)((var4) - (38)),0F,32F,32F);
		}
		RenderSystem.disableBlend();
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			return new AbilityCore$Builder(l0,l1,AbilityCategory.RACIAL,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SulongCheckAbility::new)))).setHidden().build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("sulong_check","Sulong Check",var0));
	}
}
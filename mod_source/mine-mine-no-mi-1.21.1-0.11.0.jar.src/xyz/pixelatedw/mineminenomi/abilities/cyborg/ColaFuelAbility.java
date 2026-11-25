package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent$IGaugeRenderer;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class ColaFuelAbility extends PassiveAbility {
	// Fields
	private static final int TECHNOLOGY_POINTS = 10;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<ColaFuelAbility>> INSTANCE;

	public ColaFuelAbility(AbilityCore<ColaFuelAbility> var1) {
		super((AbilityCore)(var1));
		if (super.isClientSide()){
			GaugeComponent var2 = new GaugeComponent((IAbility)(this),(GaugeComponent$IGaugeRenderer)(((xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent$IGaugeRenderer)((GaugeComponent$IGaugeRenderer) ((GaugeComponent$IGaugeRenderer<ColaFuelAbility>) (this::renderGauge))))));
			AbilityComponent[] var3 = new AbilityComponent[]{var2};
			super.addComponents(var3);
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	private void renderGauge(Player var1, GuiGraphics var2, int var3, int var4, ColaFuelAbility var5) {
		RenderSystem.enableBlend();
		Optional var6 = EntityStatsData.get((LivingEntity)(var1));
		float var7 = (float)(((Integer)(var6.map((Function)(((Function<EntityStatsData, Integer>)(EntityStatsData::getCola)))).orElse(Integer.valueOf(0)))).intValue());
		float var8 = (float)(((Integer)(var6.map((Function)(((Function<EntityStatsData, Integer>)(EntityStatsData::getMaxCola)))).orElse(Integer.valueOf(1)))).intValue());
		Minecraft var9 = Minecraft.getInstance();
		var2.blit(ModResources.WIDGETS,(var3) + (6),(var4) - (42),0,52,23,40);
		int var10 = ((int)(((var7) / (var8)) * (30F))) + (23);
		if (((var10) > (0)) && ((var10) < (24))){
			var10 = 24;
		}else{
			if ((var10) > (52)){
				var10 = 52;
			}
		}
		var2.blit(ModResources.WIDGETS,(var3) + (10),(var4) - (42),32,var10,16,32);
		float var11 = var7;
		String var12 = "" + var11;
		String var13 = var12;
		RendererHelper.drawStringWithBorder(var9.font,var2,var13,((var3) + (18)) - ((var9.font.width(var13)) / (2)),(var4) - (12),Color.WHITE.getRGB());
		RenderSystem.disableBlend();
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(15F,-4F));
		RequiredRaceUnlockCondition var2 = RequiredRaceUnlockCondition.requires(((Race)(ModRaces.CYBORG.get())));
		NodeUnlockCondition var3 = ((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).or((NodeUnlockCondition)(TrainingPointsUnlockCondition.technology(10F)));
		var1.setUnlockRule(var3,SubtractPointsAction.technology(10F).onlyIf(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).not()).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE))));
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			AbilityCore$INodeFactory[] lv1_2 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv1_3 = ColaFuelAbility::createNode;
			lv1_2[0] = lv1_3;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.RACIAL,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(ColaFuelAbility::new)))).setHidden().setIcon(ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/items/cola.png")).setNodeFactories(lv1_2).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("cola_fuel","Cola Fuel",var0));
	}
}
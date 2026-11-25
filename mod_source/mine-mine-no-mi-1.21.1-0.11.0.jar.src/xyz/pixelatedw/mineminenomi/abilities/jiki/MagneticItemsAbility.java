package xyz.pixelatedw.mineminenomi.abilities.jiki;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent$IGaugeRenderer;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateAbilityNBTPacket;

public class MagneticItemsAbility extends PassiveAbility {
	// Fields
	private static final DecimalFormat FORMAT = new DecimalFormat("#0.0");
	private float ironValue;
	private float prevIronValue;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<MagneticItemsAbility>> INSTANCE;

	public MagneticItemsAbility(AbilityCore<MagneticItemsAbility> var1) {
		super((AbilityCore)(var1));
		this.prevIronValue = -1F;
		if (this.isClientSide()){
			GaugeComponent var2 = new GaugeComponent((IAbility)(this),(GaugeComponent$IGaugeRenderer)(((xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent$IGaugeRenderer)((GaugeComponent$IGaugeRenderer) ((GaugeComponent$IGaugeRenderer<MagneticItemsAbility>) (this::renderGauge))))));
			AbilityComponent[] var3 = new AbilityComponent[]{var2};
			this.addComponents(var3);
		}
		this.addDuringPassiveEvent((PassiveAbility$IDuringPassiveEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent)(this::duringPassiveEvent))));
	}
	public void duringPassiveEvent(LivingEntity var1) {
		if (!(var1.level().isClientSide)){
			List var2 = ItemsHelper.getAllInventoryItems(var1);
			this.ironValue = JikiHelper.getIronAmount(var1,var2);
			if ((this.ironValue) != (this.prevIronValue)){
				if (var1 instanceof Player){
					Player var3 = ((Player)(var1));
					ModNetwork.sendTo(new SUpdateAbilityNBTPacket(var1,(IAbility)(this)),var3);
				}
				this.prevIronValue = this.ironValue;
			}else{
				return;
			}
		}
	}
	public void saveAdditional(CompoundTag var1) {
		var1.putFloat("ironValue",this.ironValue);
	}
	public void loadAdditional(CompoundTag var1) {
		this.ironValue = var1.getFloat("ironValue");
	}
	@OnlyIn(value=Dist.CLIENT)
	public void renderGauge(Player var1, GuiGraphics var2, int var3, int var4, MagneticItemsAbility var5) {
		RenderSystem.enableBlend();
		Minecraft var6 = Minecraft.getInstance();
		RenderSystem.setShaderTexture(0,ModResources.WIDGETS);
		RendererHelper.drawIcon(((AbilityCore)(INSTANCE.get())).getIcon(),var2.pose(),(float)(var3),(float)((var4) - (38)),0F,32F,32F);
		String var7 = FORMAT.format((double)(var5.ironValue));
		RendererHelper.drawStringWithBorder(var6.font,var2,var7,((var3) + (16)) - ((var6.font.width(var7)) / (2)),(var4) - (15),Color.WHITE.getRGB());
		RenderSystem.disableBlend();
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Displays the total value of maginetic items in the user's inventory"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(MagneticItemsAbility::new)))).addDescriptionLine(lv1_3).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("magnetic_items","Magnetic Items",var0));
	}
}
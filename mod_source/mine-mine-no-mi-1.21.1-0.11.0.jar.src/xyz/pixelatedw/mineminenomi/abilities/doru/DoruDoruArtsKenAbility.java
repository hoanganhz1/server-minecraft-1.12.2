package xyz.pixelatedw.mineminenomi.abilities.doru;

import java.awt.Color;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.ItemAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class DoruDoruArtsKenAbility extends ItemAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<DoruDoruArtsKenAbility>> INSTANCE;

	public DoruDoruArtsKenAbility(AbilityCore<DoruDoruArtsKenAbility> var1) {
		super((AbilityCore)(var1));
	}
	public ItemStack createItemStack(LivingEntity var1) {
		ItemStack var2 = new ItemStack(((ItemLike)(ModWeapons.DORU_DORU_ARTS_KEN.get())));
		float var3 = 1F;
		float var4 = 1F;
		float var5 = 1F;
		if ((ItemsHelper.countItemInInventory(var1,((Item)(ModItems.COLOR_PALETTE.get())))) > (0)){
			var3 = this.random.nextFloat();
			var4 = this.random.nextFloat();
			var5 = this.random.nextFloat();
		}
		MultiChannelItemColor.setLayerColor(var2,0,new Color(var3,var4,var5).getRGB());
		return var2;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("The user uses hardened wax to create a sword"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DoruDoruArtsKenAbility::new)))).addDescriptionLine(lv1_3).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.WAX).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("doru_doru_arts_ken","Doru Doru Arts: Ken",var0));
	}
}
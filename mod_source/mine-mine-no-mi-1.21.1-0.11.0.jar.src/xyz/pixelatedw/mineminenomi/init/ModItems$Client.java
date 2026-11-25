package xyz.pixelatedw.mineminenomi.init;

import java.util.function.Supplier;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.api.items.components.RandomizedFruit;

public class ModItems$Client {
	public static void registerColorHandlers(RegisterColorHandlersEvent.Item var0) {
		ItemLike[] var1 = ((ItemLike[])(ModValues.DEVIL_FRUITS.toArray(new ItemLike[0])));
		ItemColor var2 = ModItems$Client::createFruitItemColor;
		var0.register(var2,var1);
		ItemLike[] var3 = new ItemLike[]{((ItemLike)(ModItems.STRAW_DOLL.get())),((ItemLike)(ModArmors.STRAW_HAT.get())),((ItemLike)(ModArmors.MARINE_CAPTAIN_CAPE.get())),((ItemLike)(ModArmors.PIRATE_CAPTAIN_CAPE.get())),((ItemLike)(ModArmors.BANDANA.get())),((ItemLike)(ModArmors.VICE_ADMIRAL_CHEST.get())),((ItemLike)(ModArmors.VICE_ADMIRAL_FEET.get())),((ItemLike)(ModArmors.VICE_ADMIRAL_LEGS.get())),((ItemLike)(ModArmors.TRICORNE.get())),((ItemLike)(ModArmors.FLUFFY_CAPE.get())),((ItemLike)(ModArmors.MIHAWK_HAT.get())),((ItemLike)(ModArmors.DOFFY_GLASSES.get())),((ItemLike)(ModArmors.BANDANA.get())),((ItemLike)(ModArmors.CELESTIAL_DRAGON_BUBBLE.get())),((ItemLike)(ModArmors.PLUME_HAT.get())),((ItemLike)(ModArmors.BICORNE.get())),((ItemLike)(ModArmors.WIDE_BRIM_HAT.get())),((ItemLike)(ModArmors.WIZARD_HAT.get())),((ItemLike)(ModArmors.SENOR_PINK_HEAD.get())),((ItemLike)(ModArmors.SENOR_PINK_DIAPER.get())),((ItemLike)(ModArmors.BLUGORI_HOOD.get())),((ItemLike)(ModArmors.CABAJI_SCARF.get())),((ItemLike)(ModWeapons.AXE.get())),((ItemLike)(ModWeapons.KATANA.get())),((ItemLike)(ModWeapons.DAGGER.get())),((ItemLike)(ModWeapons.CUTLASS.get())),((ItemLike)(ModWeapons.BISENTO.get())),((ItemLike)(ModWeapons.BROADSWORD.get())),((ItemLike)(ModWeapons.JITTE.get())),((ItemLike)(ModWeapons.CLEAVER.get())),((ItemLike)(ModWeapons.MACE.get())),((ItemLike)(ModWeapons.SPEAR.get())),((ItemLike)(ModWeapons.KUJA_BOW.get())),((ItemLike)(ModWeapons.DORU_PICKAXE.get())),((ItemLike)(ModWeapons.DORU_DORU_ARTS_KEN.get()))};
		ItemLike[] var4 = var3;
		ItemColor var5 = ModItems$Client::createMultiChannelItemColor;
		var0.register(var5,var4);
	}
	private static int createFruitItemColor(ItemStack var0, int var1) {
		if ((var1) > (0)){
			return RandomizedFruit.getStemColor(var0);
		}else{
			return RandomizedFruit.getBaseColor(var0);
		}
	}
	private static int createMultiChannelItemColor(ItemStack var0, int var1) {
		MultiChannelItemColor var2 = ((MultiChannelItemColor)(var0.get(ModDataComponents.MULTI_CHANNEL_DYES)));
		if ((var2) == (null)){
			return -1;
		}else{
			return var2.getLayerColor(var1);
		}
	}
}
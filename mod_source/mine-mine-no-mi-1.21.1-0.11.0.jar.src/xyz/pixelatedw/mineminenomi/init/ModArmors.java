package xyz.pixelatedw.mineminenomi.init;

import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.items.armors.BigRedNoseItem;
import xyz.pixelatedw.mineminenomi.items.armors.ColaBackpackItem;
import xyz.pixelatedw.mineminenomi.items.armors.Mod3DArmorItem;
import xyz.pixelatedw.mineminenomi.items.armors.ModArmorItem;
import xyz.pixelatedw.mineminenomi.items.armors.OverShoulderHelmetItem;
import xyz.pixelatedw.mineminenomi.items.armors.PearlArmorItem;
import xyz.pixelatedw.mineminenomi.items.armors.SniperGogglesItem;
import xyz.pixelatedw.mineminenomi.items.armors.WootzArmorItem;

public class ModArmors {
	// Fields
	public static final DeferredItem<Item> PIRATE_CHEST;
	public static final DeferredItem<Item> PIRATE_LEGS;
	public static final DeferredItem<Item> PIRATE_FEET;
	public static final DeferredItem<Item> MARINE_HEAD;
	public static final DeferredItem<Item> MARINE_CHEST;
	public static final DeferredItem<Item> MARINE_LEGS;
	public static final DeferredItem<Item> MARINE_FEET;
	public static final DeferredItem<Mod3DArmorItem> STRAW_HAT;
	public static final DeferredItem<Item> LUFFY_SHIRT;
	public static final DeferredItem<Item> LUFFY_PANTS;
	public static final DeferredItem<Item> LUFFY_SANDALS;
	public static final DeferredItem<Item> ZORO_CHEST;
	public static final DeferredItem<Item> ZORO_LEGS;
	public static final DeferredItem<Item> ZORO_FEET;
	public static final DeferredItem<Item> SANJI_CHEST;
	public static final DeferredItem<Item> SANJI_LEGS;
	public static final DeferredItem<Item> SANJI_FEET;
	public static final DeferredItem<Item> USOPP_CHEST;
	public static final DeferredItem<Item> USOPP_LEGS;
	public static final DeferredItem<Item> USOPP_FEET;
	public static final DeferredItem<Item> FRANKY_GLASSES;
	public static final DeferredItem<Item> FRANKY_CHEST;
	public static final DeferredItem<Item> SENOR_PINK_HEAD;
	public static final DeferredItem<Item> SENOR_PINK_CHEST;
	public static final DeferredItem<Item> SENOR_PINK_DIAPER;
	public static final DeferredItem<ModArmorItem> VICE_ADMIRAL_CHEST;
	public static final DeferredItem<ModArmorItem> VICE_ADMIRAL_LEGS;
	public static final DeferredItem<ModArmorItem> VICE_ADMIRAL_FEET;
	public static final DeferredItem<Item> CP9_CHEST;
	public static final DeferredItem<Item> CP9_LEGS;
	public static final DeferredItem<Item> CP9_FEET;
	public static final DeferredItem<Item> KUMA_CHEST;
	public static final DeferredItem<Item> KUMA_LEGS;
	public static final DeferredItem<Item> KUMA_FEET;
	public static final DeferredItem<Item> SMOKER_CHEST;
	public static final DeferredItem<Item> SMOKER_LEGS;
	public static final DeferredItem<Item> SMOKER_FEET;
	public static final DeferredItem<Item> KURO_GLASSES;
	public static final DeferredItem<Item> KURO_CHEST;
	public static final DeferredItem<Item> KURO_LEGS;
	public static final DeferredItem<Item> KURO_FEET;
	public static final DeferredItem<Item> PEARL_HAT;
	public static final DeferredItem<Item> PEARL_ARMOR;
	public static final DeferredItem<Item> PEARL_LEGS;
	public static final DeferredItem<Item> ARLONG_HAT;
	public static final DeferredItem<Item> ARLONG_CHEST;
	public static final DeferredItem<Item> ARLONG_LEGS;
	public static final DeferredItem<Item> ARLONG_FEET;
	public static final DeferredItem<Item> CHEW_CHEST;
	public static final DeferredItem<Item> CHEW_LEGS;
	public static final DeferredItem<Item> CHEW_FEET;
	public static final DeferredItem<Item> KUROOBI_CHEST;
	public static final DeferredItem<Item> KUROOBI_LEGS;
	public static final DeferredItem<Item> KUROOBI_FEET;
	public static final DeferredItem<Item> CROCODILE_CHEST;
	public static final DeferredItem<Item> CROCODILE_LEGS;
	public static final DeferredItem<Item> CROCODILE_FEET;
	public static final DeferredItem<Item> MR1_CHEST;
	public static final DeferredItem<Item> MR1_LEGS;
	public static final DeferredItem<Item> MR1_FEET;
	public static final DeferredItem<Item> MR3_GLASSES;
	public static final DeferredItem<Item> MR3_CHEST;
	public static final DeferredItem<Item> MR3_LEGS;
	public static final DeferredItem<Item> MR3_FEET;
	public static final DeferredItem<Item> MR5_GLASSES;
	public static final DeferredItem<Item> MR5_CHEST;
	public static final DeferredItem<Item> MR5_LEGS;
	public static final DeferredItem<Item> MR5_FEET;
	public static final DeferredItem<Item> BANDANA;
	public static final DeferredItem<Item> SNIPER_GOGGLES;
	public static final DeferredItem<Item> MH5_GAS_MASK;
	public static final DeferredItem<Item> CHOPPER_HAT;
	public static final DeferredItem<Item> CHOPPER_HAT_2;
	public static final DeferredItem<Item> ACE_HAT;
	public static final DeferredItem<Item> KIZARU_GLASSES;
	public static final DeferredItem<Item> KILLER_MASK;
	public static final DeferredItem<Item> LAW_HAT;
	public static final DeferredItem<Item> TRICORNE;
	public static final DeferredItem<Item> SABO_HAT;
	public static final DeferredItem<Item> DOFFY_GLASSES;
	public static final DeferredItem<Item> MIHAWK_HAT;
	public static final DeferredItem<Item> FLEET_ADMIRAL_HAT;
	public static final DeferredItem<Item> CELESTIAL_DRAGON_BUBBLE;
	public static final DeferredItem<Item> PLUME_HAT;
	public static final DeferredItem<Item> BICORNE;
	public static final DeferredItem<Item> WIDE_BRIM_HAT;
	public static final DeferredItem<Item> HEART_GLASSES;
	public static final DeferredItem<Item> CABAJI_SCARF;
	public static final DeferredItem<Item> BIG_RED_NOSE;
	public static final DeferredItem<Item> IRON_JAW;
	public static final DeferredItem<Item> WIZARD_HAT;
	public static final DeferredItem<Item> SOGEKING_MASK;
	public static final DeferredItem<Item> WHOS_WHO_MASK;
	public static final DeferredItem<Item> BLUGORI_HOOD;
	public static final DeferredItem<Item> SOUL_KING_CROWN;
	public static final DeferredItem<Item> BASTILLE_MASK;
	public static final DeferredItem<Item> COLA_BACKPACK;
	public static final DeferredItem<Item> MEDIC_BAG;
	public static final DeferredItem<Item> TOMOE_DRUMS;
	public static final DeferredItem<Item> WOOTZ_STEEL_ARMOR;
	public static final DeferredItem<Item> MARINE_CAPTAIN_CAPE;
	public static final DeferredItem<Item> PIRATE_CAPTAIN_CAPE;
	public static final DeferredItem<Item> FLUFFY_CAPE;
	public static final DeferredItem<Item> WHITE_WALKIE_COAT;

	public static void init() {
	}
	static  {
		Supplier<Item> var0 = () -> {
			return new ModArmorItem(ModMaterials.PIRATE_MATERIAL,ArmorItem.Type.CHESTPLATE);
		};
		PIRATE_CHEST = (DeferredItem) (ModRegistry.registerItem("Pirate Shirt",var0));
		Supplier<Item> var1 = () -> {
			return new ModArmorItem(ModMaterials.PIRATE_MATERIAL,ArmorItem.Type.LEGGINGS);
		};
		PIRATE_LEGS = (DeferredItem) (ModRegistry.registerItem("Pirate Pants",var1));
		Supplier<Item> var2 = () -> {
			return new ModArmorItem(ModMaterials.PIRATE_MATERIAL,ArmorItem.Type.BOOTS);
		};
		PIRATE_FEET = (DeferredItem) (ModRegistry.registerItem("Pirate Boots",var2));
		Supplier<Item> var3 = () -> {
			return new ModArmorItem(ModMaterials.MARINE_MATERIAL,ArmorItem.Type.HELMET);
		};
		MARINE_HEAD = (DeferredItem) (ModRegistry.registerItem("Marine Hat",var3));
		Supplier<Item> var4 = () -> {
			return new ModArmorItem(ModMaterials.MARINE_MATERIAL,ArmorItem.Type.CHESTPLATE);
		};
		MARINE_CHEST = (DeferredItem) (ModRegistry.registerItem("Marine Shirt",var4));
		Supplier<Item> var5 = () -> {
			return new ModArmorItem(ModMaterials.MARINE_MATERIAL,ArmorItem.Type.LEGGINGS);
		};
		MARINE_LEGS = (DeferredItem) (ModRegistry.registerItem("Marine Pants",var5));
		Supplier<Item> var6 = () -> {
			return new ModArmorItem(ModMaterials.MARINE_MATERIAL,ArmorItem.Type.BOOTS);
		};
		MARINE_FEET = (DeferredItem) (ModRegistry.registerItem("Marine Boots",var6));
		Supplier<Mod3DArmorItem> var7 = () -> {
			int[] lv8_0 = new int[]{16722731};
			return new Mod3DArmorItem(ModMaterials.STRAW_HAT_MATERIAL,ArmorItem.Type.HELMET,new Item.Properties().component(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(lv8_0)));
		};
		STRAW_HAT = (DeferredItem) (ModRegistry.registerItem("Straw Hat",var7));
		Supplier<Item> var8 = () -> {
			return new ModArmorItem(ModMaterials.LUFFY_MATERIAL,ArmorItem.Type.CHESTPLATE);
		};
		LUFFY_SHIRT = (DeferredItem) (ModRegistry.registerItem("Luffy's Shirt",var8));
		Supplier<Item> var9 = () -> {
			return new ModArmorItem(ModMaterials.LUFFY_MATERIAL,ArmorItem.Type.LEGGINGS);
		};
		LUFFY_PANTS = (DeferredItem) (ModRegistry.registerItem("Luffy's Pants",var9));
		Supplier<Item> var10 = () -> {
			return new ModArmorItem(ModMaterials.LUFFY_MATERIAL,ArmorItem.Type.BOOTS);
		};
		LUFFY_SANDALS = (DeferredItem) (ModRegistry.registerItem("Luffy's Sandals",var10));
		Supplier<Item> var11 = () -> {
			return new ModArmorItem(ModMaterials.ZORO_MATERIAL,ArmorItem.Type.CHESTPLATE);
		};
		ZORO_CHEST = (DeferredItem) (ModRegistry.registerItem("Zoro's Shirt",var11));
		Supplier<Item> var12 = () -> {
			return new ModArmorItem(ModMaterials.ZORO_MATERIAL,ArmorItem.Type.LEGGINGS);
		};
		ZORO_LEGS = (DeferredItem) (ModRegistry.registerItem("Zoro's Pants",var12));
		Supplier<Item> var13 = () -> {
			return new ModArmorItem(ModMaterials.ZORO_MATERIAL,ArmorItem.Type.BOOTS);
		};
		ZORO_FEET = (DeferredItem) (ModRegistry.registerItem("Zoro's Boots",var13));
		Supplier<Item> var14 = () -> {
			return new ModArmorItem(ModMaterials.SANJI_MATERIAL,ArmorItem.Type.CHESTPLATE);
		};
		SANJI_CHEST = (DeferredItem) (ModRegistry.registerItem("Sanji's Shirt",var14));
		Supplier<Item> var15 = () -> {
			return new ModArmorItem(ModMaterials.SANJI_MATERIAL,ArmorItem.Type.LEGGINGS);
		};
		SANJI_LEGS = (DeferredItem) (ModRegistry.registerItem("Sanji's Pants",var15));
		Supplier<Item> var16 = () -> {
			return new ModArmorItem(ModMaterials.SANJI_MATERIAL,ArmorItem.Type.BOOTS);
		};
		SANJI_FEET = (DeferredItem) (ModRegistry.registerItem("Sanji's Shoes",var16));
		Supplier<Item> var17 = () -> {
			return new ModArmorItem(ModMaterials.USOPP_MATERIAL,ArmorItem.Type.CHESTPLATE);
		};
		USOPP_CHEST = (DeferredItem) (ModRegistry.registerItem("Usopp's Overall",var17));
		Supplier<Item> var18 = () -> {
			return new ModArmorItem(ModMaterials.USOPP_MATERIAL,ArmorItem.Type.LEGGINGS);
		};
		USOPP_LEGS = (DeferredItem) (ModRegistry.registerItem("Usopp's Pants",var18));
		Supplier<Item> var19 = () -> {
			return new ModArmorItem(ModMaterials.USOPP_MATERIAL,ArmorItem.Type.BOOTS);
		};
		USOPP_FEET = (DeferredItem) (ModRegistry.registerItem("Usopp's Boots",var19));
		Supplier<Item> var20 = () -> {
			return new ModArmorItem(ModMaterials.FRANKY_MATERIAL,ArmorItem.Type.HELMET);
		};
		FRANKY_GLASSES = (DeferredItem) (ModRegistry.registerItem("Franky's Glasses",var20));
		Supplier<Item> var21 = () -> {
			return new ModArmorItem(ModMaterials.FRANKY_MATERIAL,ArmorItem.Type.CHESTPLATE);
		};
		FRANKY_CHEST = (DeferredItem) (ModRegistry.registerItem("Franky's Shirt",var21));
		Supplier<Item> var22 = () -> {
			int[] lv23_0 = new int[]{16155836,7097236};
			return new OverShoulderHelmetItem(ModMaterials.SENOR_PINK_BONNET_MATERIAL,ArmorItem.Type.HELMET,new Item.Properties().component(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(lv23_0)));
		};
		SENOR_PINK_HEAD = (DeferredItem) (ModRegistry.registerItem("Senor Pink's Bonnet",var22));
		Supplier<Item> var23 = () -> {
			return new ModArmorItem(ModMaterials.SENOR_PINK_MATERIAL,ArmorItem.Type.CHESTPLATE);
		};
		SENOR_PINK_CHEST = (DeferredItem) (ModRegistry.registerItem("Senor Pink's Shirt",var23));
		Supplier<Item> var24 = () -> {
			int[] lv25_0 = new int[]{16115811,14959424};
			return new Mod3DArmorItem(ModMaterials.SENOR_PINK_DIAPER_MATERIAL,ArmorItem.Type.LEGGINGS,new Item.Properties().component(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(lv25_0)));
		};
		SENOR_PINK_DIAPER = (DeferredItem) (ModRegistry.registerItem("Senor Pink's Diaper",var24));
		Supplier<ModArmorItem> var25 = () -> {
			return new ModArmorItem(ModMaterials.VICE_ADMIRAL_MATERIAL,ArmorItem.Type.CHESTPLATE,new Item.Properties().component(ModDataComponents.MULTI_CHANNEL_DYES,MultiChannelItemColor.CLOTH_COLOR));
		};
		VICE_ADMIRAL_CHEST = (DeferredItem) (ModRegistry.registerItem("Vice Admiral Jacket",var25));
		Supplier<ModArmorItem> var26 = () -> {
			return new ModArmorItem(ModMaterials.VICE_ADMIRAL_MATERIAL,ArmorItem.Type.LEGGINGS,new Item.Properties().component(ModDataComponents.MULTI_CHANNEL_DYES,MultiChannelItemColor.CLOTH_COLOR));
		};
		VICE_ADMIRAL_LEGS = (DeferredItem) (ModRegistry.registerItem("Vice Admiral Pants",var26));
		Supplier<ModArmorItem> var27 = () -> {
			return new ModArmorItem(ModMaterials.VICE_ADMIRAL_MATERIAL,ArmorItem.Type.BOOTS,new Item.Properties().component(ModDataComponents.MULTI_CHANNEL_DYES,MultiChannelItemColor.CLOTH_COLOR));
		};
		VICE_ADMIRAL_FEET = (DeferredItem) (ModRegistry.registerItem("Vice Admiral Boots",var27));
		Supplier<Item> var28 = () -> {
			return new ModArmorItem(ModMaterials.CP9_MATERIAL,ArmorItem.Type.CHESTPLATE);
		};
		CP9_CHEST = (DeferredItem) (ModRegistry.registerItem("CP9 Jacket",var28));
		Supplier<Item> var29 = () -> {
			return new ModArmorItem(ModMaterials.CP9_MATERIAL,ArmorItem.Type.LEGGINGS);
		};
		CP9_LEGS = (DeferredItem) (ModRegistry.registerItem("CP9 Pants",var29));
		Supplier<Item> var30 = () -> {
			return new ModArmorItem(ModMaterials.CP9_MATERIAL,ArmorItem.Type.BOOTS);
		};
		CP9_FEET = (DeferredItem) (ModRegistry.registerItem("CP9 Boots",var30));
		Supplier<Item> var31 = () -> {
			return new ModArmorItem(ModMaterials.KUMA_MATERIAL,ArmorItem.Type.CHESTPLATE);
		};
		KUMA_CHEST = (DeferredItem) (ModRegistry.registerItem("Kuma Shirt",var31));
		Supplier<Item> var32 = () -> {
			return new ModArmorItem(ModMaterials.KUMA_MATERIAL,ArmorItem.Type.LEGGINGS);
		};
		KUMA_LEGS = (DeferredItem) (ModRegistry.registerItem("Kuma Pants",var32));
		Supplier<Item> var33 = () -> {
			return new ModArmorItem(ModMaterials.KUMA_MATERIAL,ArmorItem.Type.BOOTS);
		};
		KUMA_FEET = (DeferredItem) (ModRegistry.registerItem("Kuma Boots",var33));
		Supplier<Item> var34 = () -> {
			return new ModArmorItem(ModMaterials.SMOKER_MATERIAL,ArmorItem.Type.CHESTPLATE);
		};
		SMOKER_CHEST = (DeferredItem) (ModRegistry.registerItem("Smoker Jacket",var34));
		Supplier<Item> var35 = () -> {
			return new ModArmorItem(ModMaterials.SMOKER_MATERIAL,ArmorItem.Type.LEGGINGS);
		};
		SMOKER_LEGS = (DeferredItem) (ModRegistry.registerItem("Smoker Pants",var35));
		Supplier<Item> var36 = () -> {
			return new ModArmorItem(ModMaterials.SMOKER_MATERIAL,ArmorItem.Type.BOOTS);
		};
		SMOKER_FEET = (DeferredItem) (ModRegistry.registerItem("Smoker Boots",var36));
		Supplier<Item> var37 = () -> {
			return new ModArmorItem(ModMaterials.KURO_MATERIAL,ArmorItem.Type.HELMET);
		};
		KURO_GLASSES = (DeferredItem) (ModRegistry.registerItem("Kuro's Glasses",var37));
		Supplier<Item> var38 = () -> {
			return new ModArmorItem(ModMaterials.KURO_MATERIAL,ArmorItem.Type.CHESTPLATE);
		};
		KURO_CHEST = (DeferredItem) (ModRegistry.registerItem("Kuro's Suit",var38));
		Supplier<Item> var39 = () -> {
			return new ModArmorItem(ModMaterials.KURO_MATERIAL,ArmorItem.Type.LEGGINGS);
		};
		KURO_LEGS = (DeferredItem) (ModRegistry.registerItem("Kuro's Pants",var39));
		Supplier<Item> var40 = () -> {
			return new ModArmorItem(ModMaterials.KURO_MATERIAL,ArmorItem.Type.BOOTS);
		};
		KURO_FEET = (DeferredItem) (ModRegistry.registerItem("Kuro's Shoes",var40));
		Supplier<Item> var41 = () -> {
			return new PearlArmorItem(ArmorItem.Type.HELMET);
		};
		PEARL_HAT = (DeferredItem) (ModRegistry.registerItem("Pearl Head Armor",var41));
		Supplier<Item> var42 = () -> {
			return new PearlArmorItem(ArmorItem.Type.CHESTPLATE);
		};
		PEARL_ARMOR = (DeferredItem) (ModRegistry.registerItem("Pearl Body Armor",var42));
		Supplier<Item> var43 = () -> {
			return new PearlArmorItem(ArmorItem.Type.LEGGINGS);
		};
		PEARL_LEGS = (DeferredItem) (ModRegistry.registerItem("Pearl Legs Armor",var43));
		Supplier<Item> var44 = () -> {
			return new ModArmorItem(ModMaterials.ARLONG_MATERIAL,ArmorItem.Type.HELMET);
		};
		ARLONG_HAT = (DeferredItem) (ModRegistry.registerItem("Arlong's Hat",var44));
		Supplier<Item> var45 = () -> {
			return new ModArmorItem(ModMaterials.ARLONG_MATERIAL,ArmorItem.Type.CHESTPLATE);
		};
		ARLONG_CHEST = (DeferredItem) (ModRegistry.registerItem("Arlong's Shirt",var45));
		Supplier<Item> var46 = () -> {
			return new ModArmorItem(ModMaterials.ARLONG_MATERIAL,ArmorItem.Type.LEGGINGS);
		};
		ARLONG_LEGS = (DeferredItem) (ModRegistry.registerItem("Arlong's Pants",var46));
		Supplier<Item> var47 = () -> {
			return new ModArmorItem(ModMaterials.ARLONG_MATERIAL,ArmorItem.Type.BOOTS);
		};
		ARLONG_FEET = (DeferredItem) (ModRegistry.registerItem("Arlong's Shoes",var47));
		Supplier<Item> var48 = () -> {
			return new ModArmorItem(ModMaterials.CHEW_MATERIAL,ArmorItem.Type.CHESTPLATE);
		};
		CHEW_CHEST = (DeferredItem) (ModRegistry.registerItem("Chew's Shirt",var48));
		Supplier<Item> var49 = () -> {
			return new ModArmorItem(ModMaterials.CHEW_MATERIAL,ArmorItem.Type.LEGGINGS);
		};
		CHEW_LEGS = (DeferredItem) (ModRegistry.registerItem("Chew's Pants",var49));
		Supplier<Item> var50 = () -> {
			return new ModArmorItem(ModMaterials.CHEW_MATERIAL,ArmorItem.Type.BOOTS);
		};
		CHEW_FEET = (DeferredItem) (ModRegistry.registerItem("Chew's Shoes",var50));
		Supplier<Item> var51 = () -> {
			return new ModArmorItem(ModMaterials.KUROOBI_MATERIAL,ArmorItem.Type.CHESTPLATE);
		};
		KUROOBI_CHEST = (DeferredItem) (ModRegistry.registerItem("Kuroobi's Ki",var51));
		Supplier<Item> var52 = () -> {
			return new ModArmorItem(ModMaterials.KUROOBI_MATERIAL,ArmorItem.Type.LEGGINGS);
		};
		KUROOBI_LEGS = (DeferredItem) (ModRegistry.registerItem("Kuroobi's Pants",var52));
		Supplier<Item> var53 = () -> {
			return new ModArmorItem(ModMaterials.KUROOBI_MATERIAL,ArmorItem.Type.BOOTS);
		};
		KUROOBI_FEET = (DeferredItem) (ModRegistry.registerItem("Kuroobi's Shoes",var53));
		Supplier<Item> var54 = () -> {
			return new ModArmorItem(ModMaterials.CROCODILE_MATERIAL,ArmorItem.Type.CHESTPLATE);
		};
		CROCODILE_CHEST = (DeferredItem) (ModRegistry.registerItem("Crocodile's Suit",var54));
		Supplier<Item> var55 = () -> {
			return new ModArmorItem(ModMaterials.CROCODILE_MATERIAL,ArmorItem.Type.LEGGINGS);
		};
		CROCODILE_LEGS = (DeferredItem) (ModRegistry.registerItem("Crocodile's Pants",var55));
		Supplier<Item> var56 = () -> {
			return new ModArmorItem(ModMaterials.CROCODILE_MATERIAL,ArmorItem.Type.BOOTS);
		};
		CROCODILE_FEET = (DeferredItem) (ModRegistry.registerItem("Crocodile's Shoes",var56));
		Supplier<Item> var57 = () -> {
			return new ModArmorItem(ModMaterials.MR1_MATERIAL,ArmorItem.Type.CHESTPLATE);
		};
		MR1_CHEST = (DeferredItem) (ModRegistry.registerItem("Mr 1's Shirt",var57));
		Supplier<Item> var58 = () -> {
			return new ModArmorItem(ModMaterials.MR1_MATERIAL,ArmorItem.Type.LEGGINGS);
		};
		MR1_LEGS = (DeferredItem) (ModRegistry.registerItem("Mr 1's Pants",var58));
		Supplier<Item> var59 = () -> {
			return new ModArmorItem(ModMaterials.MR1_MATERIAL,ArmorItem.Type.BOOTS);
		};
		MR1_FEET = (DeferredItem) (ModRegistry.registerItem("Mr 1's Shoes",var59));
		Supplier<Item> var60 = () -> {
			return new ModArmorItem(ModMaterials.MR3_MATERIAL,ArmorItem.Type.HELMET);
		};
		MR3_GLASSES = (DeferredItem) (ModRegistry.registerItem("Mr 3's Glasses",var60));
		Supplier<Item> var61 = () -> {
			return new ModArmorItem(ModMaterials.MR3_MATERIAL,ArmorItem.Type.CHESTPLATE);
		};
		MR3_CHEST = (DeferredItem) (ModRegistry.registerItem("Mr 3's Shirt",var61));
		Supplier<Item> var62 = () -> {
			return new ModArmorItem(ModMaterials.MR3_MATERIAL,ArmorItem.Type.LEGGINGS);
		};
		MR3_LEGS = (DeferredItem) (ModRegistry.registerItem("Mr 3's Pants",var62));
		Supplier<Item> var63 = () -> {
			return new ModArmorItem(ModMaterials.MR3_MATERIAL,ArmorItem.Type.BOOTS);
		};
		MR3_FEET = (DeferredItem) (ModRegistry.registerItem("Mr 3's Shoes",var63));
		Supplier<Item> var64 = () -> {
			return new ModArmorItem(ModMaterials.MR5_MATERIAL,ArmorItem.Type.HELMET);
		};
		MR5_GLASSES = (DeferredItem) (ModRegistry.registerItem("Mr 5's Glasses",var64));
		Supplier<Item> var65 = () -> {
			return new ModArmorItem(ModMaterials.MR5_MATERIAL,ArmorItem.Type.CHESTPLATE);
		};
		MR5_CHEST = (DeferredItem) (ModRegistry.registerItem("Mr 5's Shirt",var65));
		Supplier<Item> var66 = () -> {
			return new ModArmorItem(ModMaterials.MR5_MATERIAL,ArmorItem.Type.LEGGINGS);
		};
		MR5_LEGS = (DeferredItem) (ModRegistry.registerItem("Mr 5's Pants",var66));
		Supplier<Item> var67 = () -> {
			return new ModArmorItem(ModMaterials.MR5_MATERIAL,ArmorItem.Type.BOOTS);
		};
		MR5_FEET = (DeferredItem) (ModRegistry.registerItem("Mr 5's Shoes",var67));
		Supplier<Item> var68 = () -> {
			int[] lv69_0 = new int[]{0};
			return new ModArmorItem(ModMaterials.BANDANA_MATERIAL,ArmorItem.Type.HELMET,new Item.Properties().component(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(lv69_0)));
		};
		BANDANA = (DeferredItem) (ModRegistry.registerItem("Bandana",var68));
		SNIPER_GOGGLES = (DeferredItem) (ModRegistry.registerItem("Sniper Goggles",(Supplier)(((Supplier<Item>)(SniperGogglesItem::new)))));
		Supplier<Item> var69 = () -> {
			return new Mod3DArmorItem(ModMaterials.GAS_MASK_MATERIAL,ArmorItem.Type.HELMET);
		};
		MH5_GAS_MASK = (DeferredItem) (ModRegistry.registerItem("MH5 Gas Mask",var69));
		Supplier<Item> var70 = () -> {
			return new Mod3DArmorItem(ModMaterials.CHOPPER_HAT_PRE_MATERIAL,ArmorItem.Type.HELMET);
		};
		CHOPPER_HAT = (DeferredItem) (ModRegistry.registerItem("Chopper's Hat",var70));
		Supplier<Item> var71 = () -> {
			return new Mod3DArmorItem(ModMaterials.CHOPPER_HAT_POST_MATERIAL,ArmorItem.Type.HELMET);
		};
		CHOPPER_HAT_2 = (DeferredItem) (ModRegistry.registerItem("choppers_hat_2","Chopper's Hat",var71));
		Supplier<Item> var72 = () -> {
			return new Mod3DArmorItem(ModMaterials.ACE_HAT_MATERIAL,ArmorItem.Type.HELMET);
		};
		ACE_HAT = (DeferredItem) (ModRegistry.registerItem("Ace's Hat",var72));
		Supplier<Item> var73 = () -> {
			return new ModArmorItem(ModMaterials.KIZARU_GLASSES_MATERIAL,ArmorItem.Type.HELMET);
		};
		KIZARU_GLASSES = (DeferredItem) (ModRegistry.registerItem("Kizaru's Glasses",var73));
		Supplier<Item> var74 = () -> {
			return new Mod3DArmorItem(ModMaterials.KILLER_MASK_MATERIAL,ArmorItem.Type.HELMET);
		};
		KILLER_MASK = (DeferredItem) (ModRegistry.registerItem("Killer's Mask",var74));
		Supplier<Item> var75 = () -> {
			return new Mod3DArmorItem(ModMaterials.LAW_HAT_MATERIAL,ArmorItem.Type.HELMET);
		};
		LAW_HAT = (DeferredItem) (ModRegistry.registerItem("Law's Hat",var75));
		Supplier<Item> var76 = () -> {
			int[] lv77_0 = new int[]{8932372};
			return new Mod3DArmorItem(ModMaterials.TRICORNE_MATERIAL,ArmorItem.Type.HELMET,new Item.Properties().component(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(lv77_0)));
		};
		TRICORNE = (DeferredItem) (ModRegistry.registerItem("Tricorne",var76));
		Supplier<Item> var77 = () -> {
			return new Mod3DArmorItem(ModMaterials.SABO_HAT_MATERIAL,ArmorItem.Type.HELMET);
		};
		SABO_HAT = (DeferredItem) (ModRegistry.registerItem("Sabo's Hat",var77));
		Supplier<Item> var78 = () -> {
			int[] lv79_0 = new int[]{16420857};
			return new ModArmorItem(ModMaterials.DOFFY_GLASSES_MATERIAL,ArmorItem.Type.HELMET,new Item.Properties().component(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(lv79_0)));
		};
		DOFFY_GLASSES = (DeferredItem) (ModRegistry.registerItem("Doflamingo's Glasses",var78));
		Supplier<Item> var79 = () -> {
			int[] lv80_0 = new int[]{11202757,1973533};
			return new Mod3DArmorItem(ModMaterials.MIHAWK_HAT_MATERIAL,ArmorItem.Type.HELMET,new Item.Properties().component(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(lv80_0)));
		};
		MIHAWK_HAT = (DeferredItem) (ModRegistry.registerItem("Mihawk's Hat",var79));
		Supplier<Item> var80 = () -> {
			return new Mod3DArmorItem(ModMaterials.FLEET_ADMIRAL_HAT_MATERIAL,ArmorItem.Type.HELMET);
		};
		FLEET_ADMIRAL_HAT = (DeferredItem) (ModRegistry.registerItem("Fleet Admiral's Hat",var80));
		Supplier<Item> var81 = () -> {
			return new Mod3DArmorItem(ModMaterials.CELESTIAL_DRAGON_BUBBLE_MATERIAL,ArmorItem.Type.HELMET,new Item.Properties().component(ModDataComponents.MULTI_CHANNEL_DYES,MultiChannelItemColor.CLOTH_COLOR));
		};
		CELESTIAL_DRAGON_BUBBLE = (DeferredItem) (ModRegistry.registerItem("Celestial Dragon Bubble",var81));
		Supplier<Item> var82 = () -> {
			int[] lv83_0 = new int[]{12400461,15791090};
			return new Mod3DArmorItem(ModMaterials.PLUME_HAT_MATERIAL,ArmorItem.Type.HELMET,new Item.Properties().component(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(lv83_0)));
		};
		PLUME_HAT = (DeferredItem) (ModRegistry.registerItem("Plume Hat",var82));
		Supplier<Item> var83 = () -> {
			int[] lv84_0 = new int[]{13459968,-3092272};
			return new Mod3DArmorItem(ModMaterials.BICORNE_MATERIAL,ArmorItem.Type.HELMET,new Item.Properties().component(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(lv84_0)));
		};
		BICORNE = (DeferredItem) (ModRegistry.registerItem("Bicorne",var83));
		Supplier<Item> var84 = () -> {
			int[] lv85_0 = new int[]{1581133};
			return new Mod3DArmorItem(ModMaterials.WIDE_BRIM_HAT_MATERIAL,ArmorItem.Type.HELMET,new Item.Properties().component(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(lv85_0)));
		};
		WIDE_BRIM_HAT = (DeferredItem) (ModRegistry.registerItem("Wide Brim Hat",var84));
		Supplier<Item> var85 = () -> {
			return new Mod3DArmorItem(ModMaterials.HEART_GLASSES_MATERIAL,ArmorItem.Type.HELMET);
		};
		HEART_GLASSES = (DeferredItem) (ModRegistry.registerItem("Heart Glasses",var85));
		Supplier<Item> var86 = () -> {
			int[] lv87_0 = new int[]{2844864,16053491};
			return new Mod3DArmorItem(ModMaterials.CABAJI_SCARF_MATERIAL,ArmorItem.Type.HELMET,new Item.Properties().component(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(lv87_0)));
		};
		CABAJI_SCARF = (DeferredItem) (ModRegistry.registerItem("Cabaji's Scarf",var86));
		BIG_RED_NOSE = (DeferredItem) (ModRegistry.registerItem("Big Red Nose",(Supplier)(((Supplier<Item>)(BigRedNoseItem::new)))));
		Supplier<Item> var87 = () -> {
			return new Mod3DArmorItem(ModMaterials.IRON_JAW_MATERIAL,ArmorItem.Type.HELMET);
		};
		IRON_JAW = (DeferredItem) (ModRegistry.registerItem("Iron Jaw",var87));
		Supplier<Item> var88 = () -> {
			int[] lv89_0 = new int[]{31700};
			return new Mod3DArmorItem(ModMaterials.WIZARD_HAT_MATERIAL,ArmorItem.Type.HELMET,new Item.Properties().component(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(lv89_0)));
		};
		WIZARD_HAT = (DeferredItem) (ModRegistry.registerItem("Wizard Hat",var88));
		Supplier<Item> var89 = () -> {
			return new Mod3DArmorItem(ModMaterials.SOGEKING_MASK_MATERIAL,ArmorItem.Type.HELMET);
		};
		SOGEKING_MASK = (DeferredItem) (ModRegistry.registerItem("Sogeking Mask",var89));
		Supplier<Item> var90 = () -> {
			return new Mod3DArmorItem(ModMaterials.WHOS_WHO_MASK_MATERIAL,ArmorItem.Type.HELMET);
		};
		WHOS_WHO_MASK = (DeferredItem) (ModRegistry.registerItem("Who's Who Mask",var90));
		Supplier<Item> var91 = () -> {
			int[] lv92_0 = new int[]{8831997,810414};
			return new OverShoulderHelmetItem(ModMaterials.BLUGORI_HOOD_MATERIAL,ArmorItem.Type.HELMET,new Item.Properties().component(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(lv92_0)));
		};
		BLUGORI_HOOD = (DeferredItem) (ModRegistry.registerItem("Blugori Hood",var91));
		Supplier<Item> var92 = () -> {
			return new Mod3DArmorItem(ModMaterials.SOUL_KING_CROWN_MATERIAL,ArmorItem.Type.HELMET);
		};
		SOUL_KING_CROWN = (DeferredItem) (ModRegistry.registerItem("Soul King Crown",var92));
		Supplier<Item> var93 = () -> {
			return new Mod3DArmorItem(ModMaterials.BASTILLE_MASK_MATERIAL,ArmorItem.Type.HELMET);
		};
		BASTILLE_MASK = (DeferredItem) (ModRegistry.registerItem("Bastille Mask",var93));
		COLA_BACKPACK = (DeferredItem) (ModRegistry.registerItem("Cola Backpack",(Supplier)(((Supplier<Item>)(ColaBackpackItem::new)))));
		Supplier<Item> var94 = () -> {
			return new Mod3DArmorItem(ModMaterials.MEDIC_BAG_MATERIAL,ArmorItem.Type.CHESTPLATE,new Item.Properties().durability(2000));
		};
		MEDIC_BAG = (DeferredItem) (ModRegistry.registerItem("Medic Bag",var94));
		Supplier<Item> var95 = () -> {
			return new Mod3DArmorItem(ModMaterials.TOMOE_DRUMS_MATERIAL,ArmorItem.Type.CHESTPLATE,new Item.Properties());
		};
		TOMOE_DRUMS = (DeferredItem) (ModRegistry.registerItem("Tomoe Drums",var95));
		WOOTZ_STEEL_ARMOR = (DeferredItem) (ModRegistry.registerItem("Wootz Steel Armor",(Supplier)(((Supplier<Item>)(WootzArmorItem::new)))));
		Supplier<Item> var96 = () -> {
			int[] lv97_0 = new int[]{6395589};
			return new Mod3DArmorItem(ModMaterials.MARINE_CAPTAIN_CAPE_MATERIAL,ArmorItem.Type.CHESTPLATE,new Item.Properties().component(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(lv97_0)));
		};
		MARINE_CAPTAIN_CAPE = (DeferredItem) (ModRegistry.registerItem("Marine Captain Cape",var96));
		Supplier<Item> var97 = () -> {
			int[] lv98_0 = new int[]{2949120};
			return new Mod3DArmorItem(ModMaterials.PIRATE_CAPTAIN_CAPE_MATERIAL,ArmorItem.Type.CHESTPLATE,new Item.Properties().component(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(lv98_0)));
		};
		PIRATE_CAPTAIN_CAPE = (DeferredItem) (ModRegistry.registerItem("Pirate Captain Cape",var97));
		Supplier<Item> var98 = () -> {
			int[] lv99_0 = new int[]{16754169};
			return new Mod3DArmorItem(ModMaterials.FLUFFY_CAPE_MATERIAL,ArmorItem.Type.CHESTPLATE,new Item.Properties().component(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(lv99_0)));
		};
		FLUFFY_CAPE = (DeferredItem) (ModRegistry.registerItem("Fluffy Cape",var98));
		Supplier<Item> var99 = () -> {
			return new Mod3DArmorItem(ModMaterials.WHITE_WALKIE_COAT_MATERIAL,ArmorItem.Type.CHESTPLATE);
		};
		WHITE_WALKIE_COAT = (DeferredItem) (ModRegistry.registerItem("White Walkie Coat",var99));
	}
}
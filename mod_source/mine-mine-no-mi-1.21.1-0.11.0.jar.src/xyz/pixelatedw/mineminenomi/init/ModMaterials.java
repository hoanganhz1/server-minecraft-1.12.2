package xyz.pixelatedw.mineminenomi.init;

import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import xyz.pixelatedw.mineminenomi.api.WyArmorMaterial$Builder;

public class ModMaterials {
	// Fields
	private static final int ENCHANTABILITY_LOW = 5;
	private static final int ENCHANTABILITY_MEDIUM = 15;
	private static final int ENCHANTABILITY_HIGH = 25;
	public static final Holder<ArmorMaterial> PIRATE_MATERIAL = leather("pirate").build();
	public static final Holder<ArmorMaterial> MARINE_MATERIAL = leather("marine").build();
	public static final Holder<ArmorMaterial> LUFFY_MATERIAL = leather("luffy").build();
	public static final Holder<ArmorMaterial> ZORO_MATERIAL = leather("zoro").build();
	public static final Holder<ArmorMaterial> SANJI_MATERIAL = leather("sanji").build();
	public static final Holder<ArmorMaterial> USOPP_MATERIAL = leather("usopp").build();
	public static final Holder<ArmorMaterial> FRANKY_MATERIAL = leather("franky").build();
	public static final Holder<ArmorMaterial> SENOR_PINK_MATERIAL = leather("senorpink").build();
	public static final Holder<ArmorMaterial> SENOR_PINK_BONNET_MATERIAL = leather("senorpink_bonnet").setDyeableLayers(2).build();
	public static final Holder<ArmorMaterial> SENOR_PINK_DIAPER_MATERIAL = leather("senorpink_diaper").setDyeableLayers(2).build();
	public static final Holder<ArmorMaterial> CP9_MATERIAL = leather("cp9").build();
	public static final Holder<ArmorMaterial> KUMA_MATERIAL = leather("kuma").build();
	public static final Holder<ArmorMaterial> SMOKER_MATERIAL = leather("smoker").build();
	public static final Holder<ArmorMaterial> KURO_MATERIAL = leather("kuro").build();
	public static final Holder<ArmorMaterial> ARLONG_MATERIAL = leather("arlong").build();
	public static final Holder<ArmorMaterial> CHEW_MATERIAL = leather("chew").build();
	public static final Holder<ArmorMaterial> KUROOBI_MATERIAL = leather("kuroobi").build();
	public static final Holder<ArmorMaterial> CROCODILE_MATERIAL = leather("crocodile").build();
	public static final Holder<ArmorMaterial> MR1_MATERIAL = leather("mr1").build();
	public static final Holder<ArmorMaterial> MR3_MATERIAL = leather("mr3").build();
	public static final Holder<ArmorMaterial> MR5_MATERIAL = leather("mr5").build();
	public static final Holder<ArmorMaterial> VICE_ADMIRAL_MATERIAL = leather("vice_admiral").setDyeableLayers(1).build();
	public static final Holder<ArmorMaterial> STRAW_HAT_MATERIAL;
	public static final Holder<ArmorMaterial> BANDANA_MATERIAL;
	public static final Holder<ArmorMaterial> SNIPER_GOGGLES_MATERIAL;
	public static final Holder<ArmorMaterial> GAS_MASK_MATERIAL;
	public static final Holder<ArmorMaterial> CHOPPER_HAT_PRE_MATERIAL;
	public static final Holder<ArmorMaterial> CHOPPER_HAT_POST_MATERIAL;
	public static final Holder<ArmorMaterial> ACE_HAT_MATERIAL;
	public static final Holder<ArmorMaterial> KIZARU_GLASSES_MATERIAL;
	public static final Holder<ArmorMaterial> KILLER_MASK_MATERIAL;
	public static final Holder<ArmorMaterial> LAW_HAT_MATERIAL;
	public static final Holder<ArmorMaterial> TRICORNE_MATERIAL;
	public static final Holder<ArmorMaterial> BICORNE_MATERIAL;
	public static final Holder<ArmorMaterial> SABO_HAT_MATERIAL;
	public static final Holder<ArmorMaterial> DOFFY_GLASSES_MATERIAL;
	public static final Holder<ArmorMaterial> MIHAWK_HAT_MATERIAL;
	public static final Holder<ArmorMaterial> FLEET_ADMIRAL_HAT_MATERIAL;
	public static final Holder<ArmorMaterial> CELESTIAL_DRAGON_BUBBLE_MATERIAL;
	public static final Holder<ArmorMaterial> PLUME_HAT_MATERIAL;
	public static final Holder<ArmorMaterial> WIDE_BRIM_HAT_MATERIAL;
	public static final Holder<ArmorMaterial> HEART_GLASSES_MATERIAL;
	public static final Holder<ArmorMaterial> CABAJI_SCARF_MATERIAL;
	public static final Holder<ArmorMaterial> BIG_RED_NOSE_MATERIAL;
	public static final Holder<ArmorMaterial> IRON_JAW_MATERIAL;
	public static final Holder<ArmorMaterial> WIZARD_HAT_MATERIAL;
	public static final Holder<ArmorMaterial> SOGEKING_MASK_MATERIAL;
	public static final Holder<ArmorMaterial> WHOS_WHO_MASK_MATERIAL;
	public static final Holder<ArmorMaterial> BLUGORI_HOOD_MATERIAL;
	public static final Holder<ArmorMaterial> SOUL_KING_CROWN_MATERIAL;
	public static final Holder<ArmorMaterial> BASTILLE_MASK_MATERIAL;
	public static final Holder<ArmorMaterial> MEDIC_BAG_MATERIAL;
	public static final Holder<ArmorMaterial> TOMOE_DRUMS_MATERIAL;
	public static final Holder<ArmorMaterial> WOOTZ_ARMOR_MATERIAL;
	public static final Holder<ArmorMaterial> MARINE_CAPTAIN_CAPE_MATERIAL;
	public static final Holder<ArmorMaterial> PIRATE_CAPTAIN_CAPE_MATERIAL;
	public static final Holder<ArmorMaterial> FLUFFY_CAPE_MATERIAL;
	public static final Holder<ArmorMaterial> WHITE_WALKIE_COAT_MATERIAL;
	public static final Holder<ArmorMaterial> COLA_BACKPACK_MATERIAL;
	public static final Holder<ArmorMaterial> PEARL_MATERIAL;

	private static WyArmorMaterial$Builder leather(String var0) {
		Supplier<Ingredient> var1 = () -> {
			ItemLike[] lv1_0 = new ItemLike[]{Items.LEATHER};
			return Ingredient.of(lv1_0);
		};
		return new WyArmorMaterial$Builder(ResourceLocation.fromNamespaceAndPath("mineminenomi",var0)).setEnchantability(5).setEquipSound(SoundEvents.ARMOR_EQUIP_LEATHER).setRepairIngredient(var1);
	}
	private static WyArmorMaterial$Builder wool(String var0) {
		Supplier<Ingredient> var1 = () -> {
			ItemLike[] lv2_0 = new ItemLike[]{Items.WHITE_WOOL};
			return Ingredient.of(lv2_0);
		};
		return new WyArmorMaterial$Builder(ResourceLocation.fromNamespaceAndPath("mineminenomi",var0)).setEnchantability(5).setEquipSound(Holder.direct(SoundEvents.WOOL_PLACE)).setRepairIngredient(var1);
	}
	private static WyArmorMaterial$Builder iron(String var0) {
		Supplier<Ingredient> var1 = () -> {
			ItemLike[] lv3_0 = new ItemLike[]{Items.IRON_INGOT};
			return Ingredient.of(lv3_0);
		};
		return new WyArmorMaterial$Builder(ResourceLocation.fromNamespaceAndPath("mineminenomi",var0)).setEnchantability(5).setEquipSound(SoundEvents.ARMOR_EQUIP_IRON).setRepairIngredient(var1);
	}
	private static WyArmorMaterial$Builder gold(String var0) {
		Supplier<Ingredient> var1 = () -> {
			ItemLike[] lv4_0 = new ItemLike[]{Items.GOLD_INGOT};
			return Ingredient.of(lv4_0);
		};
		return new WyArmorMaterial$Builder(ResourceLocation.fromNamespaceAndPath("mineminenomi",var0)).setEnchantability(5).setEquipSound(SoundEvents.ARMOR_EQUIP_GOLD).setRepairIngredient(var1);
	}
	private static WyArmorMaterial$Builder mask(String var0) {
		Supplier<Ingredient> var1 = () -> {
			return Ingredient.of(ItemTags.PLANKS);
		};
		return new WyArmorMaterial$Builder(ResourceLocation.fromNamespaceAndPath("mineminenomi",var0)).setEnchantability(5).setEquipSound(Holder.direct(SoundEvents.WOOD_HIT)).setRepairIngredient(var1);
	}
	private static WyArmorMaterial$Builder glasses(String var0) {
		Supplier<Ingredient> var1 = () -> {
			ItemLike[] lv6_0 = new ItemLike[]{Items.IRON_INGOT};
			return Ingredient.of(lv6_0);
		};
		return new WyArmorMaterial$Builder(ResourceLocation.fromNamespaceAndPath("mineminenomi",var0)).setEnchantability(5).setEquipSound(SoundEvents.ARMOR_EQUIP_CHAIN).setRepairIngredient(var1);
	}
	static  {
		Supplier<Ingredient> var0 = () -> {
			ItemLike[] lv7_0 = new ItemLike[]{Items.WHEAT};
			return Ingredient.of(lv7_0);
		};
		STRAW_HAT_MATERIAL = new WyArmorMaterial$Builder(ResourceLocation.fromNamespaceAndPath("mineminenomi","straw_hat")).setDyeableLayers(1).setEnchantability(15).setEquipSound(Holder.direct(SoundEvents.GRASS_HIT)).setRepairIngredient(var0).build();
		BANDANA_MATERIAL = wool("bandana").setDyeableLayers(1).build();
		SNIPER_GOGGLES_MATERIAL = iron("sniper_goggles").setEnchantability(15).build();
		GAS_MASK_MATERIAL = gold("mh5_gas_mask").setProtectionForSlot(ArmorItem.Type.HELMET,2).build();
		CHOPPER_HAT_PRE_MATERIAL = wool("chopper_hat").build();
		CHOPPER_HAT_POST_MATERIAL = wool("chopper_hat_2").build();
		ACE_HAT_MATERIAL = wool("ace_hat").build();
		KIZARU_GLASSES_MATERIAL = glasses("kizaru_glasses").build();
		KILLER_MASK_MATERIAL = iron("killer_mask").setEnchantability(15).setProtectionForSlot(ArmorItem.Type.HELMET,3).build();
		LAW_HAT_MATERIAL = wool("law_hat").build();
		TRICORNE_MATERIAL = wool("tricorne").setDyeableLayers(1).build();
		BICORNE_MATERIAL = wool("bicorne").setDyeableLayers(2).build();
		SABO_HAT_MATERIAL = wool("sabo_hat").build();
		DOFFY_GLASSES_MATERIAL = glasses("doffy_glasses").setDyeableLayers(1).build();
		MIHAWK_HAT_MATERIAL = wool("mihawk_hat").setDyeableLayers(2).build();
		FLEET_ADMIRAL_HAT_MATERIAL = wool("fleet_admiral_hat").build();
		CELESTIAL_DRAGON_BUBBLE_MATERIAL = new WyArmorMaterial$Builder(ResourceLocation.fromNamespaceAndPath("mineminenomi","celestial_dragon_bubble")).setDyeableLayers(1).setEnchantability(5).setEquipSound(Holder.direct(SoundEvents.GLASS_HIT)).build();
		PLUME_HAT_MATERIAL = wool("plume_hat").setDyeableLayers(1).build();
		WIDE_BRIM_HAT_MATERIAL = wool("wide_brim_hat").setDyeableLayers(1).build();
		HEART_GLASSES_MATERIAL = glasses("heart_glasses").build();
		CABAJI_SCARF_MATERIAL = wool("cabaji_scarf").setDyeableLayers(1).build();
		BIG_RED_NOSE_MATERIAL = wool("big_nose").setEnchantability(25).build();
		IRON_JAW_MATERIAL = iron("iron_jaw").build();
		WIZARD_HAT_MATERIAL = wool("wizard_hat").setDyeableLayers(1).build();
		SOGEKING_MASK_MATERIAL = mask("sogeking_mask").build();
		WHOS_WHO_MASK_MATERIAL = mask("whos_who_mask").build();
		BLUGORI_HOOD_MATERIAL = wool("blugori_hood").setDyeableLayers(1).build();
		SOUL_KING_CROWN_MATERIAL = gold("soul_king_crown").build();
		BASTILLE_MASK_MATERIAL = mask("bastille_mask").build();
		MEDIC_BAG_MATERIAL = wool("medic_bag").build();
		Supplier<Ingredient> var1 = () -> {
			ItemLike[] lv8_0 = new ItemLike[]{Items.IRON_INGOT,Items.GOLD_INGOT};
			return Ingredient.of(lv8_0);
		};
		TOMOE_DRUMS_MATERIAL = iron("tomoe_drums").setEnchantability(25).setRepairIngredient(var1).build();
		WOOTZ_ARMOR_MATERIAL = gold("wootz_armor").setEnchantability(25).setProtectionForSlot(ArmorItem.Type.CHESTPLATE,10).build();
		MARINE_CAPTAIN_CAPE_MATERIAL = wool("marine_captain_cape").setDyeableLayers(1).build();
		PIRATE_CAPTAIN_CAPE_MATERIAL = wool("pirate_captain_cape").setDyeableLayers(1).build();
		FLUFFY_CAPE_MATERIAL = wool("fluffy_cape").setDyeableLayers(1).build();
		WHITE_WALKIE_COAT_MATERIAL = wool("white_walkie_coat").build();
		COLA_BACKPACK_MATERIAL = iron("cola_backpack").setEnchantability(15).setProtectionForSlot(ArmorItem.Type.CHESTPLATE,2).build();
		PEARL_MATERIAL = iron("pearl").setEnchantability(15).setProtectionForSlot(ArmorItem.Type.HELMET,5).setProtectionForSlot(ArmorItem.Type.CHESTPLATE,7).setProtectionForSlot(ArmorItem.Type.LEGGINGS,5).build();
	}
}
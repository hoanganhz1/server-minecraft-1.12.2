package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import xyz.pixelatedw.mineminenomi.api.MappedTag;

public class ModTags$Items {
	// Fields
	public static final MappedTag<Item> IRON = new MappedTag(ResourceLocation.fromNamespaceAndPath("mineminenomi","iron_values"),"mapped_tags/items",Registries.ITEM);
	public static final MappedTag<Item> CONDUCTIVE = new MappedTag(ResourceLocation.fromNamespaceAndPath("mineminenomi","conductive_values"),"mapped_tags/items",Registries.ITEM);
	public static final MappedTag<Item> STRIKER_FUEL = new MappedTag(ResourceLocation.fromNamespaceAndPath("mineminenomi","striker_fuel"),"mapped_tags/items",Registries.ITEM);
	public static final TagKey<Item> KAIROSEKI = tag("kairoseki");
	public static final TagKey<Item> MAGNETIC = tag("magnetic");
	public static final TagKey<Item> RUSTY = tag("rusty");
	public static final TagKey<Item> RUST_IMMUNITY = tag("rust_immunity");
	public static final TagKey<Item> WEAPONS = tag("weapons");
	public static final TagKey<Item> TECHNOLOGY = tag("technology");
	public static final TagKey<Item> PUNCH = tag("punch");
	public static final TagKey<Item> BLUNTS = tag("blunts");
	public static final TagKey<Item> GUNS = tag("guns");
	public static final TagKey<Item> POLEARMS = tag("polearms");
	public static final TagKey<Item> CLIMA_TACTS = tag("clima_tacts");
	public static final TagKey<Item> SUPREME_GRADE = tag("supreme_grade");
	public static final TagKey<Item> GREAT_GRADE = tag("great_grade");
	public static final TagKey<Item> GUN_AMMO = tag("gun_ammo");
	public static final TagKey<Item> BAZOOKA_AMMO = tag("bazooka_ammo");
	public static final TagKey<Item> ITEM_WEIGHT_LIGHT = tag("item_weight/light");
	public static final TagKey<Item> ITEM_WEIGHT_MEDIUM = tag("item_weight/medium");
	public static final TagKey<Item> ITEM_WEIGHT_HEAVY = tag("item_weight/heavy");
	public static final TagKey<Item> RANGED_ENCHANTABLE_BY_SMITHING = tag("ranged_enchantable_by_smithing");
	public static final TagKey<Item> MELEE_ENCHANTABLE_BY_SMITHING = tag("melee_enchantable_by_smithing");
	public static final TagKey<Item> PARAMECIA = tag("paramecia");
	public static final TagKey<Item> LOGIA = tag("logia");
	public static final TagKey<Item> ZOAN = tag("zoan");
	public static final TagKey<Item> DEVIL_FRUIT = tag("devil_fruit");
	public static final TagKey<Item> MANGROVE_LOGS = tag("mangrove_logs");
	public static final TagKey<Item> FRUIT_REINCARNATION = tag("fruit_reincarnation");
	public static final TagKey<Item> BANNED_ITEMS_CHALLANGES = tag("banned_items_challenges");
	public static final TagKey<Item> HARDENING_DAMAGE_BONUS = tag("hardening_damage_bonus");
	public static final TagKey<Item> IMBUING_DAMAGE_BONUS = tag("imbuing_damage_bonus");
	public static final TagKey<Item> DUGONG_FOOD = tag("dugong_food");
	public static final TagKey<Item> YAGARA_FOOD = tag("yagara_bull_food");
	public static final TagKey<Item> WHITE_WALKIE_FOOD = tag("white_walkie_food");
	public static final TagKey<Item> BANANAWANI_FOOD = tag("bananawani_food");
	public static final TagKey<Item> BIG_DUCK_FOOD = tag("big_duck_food");
	public static final TagKey<Item> FLYING_FISH_FOOD = tag("flying_fish_food");

	static void init() {
	}
	static TagKey<Item> tag(String var0) {
		return ItemTags.create(ResourceLocation.fromNamespaceAndPath("mineminenomi",var0));
	}
}
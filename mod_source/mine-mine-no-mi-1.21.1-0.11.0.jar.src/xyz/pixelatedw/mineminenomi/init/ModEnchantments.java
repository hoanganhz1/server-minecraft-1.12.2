package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class ModEnchantments {
	// Fields
	public static final ResourceKey<Enchantment> DIAL_IMPACT = key("Impact Dial");
	public static final ResourceKey<Enchantment> DIAL_FLASH = key("Flash Dial");
	public static final ResourceKey<Enchantment> KAIROSEKI = key("Kairoseki");

	public static void bootstrap(BootstrapContext<Enchantment> var0) {
		HolderGetter var1 = var0.lookup(Registries.ITEM);
		EquipmentSlotGroup[] var2 = new EquipmentSlotGroup[]{EquipmentSlotGroup.MAINHAND};
		register(var0,DIAL_IMPACT,Enchantment.enchantment(Enchantment.definition((HolderSet)(var1.getOrThrow(ItemTags.WEAPON_ENCHANTABLE)),10,3,Enchantment.constantCost(-1),Enchantment.constantCost(-1),0,var2)));
		EquipmentSlotGroup[] var3 = new EquipmentSlotGroup[]{EquipmentSlotGroup.MAINHAND};
		register(var0,DIAL_FLASH,Enchantment.enchantment(Enchantment.definition((HolderSet)(var1.getOrThrow(ItemTags.WEAPON_ENCHANTABLE)),10,1,Enchantment.constantCost(-1),Enchantment.constantCost(-1),0,var3)));
		EquipmentSlotGroup[] var4 = new EquipmentSlotGroup[]{EquipmentSlotGroup.MAINHAND};
		register(var0,KAIROSEKI,Enchantment.enchantment(Enchantment.definition((HolderSet)(var1.getOrThrow(ItemTags.WEAPON_ENCHANTABLE)),10,1,Enchantment.constantCost(-1),Enchantment.constantCost(-1),0,var4)));
	}
	private static void register(BootstrapContext<Enchantment> var0, ResourceKey<Enchantment> var1, Enchantment.Builder var2) {
		var0.register(var1,var2.build(var1.location()));
	}
	private static ResourceKey<Enchantment> key(String var0) {
		String var1 = WyHelper.getResourceName(var0);
		ModRegistry.registerName(ModRegistry$I18nCategory.ENCHANTMENT,var1,var0);
		return ResourceKey.create(Registries.ENCHANTMENT,ResourceLocation.fromNamespaceAndPath("mineminenomi",var1));
	}
	public static void init() {
	}
}
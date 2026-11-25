package xyz.pixelatedw.mineminenomi.api;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class WyArmorMaterial$Builder {
	// Fields
	private final ResourceLocation id;
	private int[] damageReductionAmountArray;
	private int dyeableLayers;
	private boolean hasBaseLayer;
	private int enchantability;
	private Holder<SoundEvent> equipSound;
	private float toughness;
	private float knockbackResistance;
	private Supplier<Ingredient> repairMaterial;

	public WyArmorMaterial$Builder(ResourceLocation var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public WyArmorMaterial$Builder setProtectionForSlot(ArmorItem.Type var1, int var2) {
		this.damageReductionAmountArray[var1.ordinal()] = var2;
		return this;
	}
	public WyArmorMaterial$Builder setDyeableLayers(int var1) {
		this.dyeableLayers = var1;
		return this;
	}
	public WyArmorMaterial$Builder removeBaseLayer() {
		this.hasBaseLayer = false;
		return this;
	}
	public WyArmorMaterial$Builder setEnchantability(int var1) {
		this.enchantability = var1;
		return this;
	}
	public WyArmorMaterial$Builder setToughness(int var1) {
		this.toughness = (float)(var1);
		return this;
	}
	public WyArmorMaterial$Builder setKnockbackResistence(int var1) {
		this.knockbackResistance = (float)(var1);
		return this;
	}
	public WyArmorMaterial$Builder setEquipSound(Holder<SoundEvent> var1) {
		this.equipSound = var1;
		return this;
	}
	public WyArmorMaterial$Builder setRepairIngredient(Supplier<Ingredient> var1) {
		this.repairMaterial = var1;
		return this;
	}
	public Holder<ArmorMaterial> build() {
		ArrayList var1 = Lists.newArrayList();
		if (this.hasBaseLayer){
			var1.add(new ArmorMaterial.Layer(this.id));
		}
		int var2 = 0;
		do{
			if ((var2) < (this.dyeableLayers)){
				int var3 = var2;
				String var4 = "_" + var3;
				var1.add(new ArmorMaterial.Layer(this.id,var4,true));
				var2++;
				continue;
			}else{
				break;
			}
		} while (true);
		EnumMap var3_1 = new EnumMap((Class)(ArmorItem.Type.class));
		ArmorItem.Type[] var4_1 = ArmorItem.Type.values();
		int var5 = var4_1.length;
		int var6 = 0;
		do{
			if ((var6) < (var5)){
				ArmorItem.Type var7 = var4_1[var6];
				var3_1.put((Enum)(var7),(Object)(Integer.valueOf(this.damageReductionAmountArray[var7.ordinal()])));
				var6++;
				continue;
			}else{
				break;
			}
		} while (true);
		return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL,this.id,new ArmorMaterial((Map)(var3_1),this.enchantability,this.equipSound,this.repairMaterial,(List)(var1),this.toughness,this.knockbackResistance));
	}
}
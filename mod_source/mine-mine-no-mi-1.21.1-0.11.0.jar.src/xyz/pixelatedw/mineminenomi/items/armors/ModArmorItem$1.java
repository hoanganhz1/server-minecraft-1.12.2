package xyz.pixelatedw.mineminenomi.items.armors;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;

public class ModArmorItem$1 implements IClientItemExtensions {
	ModArmorItem$1(final ModArmorItem var1) {
	}
	public HumanoidModel<?> getHumanoidArmorModel(LivingEntity var0, ItemStack var1, EquipmentSlot var2, HumanoidModel<?> var3) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public <T extends LivingEntity> void slimDown(EquipmentSlot var1, HumanoidModel<T> var2) {
		float var3 = ((var1) == (EquipmentSlot.HEAD)) ? (0.92F) : (0.9F);
		float var4 = ((var1) == (EquipmentSlot.LEGS)) ? (0.9F) : (0.85F);
		float var5 = ((var1) == (EquipmentSlot.FEET)) ? (0.8F) : (1F);
		var2.head.xScale = var3;
		var2.head.zScale = var3;
		var2.hat.xScale = var3;
		var2.hat.zScale = var3;
		var2.body.xScale = var4;
		var2.body.zScale = var4;
		var2.rightArm.xScale = var4;
		var2.rightArm.zScale = var4;
		var2.leftArm.xScale = var4;
		var2.leftArm.zScale = var4;
		var2.rightLeg.xScale = var5;
		var2.rightLeg.zScale = var5;
		var2.leftLeg.xScale = var5;
		var2.leftLeg.zScale = var5;
	}
	public int getArmorLayerTintColor(ItemStack var1, LivingEntity var2, ArmorMaterial.Layer var3, int var4, int var5) {
		MultiChannelItemColor var6 = ((MultiChannelItemColor)(var1.get(ModDataComponents.MULTI_CHANNEL_DYES)));
		if (((var4) > (0)) && (var6.hasLayerColor((var4) - (1)))){
			return var6.getLayerColor((var4) - (1));
		}else{
			return var5;
		}
	}
}
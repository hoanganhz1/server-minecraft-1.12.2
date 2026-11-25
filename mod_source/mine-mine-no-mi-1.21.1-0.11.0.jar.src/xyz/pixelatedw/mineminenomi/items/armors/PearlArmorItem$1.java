package xyz.pixelatedw.mineminenomi.items.armors;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import xyz.pixelatedw.mineminenomi.init.ModArmors$Client;

public class PearlArmorItem$1 implements IClientItemExtensions {
	PearlArmorItem$1(final PearlArmorItem var1) {
	}
	public HumanoidModel<?> getHumanoidArmorModel(LivingEntity var1, ItemStack var2, EquipmentSlot var3, HumanoidModel<?> var4) {
		HumanoidModel var5 = ((HumanoidModel)(ModArmors$Client.ARMOR_MODELS.get(var2.getItem())));
		return (HumanoidModel<?>) (HumanoidModel) (((var5) != (null)) ? (var5) : (var4));
	}
}
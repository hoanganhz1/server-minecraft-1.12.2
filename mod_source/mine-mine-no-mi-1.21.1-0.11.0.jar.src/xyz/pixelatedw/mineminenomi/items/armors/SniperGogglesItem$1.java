package xyz.pixelatedw.mineminenomi.items.armors;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import xyz.pixelatedw.mineminenomi.init.ModArmors$Client;
import xyz.pixelatedw.mineminenomi.models.armors.SniperGogglesModel;

public class SniperGogglesItem$1 implements IClientItemExtensions {
	SniperGogglesItem$1(final SniperGogglesItem var1) {
	}
	public HumanoidModel<?> getHumanoidArmorModel(LivingEntity var1, ItemStack var2, EquipmentSlot var3, HumanoidModel<?> var4) {
		HumanoidModel var5 = ((HumanoidModel)(ModArmors$Client.ARMOR_MODELS.get(var2.getItem())));
		if (((var5) != (null)) && (var5 instanceof SniperGogglesModel)){
			((SniperGogglesModel)(var5)).updateState(var1);
		}
		return (HumanoidModel<?>) (HumanoidModel) (((var5) != (null)) ? (var5) : (var4));
	}
}
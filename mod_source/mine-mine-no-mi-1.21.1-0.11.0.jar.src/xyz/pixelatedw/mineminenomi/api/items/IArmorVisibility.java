package xyz.pixelatedw.mineminenomi.api.items;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IArmorVisibility<T extends LivingEntity, A extends HumanoidModel<T>> {
	public abstract void setPartVisibility(A var0, ItemStack var1, EquipmentSlot var2);
}
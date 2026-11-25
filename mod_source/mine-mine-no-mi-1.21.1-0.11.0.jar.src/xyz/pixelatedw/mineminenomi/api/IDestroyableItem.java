package xyz.pixelatedw.mineminenomi.api;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public interface IDestroyableItem {
	public abstract void onEntityDestroyed(ItemEntity var0, ItemStack var1, Entity.RemovalReason var2);
}
package xyz.pixelatedw.mineminenomi.data.entity;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import xyz.pixelatedw.mineminenomi.init.ModAttachments;

public class ProjectileData implements INBTSerializable<CompoundTag> {
	// Fields
	private final Projectile owner;
	private boolean isBusoshokuImbued;
	private boolean isBusoshokuShrouded;
	private boolean isHaoshokuInfused;
	private ItemStack weaponUsed;

	public static ProjectileData createCapability(IAttachmentHolder var0) {
		return new ProjectileData(((Projectile)(var0)));
	}
	public static Optional<ProjectileData> get(Projectile var0) {
		if (!(var0.hasData(ModAttachments.PROJECTILE))){
			var0.setData(ModAttachments.PROJECTILE,new ProjectileData(var0));
		}
		return (Optional<ProjectileData>) (Optional) (var0.getExistingData(ModAttachments.PROJECTILE));
	}
	public static ProjectileData getOrCreate(Projectile var0) {
		if (!(var0.hasData(ModAttachments.PROJECTILE))){
			var0.setData(ModAttachments.PROJECTILE,new ProjectileData(var0));
		}
		return ((ProjectileData)(var0.getData(ModAttachments.PROJECTILE)));
	}
	private ProjectileData(Projectile var1) {
		this.weaponUsed = ItemStack.EMPTY;
		this.owner = var1;
	}
	public void setProjectileBusoshokuImbued(boolean var1) {
		this.isBusoshokuImbued = var1;
	}
	public boolean isProjectileBusoshokuImbued() {
		return this.isBusoshokuImbued;
	}
	public void setProjectileBusoshokuShrouded(boolean var1) {
		this.isBusoshokuShrouded = var1;
	}
	public boolean isProjectileBusoshokuShrouded() {
		return this.isBusoshokuShrouded;
	}
	public void setProjectileHaoshokuInfused(boolean var1) {
		this.isHaoshokuInfused = var1;
	}
	public boolean isProjectileHaoshokuInfused() {
		return this.isHaoshokuInfused;
	}
	public void setWeaponUsed(ItemStack var1) {
		if ((var1) == (null)){
			var1 = ItemStack.EMPTY;
		}
		this.weaponUsed = var1;
	}
	public ItemStack getWeaponUsed() {
		return this.weaponUsed;
	}
	public CompoundTag serializeNBT(HolderLookup.Provider var1) {
		CompoundTag var2 = new CompoundTag();
		var2.putBoolean("isProjectileBusoshokuImbued",this.isProjectileBusoshokuImbued());
		var2.putBoolean("isProjectileBusoshokuShrouded",this.isProjectileBusoshokuShrouded());
		var2.putBoolean("isProjectileHaoshokuInfused",this.isProjectileHaoshokuInfused());
		ItemStack var3 = this.getWeaponUsed();
		if ((var3) != (null)){
			var2.putString("weaponUsed",BuiltInRegistries.ITEM.getKey(var3.getItem()).toString());
		}
		return var2;
	}
	public void deserializeNBT(HolderLookup.Provider var1, CompoundTag var2) {
		this.setProjectileBusoshokuImbued(var2.getBoolean("isProjectileBusoshokuImbued"));
		this.setProjectileBusoshokuShrouded(var2.getBoolean("isProjectileBusoshokuShrouded"));
		this.setProjectileHaoshokuInfused(var2.getBoolean("isProjectileHaoshokuInfused"));
		this.setWeaponUsed(new ItemStack(((ItemLike)(BuiltInRegistries.ITEM.get(ResourceLocation.parse(var2.getString("weaponUsed")))))));
	}
}
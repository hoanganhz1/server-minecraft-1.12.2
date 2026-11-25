package xyz.pixelatedw.mineminenomi.integrations.curios;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import xyz.pixelatedw.mineminenomi.handlers.ability.ProgressionHandler;

public class SimpleCurioItem implements ICurio {
	// Fields
	private final ItemStack stack;

	public SimpleCurioItem(ItemStack var1) {
		this.stack = var1;
	}
	public void curioTick(SlotContext var1) {
		LivingEntity var2 = var1.entity();
		if (var2 instanceof Player){
			Player var3 = ((Player)(var2));
			this.stack.getItem().inventoryTick(this.stack,var1.entity().level(),(Entity)(var3),var1.index(),false);
		}
	}
	public void onEquip(SlotContext var1, ItemStack var2) {
		ProgressionHandler.checkForEquipmentUnlocks(var1.entity());
	}
	public void onUnequip(SlotContext var1, ItemStack var2) {
		ProgressionHandler.checkForEquipmentUnlocks(var1.entity());
	}
	public boolean canEquipFromUse(SlotContext var1) {
		return true;
	}
	public ItemStack getStack() {
		return this.stack;
	}
}
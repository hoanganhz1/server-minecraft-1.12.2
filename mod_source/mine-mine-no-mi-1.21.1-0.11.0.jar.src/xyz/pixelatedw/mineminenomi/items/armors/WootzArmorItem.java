package xyz.pixelatedw.mineminenomi.items.armors;

import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModMaterials;

public class WootzArmorItem extends Mod3DArmorItem {
	public WootzArmorItem() {
		super(ModMaterials.WOOTZ_ARMOR_MATERIAL,ArmorItem.Type.CHESTPLATE);
	}
	@OnlyIn(value=Dist.CLIENT)
	public void initializeClient(Consumer<IClientItemExtensions> var1) {
		var1.accept((IClientItemExtensions)(new WootzArmorItem$1(this)));
	}
	public static boolean hasArmorEquipped(LivingEntity var0) {
		ItemStack var1 = var0.getItemBySlot(EquipmentSlot.CHEST);
		if ((!(var1.isEmpty())) && ((var1.getItem()) == (ModArmors.WOOTZ_STEEL_ARMOR.get()))){
			return true;
		}else{
			return false;
		}
	}
}
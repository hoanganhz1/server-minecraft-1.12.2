package xyz.pixelatedw.mineminenomi.items.armors;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.items.IArmorVisibility;

public class OverShoulderHelmetItem extends ModArmorItem implements IArmorVisibility<LivingEntity, HumanoidModel<LivingEntity>> {
	public OverShoulderHelmetItem(Holder<ArmorMaterial> var1, ArmorItem.Type var2) {
		this(var1,var2,new Item.Properties());
	}
	public OverShoulderHelmetItem(Holder<ArmorMaterial> var1, ArmorItem.Type var2, Item.Properties var3) {
		super(var1,var2,var3);
	}
	@OnlyIn(value=Dist.CLIENT)
	public void setPartVisibility(HumanoidModel<LivingEntity> var1, ItemStack var2, EquipmentSlot var3) {
		var1.setAllVisible(false);
		if ((var3) == (EquipmentSlot.HEAD)){
			var1.head.visible = true;
			var1.hat.visible = true;
			var1.body.visible = true;
			var1.rightArm.visible = true;
			var1.leftArm.visible = true;
		}
	}
}
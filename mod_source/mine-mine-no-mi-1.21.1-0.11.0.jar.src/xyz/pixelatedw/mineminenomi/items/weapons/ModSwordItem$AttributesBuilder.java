package xyz.pixelatedw.mineminenomi.items.weapons;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class ModSwordItem$AttributesBuilder {
	// Fields
	private float damage;
	private float speed;
	private float reach;

	public ModSwordItem$AttributesBuilder() {
		this.damage = 1F;
		this.speed = -2.4F;
		this.reach = 0F;
	}
	public static ModSwordItem$AttributesBuilder of(float var0) {
		ModSwordItem$AttributesBuilder var1 = new ModSwordItem$AttributesBuilder();
		var1.damage = var0;
		return var1;
	}
	public static ModSwordItem$AttributesBuilder of(float var0, float var1) {
		ModSwordItem$AttributesBuilder var2 = new ModSwordItem$AttributesBuilder();
		var2.damage = var0;
		var2.speed = var1;
		return var2;
	}
	public ModSwordItem$AttributesBuilder reach(float var1) {
		this.reach = var1;
		return this;
	}
	public ItemAttributeModifiers build() {
		ItemAttributeModifiers.Builder var1 = ItemAttributeModifiers.builder();
		var1.add(Attributes.ATTACK_DAMAGE,new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,(double)(this.damage),AttributeModifier.Operation.ADD_VALUE),EquipmentSlotGroup.MAINHAND);
		var1.add(Attributes.ATTACK_SPEED,new AttributeModifier(Item.BASE_ATTACK_SPEED_ID,(double)(this.speed),AttributeModifier.Operation.ADD_VALUE),EquipmentSlotGroup.MAINHAND);
		var1.add(Attributes.ENTITY_INTERACTION_RANGE,new AttributeModifier(ModSwordItem.BASE_ATTACK_REACH_ID,(double)(this.reach),AttributeModifier.Operation.ADD_VALUE),EquipmentSlotGroup.MAINHAND);
		return var1.build();
	}
}
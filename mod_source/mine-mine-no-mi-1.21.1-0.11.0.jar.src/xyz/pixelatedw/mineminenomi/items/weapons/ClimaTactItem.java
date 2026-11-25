package xyz.pixelatedw.mineminenomi.items.weapons;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class ClimaTactItem extends ModSwordItem {
	public ClimaTactItem(Tier var1, int var2) {
		super(var1,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of((float)(var2)).build()));
	}
}
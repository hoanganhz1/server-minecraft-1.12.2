package xyz.pixelatedw.mineminenomi.api.events;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.neoforged.bus.api.Event;

public class AttributeModifierApplyEvent extends Event {
	// Fields
	private AttributeMap map;

	public AttributeModifierApplyEvent(AttributeMap var1, int var2) {
		this.map = var1;
	}
	public AttributeInstance getAttribute(Holder<Attribute> var1) {
		return this.map.getInstance(var1);
	}
}
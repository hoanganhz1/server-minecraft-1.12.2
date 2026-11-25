package xyz.pixelatedw.mineminenomi.api;

import net.minecraft.world.entity.ai.attributes.Attribute;

@FunctionalInterface()
public interface AttributeRegistryEntry {
	public abstract Attribute get(String var0);
}
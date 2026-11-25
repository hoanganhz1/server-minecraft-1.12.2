package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.resources.ResourceLocation;

public class AbilityComponentKey<C extends AbilityComponent<?>> {
	// Fields
	private final ResourceLocation id;

	public AbilityComponentKey(ResourceLocation var1) {
		this.id = var1;
	}
	public static <C extends AbilityComponent<?>> AbilityComponentKey<C> key(ResourceLocation var0) {
		return new AbilityComponentKey(var0);
	}
	public ResourceLocation getId() {
		return this.id;
	}
}
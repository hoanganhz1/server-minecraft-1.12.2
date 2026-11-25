package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import xyz.pixelatedw.mineminenomi.api.MappedTag;

public class ModTags$Entities {
	// Fields
	public static final MappedTag<EntityType<?>> CONDUCTIVE = new MappedTag(ResourceLocation.fromNamespaceAndPath("mineminenomi","conductive_values"),"mapped_tags/entity_types",Registries.ENTITY_TYPE);
	public static final TagKey<EntityType<?>> MAGNETIC = tag("magnetic");
	public static final TagKey<EntityType<?>> KAIROSEKI = tag("kairoseki");

	static void init() {
	}
	static TagKey<EntityType<?>> tag(String var0) {
		return TagKey.create(Registries.ENTITY_TYPE,ResourceLocation.fromNamespaceAndPath("mineminenomi",var0));
	}
}
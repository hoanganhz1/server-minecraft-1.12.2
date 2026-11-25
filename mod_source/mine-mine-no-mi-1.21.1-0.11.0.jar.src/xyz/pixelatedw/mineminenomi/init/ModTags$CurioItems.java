package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags$CurioItems {
	// Fields
	public static final TagKey<Item> BACK = tag("back");
	public static final TagKey<Item> CHARM = tag("charm");
	public static final TagKey<Item> FACE = tag("face");
	public static final TagKey<Item> HEAD = tag("head");

	static void init() {
	}
	static TagKey<Item> tag(String var0) {
		return ItemTags.create(ResourceLocation.fromNamespaceAndPath("curios",var0));
	}
}
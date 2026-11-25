package xyz.pixelatedw.mineminenomi.api.damagesources;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public enum SourceType {
	// Fields
	UNKNOWN((ResourceLocation)(null)),
	SLASH(ModResources.SOURCE_TYPE_SLASH),
	BLUNT(ModResources.SOURCE_TYPE_BLUNT),
	INDIRECT((ResourceLocation)(null)),
	FIST(ModResources.SOURCE_TYPE_FIST),
	PHYSICAL(ModResources.SOURCE_TYPE_PHYSICAL),
	INTERNAL(ModResources.SOURCE_TYPE_INTERNAL),
	PROJECTILE(ModResources.SOURCE_TYPE_PROJECTILE),
	BULLET(ModResources.SOURCE_TYPE_BULLET),
	FRIENDLY((ResourceLocation)(null)),
	LOGIA_BYPASS((ResourceLocation)(null)),
	TECHNOLOGY(ModResources.SOURCE_TYPE_TECHNOLOGY),
	VANILLA((ResourceLocation)(null));
	private final @Nullable() ResourceLocation texture;

	private SourceType(ResourceLocation var3) {
		this.texture = var3;
	}
	@Nullable()
	public @Nullable() ResourceLocation getTexture() {
		return this.texture;
	}
	public String getUnlocalizedName() {
		return this.toString().toLowerCase();
	}
}
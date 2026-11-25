package xyz.pixelatedw.mineminenomi.api.damagesources;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public enum SourceElement {
	// Fields
	NONE((ResourceLocation)(null)),
	FIRE(ModResources.SOURCE_ELEMENT_FIRE),
	MAGMA(ModResources.SOURCE_ELEMENT_MAGMA),
	ICE(ModResources.SOURCE_ELEMENT_ICE),
	WATER(ModResources.SOURCE_ELEMENT_WATER),
	LIGHT(ModResources.SOURCE_ELEMENT_LIGHT),
	LIGHTNING(ModResources.SOURCE_ELEMENT_LIGHTNING),
	RUBBER((ResourceLocation)(null)),
	EXPLOSION(ModResources.SOURCE_ELEMENT_EXPLOSION),
	WAX(ModResources.SOURCE_ELEMENT_WAX),
	POISON(ModResources.SOURCE_ELEMENT_POISON),
	RUST((ResourceLocation)(null)),
	SHOCKWAVE(ModResources.SOURCE_ELEMENT_SHOCKWAVE),
	SMOKE(ModResources.SOURCE_ELEMENT_SMOKE),
	METAL(ModResources.SOURCE_ELEMENT_METAL),
	AIR((ResourceLocation)(null)),
	SLIME((ResourceLocation)(null)),
	GRAVITY((ResourceLocation)(null));
	private final @Nullable() ResourceLocation texture;

	private SourceElement(ResourceLocation var3) {
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
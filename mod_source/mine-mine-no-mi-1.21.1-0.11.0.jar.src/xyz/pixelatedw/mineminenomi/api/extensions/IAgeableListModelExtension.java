package xyz.pixelatedw.mineminenomi.api.extensions;

import net.minecraft.client.model.geom.ModelPart;

public interface IAgeableListModelExtension {
	public abstract Iterable<ModelPart> getParts();
}